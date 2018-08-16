package teamproject.com.clubapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MyOptionActivity extends AppCompatActivity {

    private DrawerMenu drawerMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_option);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (drawerMenu == null) {
            drawerMenu = DrawerMenu.addMenu(this, R.id.my_option_menu, R.id.my_option_drawer);
        } else {
            drawerMenu.restartMenu(this, R.id.my_option_menu, R.id.my_option_drawer);
        }
    }
}
