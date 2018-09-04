package teamproject.com.clubapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamproject.com.clubapplication.GroupActivity;
import teamproject.com.clubapplication.adapter.GroupHomeNoticeListviewAdapter;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.data.Club;
import teamproject.com.clubapplication.data.ClubMemberClass;
import teamproject.com.clubapplication.data.Notice;
import teamproject.com.clubapplication.utils.CommonUtils;
import teamproject.com.clubapplication.utils.RefreshData;
import teamproject.com.clubapplication.utils.bus.BusProvider;
import teamproject.com.clubapplication.utils.bus.event.ClubLoadEvent;
import teamproject.com.clubapplication.utils.glide.GlideApp;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;


public class GroupHomeFragment extends Fragment  implements RefreshData {

    @BindView(R.id.group_home_img)
    ImageView groupHomeImg;
    @BindView(R.id.group_home_txt_group_name)
    LinearLayout groupHomeTxtGroupName;
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
    Unbinder unbinder;

    GroupHomeNoticeListviewAdapter groupHomeNoticeListviewAdapter;
    ArrayList<Notice> arrayList;
    Bus bus;
    ClubMemberClass clubMemberClass;
    int page = 1;
    int count = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_home, container, false);

        unbinder = ButterKnife.bind(this, view);
        bus = BusProvider.getInstance().getBus();
        bus.register(this);

        arrayList= new ArrayList<>();
        groupHomeNoticeListviewAdapter = new GroupHomeNoticeListviewAdapter(arrayList);
        groupHomeLvNotice.setAdapter(groupHomeNoticeListviewAdapter);
        clubMemberClass = ((GroupActivity)getActivity()).getClubMemberClass();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
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
        if(clubMemberClass!=null) {
            Call<String> imgObserver = RetrofitService.getInstance().getRetrofitRequest().selectClubProfileImg(clubMemberClass.getClub().getId());
            imgObserver.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    GlideApp.with(getContext()).load(CommonUtils.serverURL+response.body()).centerCrop().into(groupHomeImg);
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                }
            });
            getCount();
        }
    }

    public void getData() {

        Call<ArrayList<Notice>> observer  = RetrofitService.getInstance().getRetrofitRequest().selectClubNotice(clubMemberClass.getClub().getId(), page);
        observer.enqueue(new Callback<ArrayList<Notice>>() {
            @Override
            public void onResponse(Call<ArrayList<Notice>> call, Response<ArrayList<Notice>> response) {
                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        arrayList.addAll(response.body());
                        groupHomeNoticeListviewAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Notice>> call, Throwable t) {
            }
        });
    }

    public void getCount(){
        Call<Integer> observer = RetrofitService.getInstance().getRetrofitRequest().getNoticeCount(clubMemberClass.getClub().getId());
        observer.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    count = response.body();
                    if(count>0) {
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
