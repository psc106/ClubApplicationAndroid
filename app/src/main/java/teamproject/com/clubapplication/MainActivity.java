package teamproject.com.clubapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import teamproject.com.clubapplication.utils.DrawerMenu;

public class MainActivity extends AppCompatActivity {
    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    DrawerMenu drawerMenu;
    @Override
    protected void onResume() {
        super.onResume();

        if (drawerMenu == null) {
            drawerMenu = DrawerMenu.addMenu(this, R.id.main_menu, R.id.main_drawer);
        } else {
            drawerMenu.restartMenu(this, R.id.main_menu, R.id.main_drawer);
        }
    }

    public void backHomeActivity(Activity otherActivity) {
        Intent intent = new Intent(otherActivity, this.getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
