package teamproject.com.clubapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.FrameLayout;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamproject.com.clubapplication.adapter.GroupHomeViewpagerAdapter;
import teamproject.com.clubapplication.data.Club;
import teamproject.com.clubapplication.data.ClubMemberClass;
import teamproject.com.clubapplication.fragment.GroupHomeFragment;
import teamproject.com.clubapplication.fragment.GroupManageFragment;
import teamproject.com.clubapplication.utils.DrawerMenu;
import teamproject.com.clubapplication.utils.LoginService;
import teamproject.com.clubapplication.utils.RefreshData;
import teamproject.com.clubapplication.utils.bus.BusProvider;
import teamproject.com.clubapplication.utils.bus.event.ClubLoadEvent;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;

public class GroupHomeActivity extends AppCompatActivity implements RefreshData {

    @BindView(R.id.groupHome_viewpager)
    ViewPager viewpager;
    @BindView(R.id.groupHome_tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.group_home_menu)
    FrameLayout groupHomeMenu;
    @BindView(R.id.group_home_drawer)
    DrawerLayout groupHomeDrawer;
    private DrawerMenu drawerMenu;

    GroupHomeViewpagerAdapter homeAdapter;
    LoginService loginService;
    private ClubMemberClass clubMemberClass = null;
    boolean isLoad = false;
    Bus bus;
    Long clubId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_home);
        ButterKnife.bind(this);
        loginService = LoginService.getInstance();
        Intent intent = getIntent();
        bus = BusProvider.getInstance().getBus();
        bus.register(this);

        homeAdapter = new GroupHomeViewpagerAdapter(getSupportFragmentManager(), 5);
        viewpager.setAdapter(homeAdapter);
        tabLayout.setupWithViewPager(viewpager);

        clubId = intent.getLongExtra("clubId", -1);
        if(clubId==-1) {
            //실패처리
        }
        else {
            Long userId = -1L;
            if(loginService.getMember()!=null)
                userId = loginService.getMember().getId();
            Call<ClubMemberClass> observer = RetrofitService.getInstance().getRetrofitRequest().selectClub(clubId, userId);
            observer.enqueue(new Callback<ClubMemberClass>() {
                @Override
                public void onResponse(Call<ClubMemberClass> call, Response<ClubMemberClass> response) {
                    if(response.isSuccessful()){
                        BusProvider.getInstance().getBus().post(new ClubLoadEvent(response.body()));
                        isLoad = true;
                    }
                }

                @Override
                public void onFailure(Call<ClubMemberClass> call, Throwable t) {

                }
            });
        }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }

    public ClubMemberClass getClubMemberClass() {
        return clubMemberClass;
    }

    @Subscribe
    void finishLoad(ClubLoadEvent clubLoadEvent) {
        if(this.clubId==clubLoadEvent.getClubMemberClass().getClub().getId()) {
            refresh();
        }
    }

    @Override
    public void refresh() {
        if(clubMemberClass!=null) {
            if (clubMemberClass.getMemberClass().equals("N") && clubMemberClass.getMemberClass().equals("O")) {
                tabLayout.removeAllTabs();
                tabLayout.addTab(tabLayout.newTab().setText("홈"));
                tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            } else {
                tabLayout.removeAllTabs();
                tabLayout.addTab(tabLayout.newTab().setText("홈"));
                tabLayout.addTab(tabLayout.newTab().setText("게시판"));
                tabLayout.addTab(tabLayout.newTab().setText("앨범"));
                tabLayout.addTab(tabLayout.newTab().setText("일정"));
                tabLayout.addTab(tabLayout.newTab().setText("관리"));
                tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            }
            homeAdapter.setTabCount(tabLayout.getTabCount());
            homeAdapter.notifyDataSetChanged();
        }
    }
}
