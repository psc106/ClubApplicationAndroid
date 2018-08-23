package teamproject.com.clubapplication;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import teamproject.com.clubapplication.adapter.GroupManageMemberCheckListviewAdapter;
import teamproject.com.clubapplication.adapter.GroupManageMemberJoinListviewAdapter;

public class GroupManageMemberActivity extends AppCompatActivity {

    @BindView(R.id.group_manage_member_lv_join)
    ListView groupManageMemberLvJoin;
    @BindView(R.id.group_manage_member_join_layout)
    LinearLayout groupManageMemberJoinLayout;
    @BindView(R.id.group_manage_member_lv_check_member)
    ListView groupManageMemberLvCheckMember;
    @BindView(R.id.group_manage_member_check_layout)
    LinearLayout groupManageMemberCheckLayout;
    @BindView(R.id.group_manage_member_menu)
    FrameLayout groupManageMemberMenu;
    @BindView(R.id.group_manage_member_drawer)
    DrawerLayout groupManageMemberDrawer;
    private DrawerMenu drawerMenu;

    GroupManageMemberCheckListviewAdapter groupManageMemberCheckAdapter;
    GroupManageMemberJoinListviewAdapter groupManageMemberJoinListviewAdapter;

    ArrayList<?>arrayListjoin;
    ArrayList<?>arrayListcheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_manage_member);
        ButterKnife.bind(this);


        arrayListcheck=new ArrayList<>();
        groupManageMemberCheckAdapter = new GroupManageMemberCheckListviewAdapter(arrayListcheck);
        groupManageMemberLvCheckMember.setAdapter(groupManageMemberCheckAdapter);


        arrayListjoin = new ArrayList<>();
        groupManageMemberJoinListviewAdapter = new GroupManageMemberJoinListviewAdapter(arrayListjoin);
        groupManageMemberLvJoin.setAdapter(groupManageMemberJoinListviewAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (drawerMenu == null) {
            drawerMenu = DrawerMenu.addMenu(this, R.id.group_manage_member_menu, R.id.group_manage_member_drawer);
        } else {
            drawerMenu.restartMenu(this, R.id.group_manage_member_menu, R.id.group_manage_member_drawer);
        }
    }
}
