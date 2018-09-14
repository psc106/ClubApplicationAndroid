package teamproject.com.clubapplication;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
import teamproject.com.clubapplication.utils.CommonUtils;
import teamproject.com.clubapplication.utils.DrawerMenu;
import teamproject.com.clubapplication.utils.LoadingDialog;
import teamproject.com.clubapplication.utils.LoginService;
import teamproject.com.clubapplication.utils.RefreshData;
import teamproject.com.clubapplication.utils.bus.BusProvider;
import teamproject.com.clubapplication.utils.bus.event.ClubLoadEvent;
import teamproject.com.clubapplication.utils.bus.event.LoginEvent;
import teamproject.com.clubapplication.utils.customView.KeyHideActivity;
import teamproject.com.clubapplication.utils.glide.GlideApp;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;

public class GroupActivity extends KeyHideActivity implements RefreshData {

    @BindView(R.id.group_viewpager)
    ViewPager viewpager;
    @BindView(R.id.group_tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.group_menu)
    FrameLayout groupHomeMenu;
    @BindView(R.id.group_drawer)
    DrawerLayout groupHomeDrawer;
    @BindView(R.id.group_btn_Write)
    FloatingActionButton writeBtn;
    @BindView(R.id.group_imageV)
    ImageView imageView;
    @BindView(R.id.group_toolbar)
    Toolbar toolbar;
    @BindView(R.id.group_collapsToolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.group_appbar)
    AppBarLayout appBarLayout;
//    @BindView(R.id.group_toolbar2)
//    Toolbar toolbar2;
//    @BindView(R.id.group_collapsToolbar2)
//    CollapsingToolbarLayout collapsingToolbarLayout2;
//    @BindView(R.id.group_appbar2)
//    AppBarLayout appBarLayout2;
    @BindView(R.id.group_coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.group_layout)
    LinearLayout layout;
    @BindView(R.id.group_frame_Write)
    FrameLayout writeBtnFrame;

    private DrawerMenu drawerMenu;

    GroupViewpagerAdapter homeAdapter;
    LoginService loginService;
    private ClubMemberClass clubMemberClass = null;
    Bus bus;
    Long clubId;

    //0 : 전체보임, 1 : 툴바까지, 2 : 탭레이아웃까지 3 :
    int state = 0;
    int offset = 0;

    @OnClick(R.id.group_btn_Write)
    void moveWriteForm() {
        if (clubMemberClass == null) {
            return;
        } else {
            Intent intent = new Intent(this, GroupWriteActivity.class);
            int category = 1;
            //관리자
            if (tabLayout.getSelectedTabPosition() == 0 && clubMemberClass.getMemberClass().equals("A")) {
                category = 0;
            } else if (tabLayout.getSelectedTabPosition() == 2) {
                category = 2;
            } else if (tabLayout.getSelectedTabPosition() == 3) {
                category = 3;
            } else {
                category = 1;
            }
            intent.putExtra("category", category);
            intent.putExtra("class", clubMemberClass.getMemberClass());
            startActivity(intent);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        ButterKnife.bind(this);
        loginService = LoginService.getInstance();
        Intent intent = getIntent();
        bus = BusProvider.getInstance().getBus();
        bus.register(this);

        homeAdapter = new GroupViewpagerAdapter(getSupportFragmentManager(), this);
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

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position > 0) {
                    if (!ishideState) {
                        hideView(appBarLayout);
                    }
                } else {
                    if (ishideState) {
                        showView(appBarLayout);
//                        viewpager.scrollTo(0, offset);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    @Override
    protected void onResume() {
        if (drawerMenu == null) {
            drawerMenu = DrawerMenu.addMenu(this, R.id.group_menu, R.id.group_drawer);
        } else {
            drawerMenu.restartMenu(this, R.id.group_menu, R.id.group_drawer);
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
        Call<String> imgObserver = RetrofitService.getInstance().getRetrofitRequest().selectClubProfileImg(clubMemberClass.getClub().getId());
        imgObserver.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    GlideApp.with(GroupActivity.this).load(CommonUtils.serverURL + response.body()).centerCrop().into(imageView);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
        if (loginService.getMember() == null || clubMemberClass.getMemberClass().equals("O") || clubMemberClass.getMemberClass().equals("N") || clubMemberClass.getMemberClass().equals("W")) {
            homeAdapter.clearFragment();
            homeAdapter.addFragment(new GroupHomeFragment(), "HOME", 0);
            homeAdapter.notifyDataSetChanged();
            viewpager.setOffscreenPageLimit(1);
            writeBtnFrame.setVisibility(View.GONE);
        } else {
            homeAdapter.clearFragment();
            homeAdapter.addFragment(new GroupHomeFragment(), "HOME", 0);
            homeAdapter.addFragment(new GroupBoardFragment(), "게시판", 1);
            homeAdapter.addFragment(new GroupAlbumFragment(), "앨범", 2);
            homeAdapter.addFragment(new GroupCalendarFragment(), "일정", 3);
            homeAdapter.addFragment(new GroupManageFragment(), "설정", 4);
            homeAdapter.notifyDataSetChanged();
            viewpager.setOffscreenPageLimit(4);
            writeBtnFrame.setVisibility(View.VISIBLE);
        }
    }


    boolean ishideState = false;
    boolean mIsShowing = false;
    boolean mIsHiding = false;
    Interpolator interpolator = new FastOutSlowInInterpolator();

    /**
     * View를 숨긴다
     * <p/>
     * 아래로 슬라이딩하는 애니메이션.
     * 애니메이션 종료후 View를 없앤다.
     *
     * @param view The quick return view
     */
    private void hideView(final View view) {

        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        mIsHiding = true;
        ViewPropertyAnimator animator = view.animate()
                .setInterpolator(interpolator)
                .setDuration(300);

        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                appBarLayout.setExpanded(false, true);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mIsHiding = false;

                appBarLayout.setActivated(false);
                appBarLayout.setVisibility(View.GONE);

                ishideState = true;
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                // 취소되면 다시 보여줌
                mIsHiding = false;
                if (!mIsShowing) {
                    showView(view);
                }
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });

        animator.start();
    }

    /**
     * View를 보여준다.
     * <p/>
     * 아래서 위로 슬라이딩 애니메이션.
     * 애니메이션을 시작하기전 View를 보여준다.
     *
     * @param view The quick return view
     */
    private void showView(final View view) {

        mIsShowing = true;
        ViewPropertyAnimator animator = view.animate()
                .setInterpolator(interpolator)
                .setDuration(300);

        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                appBarLayout.setVisibility(View.VISIBLE);
//                appBarLayout.setExpanded(true, true);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewpager.getLayoutParams();
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mIsShowing = false;
                ishideState = false;
                appBarLayout.setActivated(true);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                // 취소되면 다시 숨김
                mIsShowing = false;
                if (!mIsHiding) {
                    hideView(view);
                }
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });

        animator.start();
    }

}

