package teamproject.com.clubapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamproject.com.clubapplication.adapter.GroupViewpagerAdapter;
import teamproject.com.clubapplication.data.ClubMemberClass;
import teamproject.com.clubapplication.fragment.GroupAlbumFragment;
import teamproject.com.clubapplication.fragment.GroupBoardFragment;
import teamproject.com.clubapplication.fragment.GroupCalendarFragment;
import teamproject.com.clubapplication.fragment.GroupHomeFragment;
import teamproject.com.clubapplication.fragment.GroupManageFragment;
import teamproject.com.clubapplication.utils.DrawerMenu;
import teamproject.com.clubapplication.utils.LoadingDialog;
import teamproject.com.clubapplication.utils.LoginService;
import teamproject.com.clubapplication.utils.RefreshData;
import teamproject.com.clubapplication.utils.bus.BusProvider;
import teamproject.com.clubapplication.utils.bus.event.ClubLoadEvent;
import teamproject.com.clubapplication.utils.bus.event.LoginEvent;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;

public class GroupActivity extends AppCompatActivity implements RefreshData {

    @BindView(R.id.groupHome_viewpager)
    ViewPager viewpager;
    @BindView(R.id.groupHome_tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.group_home_menu)
    FrameLayout groupHomeMenu;
    @BindView(R.id.group_home_drawer)
    DrawerLayout groupHomeDrawer;
    private DrawerMenu drawerMenu;

    GroupViewpagerAdapter homeAdapter;
    LoginService loginService;
    private ClubMemberClass clubMemberClass = null;
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

        homeAdapter = new GroupViewpagerAdapter(getSupportFragmentManager());
        homeAdapter.addFragment(new GroupHomeFragment(), "HOME", 0);
        viewpager.setOffscreenPageLimit(4);
        viewpager.setAdapter(homeAdapter);
        tabLayout.setupWithViewPager(viewpager);

        clubId = intent.getLongExtra("clubId", -1);
        if (clubId == -1) {
            finish();
            //실패처리
        } else {
            final LoadingDialog loadingDialog = LoadingDialog.getInstance();
            loadingDialog.progressON(this, "메세지");
            Long userId = -1L;
            if (loginService.getMember() != null)
                userId = loginService.getMember().getId();
            Call<ClubMemberClass> observer = RetrofitService.getInstance().getRetrofitRequest().selectClub(clubId, userId);
            observer.enqueue(new Callback<ClubMemberClass>() {
                @Override
                public void onResponse(Call<ClubMemberClass> call, Response<ClubMemberClass> response) {
                    if (response.isSuccessful()) {
                        clubMemberClass = response.body();
                        BusProvider.getInstance().getBus().post(new ClubLoadEvent(response.body()));
                        loadingDialog.progressOFF();
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
        if (this.clubId == clubLoadEvent.getClubMemberClass().getClub().getId()) {
            refresh();
        }
    }


    @Subscribe
    void finishLoad(LoginEvent loginEvent) {
        if (loginEvent.getState() == 0) {
            if (tabLayout.getSelectedTabPosition() > 0) {
                viewpager.setCurrentItem(0);
            }
        }
        Long userId = -1L;
        if (loginService.getMember() != null)
            userId = loginService.getMember().getId();
        Call<ClubMemberClass> observer = RetrofitService.getInstance().getRetrofitRequest().selectClub(clubId, userId);
        observer.enqueue(new Callback<ClubMemberClass>() {
            @Override
            public void onResponse(Call<ClubMemberClass> call, Response<ClubMemberClass> response) {
                if (response.isSuccessful()) {
                    clubMemberClass = response.body();
                    BusProvider.getInstance().getBus().post(new ClubLoadEvent(response.body()));
                }
            }

            @Override
            public void onFailure(Call<ClubMemberClass> call, Throwable t) {

            }
        });

    }

    @Override
    public void refresh() {
        if (loginService.getMember() == null || clubMemberClass.getMemberClass().equals("O") || clubMemberClass.getMemberClass().equals("N")) {
            homeAdapter.clearFragment();
            homeAdapter.addFragment(new GroupHomeFragment(), "HOME", 0);
            homeAdapter.notifyDataSetChanged();
            viewpager.setOffscreenPageLimit(1);
        } else {
            homeAdapter.clearFragment();
            homeAdapter.addFragment(new GroupHomeFragment(), "HOME", 0);
            homeAdapter.addFragment(new GroupBoardFragment(), "게시판", 1);
            homeAdapter.addFragment(new GroupAlbumFragment(), "앨범", 2);
            homeAdapter.addFragment(new GroupCalendarFragment(), "일정", 3);
            homeAdapter.addFragment(new GroupManageFragment(), "설정", 4);
            homeAdapter.notifyDataSetChanged();
            viewpager.setOffscreenPageLimit(4);
        }
    }
}
