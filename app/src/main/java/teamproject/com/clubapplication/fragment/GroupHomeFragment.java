package teamproject.com.clubapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamproject.com.clubapplication.GroupActivity;
import teamproject.com.clubapplication.MainActivity;
import teamproject.com.clubapplication.adapter.GroupHomeListviewAdapter;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.data.ClubMemberClass;
import teamproject.com.clubapplication.data.Notice;
import teamproject.com.clubapplication.db.DBManager;
import teamproject.com.clubapplication.utils.CommonUtils;
import teamproject.com.clubapplication.utils.LoadingDialog;
import teamproject.com.clubapplication.utils.LoginService;
import teamproject.com.clubapplication.utils.RefreshData;
import teamproject.com.clubapplication.utils.bus.BusProvider;
import teamproject.com.clubapplication.utils.bus.event.ClubLoadEvent;
import teamproject.com.clubapplication.utils.glide.GlideApp;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;


public class GroupHomeFragment extends Fragment implements RefreshData {

    @BindView(R.id.group_home_txt_group_count)
    TextView groupHomeTxtGroupCount;
    @BindView(R.id.group_home_txt_master)
    TextView groupHomeTxtMaster;
    @BindView(R.id.group_home_txt_category)
    TextView groupHomeTxtCategory;
    @BindView(R.id.group_home_txt_location)
    TextView groupHomeTxtLocation;
    @BindView(R.id.group_home_txt_info)
    TextView groupHomeTxtInfo;
    @BindView(R.id.group_home_lv_notice)
    ListView groupHomeLvNotice;
    @BindView(R.id.group_home_btn_join)
    Button groupHomeBtnJoin;
    @BindView(R.id.groupHome_txt_Title)
    TextView title;
    @BindView(R.id.groupHome_scrollV)
    NestedScrollView scrollView;

    Unbinder unbinder;

    @OnClick(R.id.group_home_btn_join)
    void joinClick() {
        if (loginService.getMember() != null && clubMemberClass != null) {
            Call<Void> observer = RetrofitService.getInstance().getRetrofitRequest().joinClub(clubMemberClass.getClubView().getId(), loginService.getMember().getId());
            observer.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        if (clubMemberClass != null) {
                            Intent intent = new Intent(getActivity(), GroupActivity.class);
                            intent.putExtra("clubId", clubMemberClass.getClubView().getId());
                            clubMemberClass.setMemberClass("O");
                            startActivity(intent);
                            getActivity().finish();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        }
    }

    GroupHomeListviewAdapter groupHomeNoticeListviewAdapter;
    ArrayList<Notice> arrayList;
    Bus bus;
    ClubMemberClass clubMemberClass;
    DBManager dbManager;
    LoginService loginService;
    int page = 1;
    int count = 0;
    boolean isLoad = false;

    public static GroupHomeFragment newInstance(Long clubId) {

        Bundle args = new Bundle();
        args.putLong("clubId", clubId);

        GroupHomeFragment fragment = new GroupHomeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_home, container, false);

        unbinder = ButterKnife.bind(this, view);
        bus = BusProvider.getInstance().getBus();
        bus.register(this);
        loginService = LoginService.getInstance();

        groupHomeBtnJoin.setFocusable(false);
        groupHomeBtnJoin.setFocusableInTouchMode(false);

        arrayList = new ArrayList<>();
        groupHomeNoticeListviewAdapter = new GroupHomeListviewAdapter(arrayList);
        groupHomeLvNotice.setAdapter(groupHomeNoticeListviewAdapter);
        clubMemberClass = ((GroupActivity) getActivity()).getClubMemberClass();
        dbManager = new DBManager(getContext(), DBManager.DB_NAME, null, DBManager.CURRENT_VERSION);


        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollView.getChildAt(0).getBottom()
                        <= (scrollView.getHeight() + scrollView.getScrollY())) {
                    if (page * 10 < count) {
                        page++;
                        getData();

                        Log.d("로그", ""+page);
                        if(page>2) {
                            scrollView.fling(0);
                            scrollView.scrollBy(0, 0);
                            //scroll view is at bottom
                        }
                    }
                }
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        refresh();
        isLoad = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        isLoad = false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        bus.unregister(this);
    }

    @Override
    public void refresh() {
        page = 1;

        if (clubMemberClass == null) {
            if (isLoad)
                ((MainActivity) (MainActivity.activity)).backHomeActivity(getActivity());
            return;
        }

        groupHomeTxtCategory.setText(dbManager.getCategoryFromId(clubMemberClass.getClubView().getCategory_id()));
        groupHomeTxtGroupCount.setText(clubMemberClass.getClubView().getCur_people() + "/" + clubMemberClass.getClubView().getMax_people());
        groupHomeTxtInfo.setText(clubMemberClass.getClubView().getIntro());
        groupHomeTxtLocation.setText(clubMemberClass.getClubView().getLocal());
        groupHomeTxtMaster.setText(clubMemberClass.getClubView().getNickname());
        title.setText(clubMemberClass.getClubView().getName());

        if (!clubMemberClass.getMemberClass().equals("O")) {
            Call<String> observer = RetrofitService.getInstance().getRetrofitRequest().refreshMemberClass(clubMemberClass.getClubView().getId(), loginService.getMember().getId());
            observer.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        clubMemberClass.setMemberClass(response.body());
                    }

                    if (clubMemberClass.getMemberClass().equals("N")) {
                        groupHomeBtnJoin.setVisibility(View.VISIBLE);
                    } else {
                        groupHomeBtnJoin.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                }
            });
        } else {
            groupHomeBtnJoin.setVisibility(View.GONE);
        }

        if (clubMemberClass != null) {
            getCount();
        }
    }

    public void getData() {

        Call<ArrayList<Notice>> observer = RetrofitService.getInstance().getRetrofitRequest().selectClubNotice(clubMemberClass.getClubView().getId(), page);
        observer.enqueue(new Callback<ArrayList<Notice>>() {
            @Override
            public void onResponse(Call<ArrayList<Notice>> call, Response<ArrayList<Notice>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        arrayList.addAll(response.body());
                        groupHomeNoticeListviewAdapter.notifyDataSetChanged();
                        CommonUtils.setListviewHeightBasedOnChildren(groupHomeLvNotice);


                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Notice>> call, Throwable t) {
            }
        });
    }

    public void getCount() {
        Call<Integer> observer = RetrofitService.getInstance().getRetrofitRequest().getNoticeCount(clubMemberClass.getClubView().getId());
        observer.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    count = response.body();
                    if (count > 0) {
                        arrayList.clear();
                        getData();
                    }
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
            }
        });
    }

    @Subscribe
    void finishLoad(ClubLoadEvent clubLoadEvent) {
        this.clubMemberClass = clubLoadEvent.getClubMemberClass();
        refresh();
    }
}
