package teamproject.com.clubapplication.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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
import teamproject.com.clubapplication.data.CommentView;
import teamproject.com.clubapplication.data.Notice;
import teamproject.com.clubapplication.data.PostFrame;
import teamproject.com.clubapplication.data.PostView;
import teamproject.com.clubapplication.utils.CommonUtils;
import teamproject.com.clubapplication.utils.RefreshData;
import teamproject.com.clubapplication.utils.bus.BusProvider;
import teamproject.com.clubapplication.utils.bus.event.ClubLoadEvent;
import teamproject.com.clubapplication.utils.bus.event.CommentEvent;
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


    Unbinder unbinder;

    ArrayList<PostFrame> arrayList;
    GroupBoardListviewAdapter groupBoardListviewAdapter;
    Bus bus;
    ClubMemberClass clubMemberClass;
    int page = 0;
    int count = 0;
    private boolean lastItemVisibleFlag = false;    // 리스트 스크롤이 마지막 셀(맨 바닥)로 이동했는지 체크할 변수
    private boolean mLockListView = false;          // 데이터 불러올때 중복안되게 하기위한 변수

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_board, container, false);
        unbinder = ButterKnife.bind(this, view);
        bus = BusProvider.getInstance().getBus();
        bus.register(this);


        arrayList = new ArrayList<>();
        groupBoardListviewAdapter = new GroupBoardListviewAdapter(getContext(), arrayList);
        groupBoardListV.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        groupBoardListV.setItemsCanFocus(true);
        groupBoardListV.setAdapter(groupBoardListviewAdapter);
        clubMemberClass = ((GroupActivity) getActivity()).getClubMemberClass();

        groupBoardListV.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Log.d("로그", "collapse");
                CommonUtils.setListViewHeight(groupBoardListV);
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

                    Log.d("로그", "page " + page);
                    for (int i = 0; i < response.body().size(); ++i) {
                        for (int j = 0; j < response.body().get(i).getCommentView().size(); ++j) {
                            Log.d("로그", "덧글 내용 " + response.body().get(i).getCommentView().get(j).getContent());
                            Log.d("로그", "덧글 아이디 " + response.body().get(i).getCommentView().get(j).getId());
                        }
                    }
                    for (int i = (page - 1) * 4; i < arrayList.size(); ++i) {
                        Log.d("로그", "열리는 페이지 " + i);
                        groupBoardListV.expandGroup(i);
                    }

                    CommonUtils.setListViewHeight(groupBoardListV);

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


    @Subscribe
    void finishLoad(final CommentEvent commentEvent) {

        Call<ArrayList<CommentView>> observer;
        switch (commentEvent.getType()) {
            case 0:
                observer = RetrofitService.getInstance().getRetrofitRequest().refreshPostComment(commentEvent.getPostId());
                observer.enqueue(new Callback<ArrayList<CommentView>>() {
                    @Override
                    public void onResponse(Call<ArrayList<CommentView>> call, Response<ArrayList<CommentView>> response) {
                        if (response.isSuccessful()) {
                            arrayList.get(commentEvent.getPosition()).getCommentView().clear();
                            arrayList.get(commentEvent.getPosition()).getCommentView().addAll(response.body());
                            groupBoardListviewAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<CommentView>> call, Throwable t) {

                    }
                });
                break;

            case 1:
                observer = RetrofitService.getInstance().getRetrofitRequest().refreshPostComment(commentEvent.getPostId());
                observer.enqueue(new Callback<ArrayList<CommentView>>() {
                    @Override
                    public void onResponse(Call<ArrayList<CommentView>> call, Response<ArrayList<CommentView>> response) {
                        if (response.isSuccessful()) {
                            arrayList.get(commentEvent.getPosition()).getCommentView().clear();
                            arrayList.get(commentEvent.getPosition()).getCommentView().addAll(response.body());
                            groupBoardListviewAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<CommentView>> call, Throwable t) {

                    }
                });
                break;

            case 2:
                observer = RetrofitService.getInstance().getRetrofitRequest().refreshPostComment(commentEvent.getPostId());
                observer.enqueue(new Callback<ArrayList<CommentView>>() {
                    @Override
                    public void onResponse(Call<ArrayList<CommentView>> call, Response<ArrayList<CommentView>> response) {
                        if (response.isSuccessful()) {
                            arrayList.get(commentEvent.getPosition()).getCommentView().clear();
                            arrayList.get(commentEvent.getPosition()).getCommentView().addAll(response.body());
                            groupBoardListviewAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<CommentView>> call, Throwable t) {

                    }
                });
                break;
        }

    }
}
