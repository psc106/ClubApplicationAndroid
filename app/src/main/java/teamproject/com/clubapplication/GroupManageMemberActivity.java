package teamproject.com.clubapplication;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import teamproject.com.clubapplication.adapter.WaitingMemberListviewAdapter;
import teamproject.com.clubapplication.adapter.joinMemberListviewAdapter;
import teamproject.com.clubapplication.data.Member;

public class GroupManageMemberActivity extends AppCompatActivity {

    @BindView(R.id.join_toolbar)
    Toolbar joinToolbar;
    @BindView(R.id.main_appbarLayout)
    AppBarLayout mainAppbarLayout;
    @BindView(R.id.memberManage_listV_Waiting)
    ListView listViewWaiting;
    @BindView(R.id.memberManage_layout_Waiting)
    LinearLayout waitingLayout;
    @BindView(R.id.memberManage_listV_Join)
    ListView listViewJoin;
    @BindView(R.id.memberManage_layout_Join)
    LinearLayout joinLayout;


    WaitingMemberListviewAdapter waitAdapter;
    joinMemberListviewAdapter joinAdapter;

    ArrayList<Member> waitList;
    ArrayList<Member> joinList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_manage_member);
        ButterKnife.bind(this);


        waitList = new ArrayList<>();
        waitAdapter = new WaitingMemberListviewAdapter(waitList);
        listViewWaiting.setAdapter(waitAdapter);


        joinList = new ArrayList<>();
        joinAdapter = new joinMemberListviewAdapter(joinList);
        listViewJoin.setAdapter(joinAdapter);
    }

    @OnClick({R.id.memberManage_listV_Waiting, R.id.memberManage_listV_Join})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.memberManage_listV_Waiting:
                break;
            case R.id.memberManage_listV_Join:
                break;
        }
    }
}
