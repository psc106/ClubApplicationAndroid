package teamproject.com.clubapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamproject.com.clubapplication.data.TestData;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
}
