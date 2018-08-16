package teamproject.com.clubapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ModifyNickNameActivity extends AppCompatActivity {

    private DrawerMenu drawerMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_nick_name);


    }
    @Override
    protected void onResume() {
        super.onResume();
        if (drawerMenu == null) {
            drawerMenu = DrawerMenu.addMenu(this, R.id.modifty_nick_name_menu, R.id.modify_nick_name_drawer);
        } else {
            drawerMenu.restartMenu(this, R.id.modifty_nick_name_menu, R.id.modify_nick_name_drawer);
        }
    }
}
