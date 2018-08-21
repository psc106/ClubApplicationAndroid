package teamproject.com.clubapplication;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyGroupActivity extends AppCompatActivity {

    @BindView(R.id.lv_my_group)
    ListView lvMyGroup;
    @BindView(R.id.my_group_menu)
    FrameLayout myGroupMenu;
    @BindView(R.id.my_group_drawer)
    DrawerLayout myGroupDrawer;
    private DrawerMenu drawerMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_group);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (drawerMenu == null) {
            drawerMenu = DrawerMenu.addMenu(this, R.id.my_group_menu, R.id.my_group_drawer);
        } else {
            drawerMenu.restartMenu(this, R.id.my_group_menu, R.id.my_group_drawer);
        }
    }
}
