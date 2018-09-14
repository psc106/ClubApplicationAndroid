package teamproject.com.clubapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import teamproject.com.clubapplication.fragment.GroupWriteAlbumFragment;
import teamproject.com.clubapplication.fragment.GroupWriteBoardFragment;
import teamproject.com.clubapplication.fragment.GroupWriteNoticeFragment;
import teamproject.com.clubapplication.fragment.GroupWriteScheduleFragment;
import teamproject.com.clubapplication.utils.CommonUtils;
import teamproject.com.clubapplication.utils.DrawerMenu;
import teamproject.com.clubapplication.utils.customView.KeyHideActivity;

public class GroupWriteActivity extends KeyHideActivity {

    @BindView(R.id.groupWrite_spinner)
    Spinner typeSpinner;
    @BindView(R.id.groupWrite_toolbar)
    AppBarLayout toolbar;
    @BindView(R.id.groupWrite_frame_writeForm)
    FrameLayout writeFormFrame;
    private DrawerMenu drawerMenu;

    final static String[] WRITE_COMMON_TYPE_NAME = {"게시판", "앨범", "일정"};
    final static String[] WRITE_ADMIN_TYPE_NAME = {"공지", "게시판", "앨범", "일정"};
    final static Fragment[] FRAGMENTS = {GroupWriteNoticeFragment.getInstance(),GroupWriteBoardFragment.getInstance(), GroupWriteAlbumFragment.getInstance(), GroupWriteScheduleFragment.getInstance()};

    String memberClass;
    int category;
    FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_write);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        memberClass = intent.getStringExtra("class");
        category = intent.getIntExtra("category", 0);
        if(TextUtils.isEmpty(memberClass)){
            // finish();
        }


        if (memberClass.equals("A")) {
            CommonUtils.initSpinner(this, typeSpinner, WRITE_ADMIN_TYPE_NAME, null);
            typeSpinner.setSelection(category);
            Log.d("로그", "onCreate: "+memberClass);
        } else {
            CommonUtils.initSpinner(this, typeSpinner, WRITE_COMMON_TYPE_NAME, null);
            typeSpinner.setSelection(category-1);
            Log.d("로그", "onCreate: "+memberClass);
        }


        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("로그", "onCreate: "+memberClass);
                ft = getSupportFragmentManager().beginTransaction();
                if(memberClass!=null){
                    if(memberClass.equals("A")) {
                        ft.replace(R.id.groupWrite_frame_writeForm, FRAGMENTS[position]);
                    } else {
                        ft.replace(R.id.groupWrite_frame_writeForm, FRAGMENTS[position+1]);
                    }
                }
                ft.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    @Override
    protected void onResume() {
        if (drawerMenu == null) {
            drawerMenu = DrawerMenu.addMenu(this, R.id.group_album_board_write_menu, R.id.group_album_board_write_drawer);
        } else {
            drawerMenu.restartMenu(this, R.id.group_album_board_write_menu, R.id.group_album_board_write_drawer);
        }
        super.onResume();

    }
}
