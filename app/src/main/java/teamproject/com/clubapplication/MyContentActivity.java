package teamproject.com.clubapplication;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyContentActivity extends AppCompatActivity {

    @BindView(R.id.myContent_viewPager)
    ViewPager viewPager;
    MyContentPageAdapter pageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
}
