package teamproject.com.clubapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GroupManageModifyActivity extends AppCompatActivity {

    private DrawerMenu drawerMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_manage_modify);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (drawerMenu == null) {
            drawerMenu = DrawerMenu.addMenu(this, R.id.group_manage_modify_menu, R.id.group_manage_modify_drawer);
        } else {
            drawerMenu.restartMenu(this, R.id.group_manage_modify_menu, R.id.group_manage_modify_drawer);
        }
    }
}
