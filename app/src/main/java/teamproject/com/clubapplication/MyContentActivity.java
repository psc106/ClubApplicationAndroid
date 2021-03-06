package teamproject.com.clubapplication;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamproject.com.clubapplication.adapter.MyContentPageAdapter;
import teamproject.com.clubapplication.data.Member;
import teamproject.com.clubapplication.utils.DrawerMenu;
import teamproject.com.clubapplication.utils.LoadingDialog;
import teamproject.com.clubapplication.utils.LoginService;
import teamproject.com.clubapplication.utils.RefreshData;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;

public class MyContentActivity extends AppCompatActivity implements RefreshData {
    public static Activity activity;

    @BindView(R.id.myContent_viewPager)
    ViewPager viewPager;
    @BindView(R.id.myContent_btn_Post)
    Button postBtn;
    @BindView(R.id.myContent_btn_Comment)
    Button commentBtn;

    @OnClick(R.id.myContent_btn_Post)
    void movePostPage() {
        if(viewPager.getCurrentItem()!=0) {
            viewPager.setCurrentItem(0);
        }
    }
    @OnClick(R.id.myContent_btn_Comment)
    void moveCommentPage() {
        if(viewPager.getCurrentItem()!=1) {
            viewPager.setCurrentItem(1);
        }
    }

    MyContentPageAdapter pageAdapter;
    LoginService loginService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_content);
        ButterKnife.bind(this);
        loginService = LoginService.getInstance();
        pageAdapter = new MyContentPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pageAdapter);
    }

    DrawerMenu drawerMenu;
    @Override
    protected void onResume() {
        super.onResume();

        if (drawerMenu == null) {
            drawerMenu = DrawerMenu.addMenu(this, R.id.myContent_menu, R.id.myContent_drawer);
        } else {
            drawerMenu.restartMenu(this, R.id.myContent_menu, R.id.myContent_drawer);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        activity = null;
    }

    @Override
    public void refresh() {

        RefreshData refreshData = (RefreshData)(pageAdapter.getItem(viewPager.getCurrentItem()));
        refreshData.refresh();
    }


    @Override
    public void onBackPressed() {
        LoadingDialog.getInstance().progressOFF();
        super.onBackPressed();
    }
}
