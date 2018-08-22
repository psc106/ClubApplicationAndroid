package teamproject.com.clubapplication;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import teamproject.com.clubapplication.adapter.GroupHomeAdapter;
import teamproject.com.clubapplication.utils.DrawerMenu;

public class GroupHomeActivity extends AppCompatActivity {

    @BindView(R.id.group_home_btn_home)
    Button groupHomeBtnHome;
    @BindView(R.id.group_home_btn_board)
    Button groupHomeBtnBoard;
    @BindView(R.id.group_home_btn_schedule)
    Button groupHomeBtnSchedule;
    @BindView(R.id.group_home_btn_album)
    Button groupHomeBtnAlbum;
    @BindView(R.id.group_home_btn_setting)
    Button groupHomeBtnSetting;
    @BindView(R.id.group_home_viewpager)
    ViewPager groupHomeViewpager;
    @BindView(R.id.group_home_menu)
    FrameLayout groupHomeMenu;
    @BindView(R.id.group_home_drawer)
    DrawerLayout groupHomeDrawer;
    private DrawerMenu drawerMenu;

    GroupHomeAdapter groupHomeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_home);
        ButterKnife.bind(this);


        groupHomeAdapter = new GroupHomeAdapter(getSupportFragmentManager());
        groupHomeViewpager.setAdapter(groupHomeAdapter);



    }
    @OnClick(R.id.group_home_btn_home)
    void movehome(){
        groupHomeViewpager.setCurrentItem(0);
    }

    @OnClick(R.id.group_home_btn_board)
    void moveboard(){
        groupHomeViewpager.setCurrentItem(1);
    }

    @OnClick(R.id.group_home_btn_schedule)
    void moveschedule(){
        groupHomeViewpager.setCurrentItem(2);
    }

    @OnClick(R.id.group_home_btn_album)
    void movealbum(){
        groupHomeViewpager.setCurrentItem(3);
    }

    @OnClick(R.id.group_home_btn_setting)
    void movesetting(){
        groupHomeViewpager.setCurrentItem(0);
    }


    @Override
    protected void onResume() {
        if (drawerMenu == null) {
            drawerMenu = DrawerMenu.addMenu(this, R.id.group_home_menu, R.id.group_home_drawer);
        } else {
            drawerMenu.restartMenu(this, R.id.group_home_menu, R.id.group_home_drawer);
        }
        super.onResume();

    }
}
