package teamproject.com.clubapplication;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import teamproject.com.clubapplication.data.ExternalImage;
import teamproject.com.clubapplication.fragment.GroupWriteAlbumFragment;
import teamproject.com.clubapplication.fragment.GroupWriteBoardFragment;
import teamproject.com.clubapplication.fragment.GroupWriteNoticeFragment;
import teamproject.com.clubapplication.fragment.GroupWriteScheduleFragment;
import teamproject.com.clubapplication.utils.CommonUtils;
import teamproject.com.clubapplication.utils.DrawerMenu;
import teamproject.com.clubapplication.utils.bus.BusProvider;
import teamproject.com.clubapplication.utils.customView.KeyHideActivity;

public class GroupWriteActivity extends KeyHideActivity {

    @BindView(R.id.groupWrite_spinner)
    Spinner typeSpinner;
    @BindView(R.id.groupWrite_toolbar)
    AppBarLayout toolbar;
    @BindView(R.id.groupWrite_frame_writeForm)
    FrameLayout writeFormFrame;
    private DrawerMenu drawerMenu;
//
//    final static String[] WRITE_COMMON_TYPE_NAME = {"게시판", "앨범", "일정"};
//    final static String[] WRITE_ADMIN_TYPE_NAME = {"공지", "게시판", "앨범", "일정"};

    final static String[] WRITE_COMMON_TYPE_NAME = {"게시판"};
    final static String[] WRITE_ADMIN_TYPE_NAME = {"공지", "게시판"};
    final static Fragment[] FRAGMENTS = {GroupWriteNoticeFragment.getInstance(), GroupWriteBoardFragment.getInstance(), GroupWriteAlbumFragment.getInstance(), GroupWriteScheduleFragment.getInstance()};

    String memberClass;
    int category;
    long clubId;
    FragmentTransaction ft;

    public long getClubId() {
        return clubId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_write);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        memberClass = intent.getStringExtra("class");
        clubId = intent.getLongExtra("clubId", -1);
        category = intent.getIntExtra("category", 0);
        if (TextUtils.isEmpty(memberClass)) {
            // finish();
        } if (clubId==-1) {
            // finish();
        }


        if (memberClass.equals("A")) {
            CommonUtils.initSpinner(this, typeSpinner, WRITE_ADMIN_TYPE_NAME, null);
            typeSpinner.setSelection(category);
            Log.d("로그", "onCreate: " + memberClass);
        } else {
            CommonUtils.initSpinner(this, typeSpinner, WRITE_COMMON_TYPE_NAME, null);
            typeSpinner.setSelection(category - 1);
            Log.d("로그", "onCreate: " + memberClass);
        }


        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("로그", "onCreate: " + memberClass);
                ft = getSupportFragmentManager().beginTransaction();
                if (memberClass != null) {
                    if (memberClass.equals("A")) {
                        ft.replace(R.id.groupWrite_frame_writeForm, FRAGMENTS[position]);
                    } else {
                        ft.replace(R.id.groupWrite_frame_writeForm, FRAGMENTS[position + 1]);
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

    final int REQUEST_TAKE_ALBUM = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_TAKE_ALBUM:
                ArrayList<ExternalImage> resultEvent = new ArrayList<>();
                if (resultCode == Activity.RESULT_OK) {

                    // 멀티 선택을 지원하지 않는 기기에서는 getClipdata()가 없음 > getData()로 접근해야 함
                    if (data.getClipData() == null) {
                        ExternalImage externalImage = new ExternalImage(data.getData(), CommonUtils.getPath(this, data.getData()));
                        resultEvent.add(externalImage);
                    } else {
                        ClipData clipData = data.getClipData();
                        if (clipData.getItemCount() > 0) {
                            for (int i = 0; i < clipData.getItemCount(); i++) {
                                if (i > 10) {
                                    break;
                                }

                                Uri dataStr = clipData.getItemAt(i).getUri();
                                ExternalImage externalImage = new ExternalImage(dataStr, CommonUtils.getPath(this, dataStr));
                                resultEvent.add(externalImage);
                            }
                        }
                    }
                } else {
                    Toast.makeText(this, "사진 선택을 취소하였습니다.", Toast.LENGTH_SHORT).show();
                }
                BusProvider.getInstance().getBus().post(resultEvent);
                break;
        }
    }


}
