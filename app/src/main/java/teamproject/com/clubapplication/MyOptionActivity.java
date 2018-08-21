package teamproject.com.clubapplication;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.Switch;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyOptionActivity extends AppCompatActivity {

    @BindView(R.id.my_option_menu)
    FrameLayout myOptionMenu;
    @BindView(R.id.my_option_drawer)
    DrawerLayout myOptionDrawer;
    @BindView(R.id.my_option_join)
    Switch myOptionJoin;
    @BindView(R.id.my_option_club)
    Switch myOptionClub;
    @BindView(R.id.my_option_re)
    Switch myOptionRe;
    @BindView(R.id.my_option_calendar)
    Switch myOptionCalendar;
    @BindView(R.id.my_option_all)
    Switch myOptionAll;
    private DrawerMenu drawerMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_option);
        ButterKnife.bind(this);
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
