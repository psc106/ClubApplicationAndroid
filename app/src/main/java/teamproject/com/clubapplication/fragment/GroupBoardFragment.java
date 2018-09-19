package teamproject.com.clubapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

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
import teamproject.com.clubapplication.GroupPostDetailActivity;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.adapter.GroupBoardListviewAdapter;
import teamproject.com.clubapplication.data.ClubMemberClass;
import teamproject.com.clubapplication.data.Notice;
import teamproject.com.clubapplication.data.PostFrame;
import teamproject.com.clubapplication.data.PostView;
import teamproject.com.clubapplication.utils.CommonUtils;
import teamproject.com.clubapplication.utils.RefreshData;
import teamproject.com.clubapplication.utils.bus.BusProvider;
import teamproject.com.clubapplication.utils.bus.event.ClubLoadEvent;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;


public class GroupBoardFragment extends Fragment implements RefreshData {

    @BindView(R.id.group_board_txt_search)
    EditText groupBoardTxtSearch;
    @BindView(R.id.group_board_btn_search)
    Button groupBoardBtnSearch;
    @BindView(R.id.groupBoard_listV)
    ExpandableListView groupBoardListV;
    @BindView(R.id.groupBoard_scroll)
    NestedScrollView scrollView;
    @BindView(R.id.hiddenFocus)
    View view;


    Unbinder unbinder;

    ArrayList<PostFrame> arrayList;
    GroupBoardListviewAdapter groupBoardListviewAdapter;
    Bus bus;
    ClubMemberClass clubMemberClass;
    int page = 1;
    int count = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_board, container, false);
        unbinder = ButterKnife.bind(this, view);
        bus = BusProvider.getInstance().getBus();
        bus.register(this);


        arrayList = new ArrayList<>();
        groupBoardListviewAdapter = new GroupBoardListviewAdapter(getContext(), arrayList);
        groupBoardListV.setAdapter(groupBoardListviewAdapter);
        clubMemberClass = ((GroupActivity) getActivity()).getClubMemberClass();

        groupBoardListV.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.d("로그", "g click = " + groupPosition);
                return false;
            }
        });


        groupBoardListV.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Log.d("로그", "c click = " + childPosition);
                return false;
            }
        });

        groupBoardListV.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Log.d("로그", "g Collapse = " + groupPosition);
            }
        });

        groupBoardListV.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Log.d("로그", "g Expand = " + groupPosition);
            }
        });


        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollView.getChildAt(0).getBottom()
                        <= (scrollView.getHeight() + scrollView.getScrollY())) {
                    if (page * 4 < count) {
                        page++;
                        getData();

                        groupBoardListviewAdapter.notifyDataSetChanged();
                        CommonUtils.setListViewHeight(groupBoardListV);
                        Toast.makeText(getContext(), page + "", Toast.LENGTH_SHORT).show();

                        scrollView.fling(0);
                        scrollView.scrollBy(0, 0);
                    }

                    //scroll view is at bottom
                }
            }
        });

        return view;

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
        if (clubMemberClass != null) {
            getCount();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        view.requestFocus();
        refresh();
    }

    public void getData() {
        Call<ArrayList<PostFrame>> observer = RetrofitService.getInstance().getRetrofitRequest().selectClubPost(clubMemberClass.getClubView().getId(), page);
        observer.enqueue(new Callback<ArrayList<PostFrame>>() {
            @Override
            public void onResponse(Call<ArrayList<PostFrame>> call, Response<ArrayList<PostFrame>> response) {
                if (response.isSuccessful()) {
                    arrayList.addAll(response.body());
                    groupBoardListviewAdapter.notifyDataSetChanged();
                    CommonUtils.setListViewHeight(groupBoardListV);


                    Log.d("로그", "asd");
                    for(int i = (page-1)*10; i < arrayList.size(); ++i) {
                        groupBoardListV.expandGroup(i);
                        Log.d("로그", "asd"+i);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PostFrame>> call, Throwable t) {
            }
        });
    }

    public void getCount() {
        Call<Integer> observer = RetrofitService.getInstance().getRetrofitRequest().getPostCount(clubMemberClass.getClubView().getId());
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
