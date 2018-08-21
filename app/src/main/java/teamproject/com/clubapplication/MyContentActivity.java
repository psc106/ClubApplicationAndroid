package teamproject.com.clubapplication;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import teamproject.com.clubapplication.adapter.MyContentPageAdapter;
import teamproject.com.clubapplication.utils.DrawerMenu;
import teamproject.com.clubapplication.utils.RefreshData;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_content);
        ButterKnife.bind(this);
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
}
