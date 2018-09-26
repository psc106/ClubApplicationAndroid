package teamproject.com.clubapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamproject.com.clubapplication.adapter.MemberJoinListviewAdapter;
import teamproject.com.clubapplication.adapter.MemberWaitingListviewAdapter;
import teamproject.com.clubapplication.data.Member;
import teamproject.com.clubapplication.data.MemberView;
import teamproject.com.clubapplication.utils.CommonUtils;
import teamproject.com.clubapplication.utils.RefreshData;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;

public class GroupManageMemberActivity extends AppCompatActivity implements RefreshData {

    @BindView(R.id.memberManage_listV_Waiting)
    ListView listViewWaiting;
    @BindView(R.id.memberManage_layout_Waiting)
    LinearLayout waitingLayout;
    @BindView(R.id.memberManage_listV_Join)
    ListView listViewJoin;
    @BindView(R.id.memberManage_layout_Join)
    LinearLayout joinLayout;


    MemberWaitingListviewAdapter waitAdapter;
    MemberJoinListviewAdapter joinAdapter;

    ArrayList<MemberView> waitList;
    ArrayList<MemberView> joinList;

    Long clubId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_manage_member);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        clubId = intent.getLongExtra("clubId", -1);

        waitList = new ArrayList<>();
        waitAdapter = new MemberWaitingListviewAdapter(waitList);
        listViewWaiting.setAdapter(waitAdapter);



        joinList = new ArrayList<>();
        joinAdapter = new MemberJoinListviewAdapter(joinList);
        listViewJoin.setAdapter(joinAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void refresh() {
        Call<ArrayList<MemberView>> waitObserver = RetrofitService.getInstance().getRetrofitRequest().selectWaitingMember(clubId);
        waitObserver.enqueue(new Callback<ArrayList<MemberView>>() {
            @Override
            public void onResponse(Call<ArrayList<MemberView>> call, Response<ArrayList<MemberView>> response) {
                if(response.isSuccessful()){
                    waitList.clear();
                    waitList.addAll(response.body());
                    waitAdapter.notifyDataSetChanged();
                    CommonUtils.setListviewHeightBasedOnChildren(listViewWaiting);
                    Log.d("로그", "wait: "+response.body().size());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MemberView>> call, Throwable t) {

            }
        });

        Call<ArrayList<MemberView>> joinObserver = RetrofitService.getInstance().getRetrofitRequest().selectJoinMember(clubId);
        joinObserver.enqueue(new Callback<ArrayList<MemberView>>() {
            @Override
            public void onResponse(Call<ArrayList<MemberView>> call, Response<ArrayList<MemberView>> response) {
                if(response.isSuccessful()){
                    joinList.clear();
                    joinList.addAll(response.body());
                    joinAdapter.notifyDataSetChanged();
                    CommonUtils.setListviewHeightBasedOnChildren(listViewJoin);
                    Log.d("로그", "join: "+response.body().size());

                }
            }

            @Override
            public void onFailure(Call<ArrayList<MemberView>> call, Throwable t) {

            }
        });
    }
}
