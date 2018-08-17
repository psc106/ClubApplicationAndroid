package teamproject.com.clubapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import teamproject.com.clubapplication.Adapter.GroupManageMemberCheckAdapter;
import teamproject.com.clubapplication.Adapter.GroupManageMemberJoinAdapter;

public class GroupManageMemberActivity extends AppCompatActivity {

    private DrawerMenu drawerMenu;

    GroupManageMemberCheckAdapter groupManageMemberCheckAdapter;
    GroupManageMemberJoinAdapter groupManageMemberJoinAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_manage_member);



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
