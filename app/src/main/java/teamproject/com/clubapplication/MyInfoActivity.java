package teamproject.com.clubapplication;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyInfoActivity extends AppCompatActivity {

    @BindView(R.id.myInfo_img_Profile)
    ImageView myInfoImgProfile;
    @BindView(R.id.myInfo_txt_Name)
    TextView myInfoTxtName;
    @BindView(R.id.myInfo_txt_Age)
    TextView myInfoTxtAge;
    @BindView(R.id.myInfo_txt_Gender)
    TextView myInfoTxtGender;
    @BindView(R.id.myInfo_txt_Id)
    TextView myInfoTxtId;
    @BindView(R.id.myinfo_edt_pw)
    EditText myinfoEdtPw;
    @BindView(R.id.myInfo_edt_Local)
    EditText myInfoEdtLocal;
    @BindView(R.id.myInfo_edt_Email)
    EditText myInfoEdtEmail;
    @BindView(R.id.myInfo_btn_modify)
    Button myInfoBtnModify;
    @BindView(R.id.myInfo_menu)
    FrameLayout myInfoMenu;
    @BindView(R.id.myInfo_drawer)
    DrawerLayout myInfoDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        ButterKnife.bind(this);
    }

    DrawerMenu drawerMenu;

    @Override
    protected void onResume() {
        super.onResume();

        if (drawerMenu == null) {
            drawerMenu = DrawerMenu.addMenu(this, R.id.myInfo_menu, R.id.myInfo_drawer);
        } else {
            drawerMenu.restartMenu(this, R.id.myInfo_menu, R.id.myInfo_drawer);
        }
    }

}
