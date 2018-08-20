package teamproject.com.clubapplication;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import teamproject.com.clubapplication.data.Member;
import teamproject.com.clubapplication.utils.LoginService;

public class MyInfoActivity extends AppCompatActivity {
    public static Activity activity;

    @BindView(R.id.myInfo_txt_Id)
    TextView idTxt;
    @BindView(R.id.myInfo_txt_Age)
    TextView ageTxt;
    @BindView(R.id.myInfo_txt_Gender)
    TextView genderTxt;
    @BindView(R.id.myInfo_txt_Name)
    TextView nameTxt;

    @BindView(R.id.myInfo_edt_Email)
    EditText emailEdt;
    @BindView(R.id.myInfo_edt_Local)
    EditText localEdt;

    @BindView(R.id.myInfo_img_Profile)
    ImageView profileImg;
    @BindView(R.id.myInfo_btn_modify)
    Button modifyBtn;

    LoginService loginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        ButterKnife.bind(this);
        loginService = LoginService.getInstance();
    }

    DrawerMenu drawerMenu;
    @Override
    protected void onResume() {
        super.onResume();
        initInfo(loginService.getMember());

        if (drawerMenu == null) {
            drawerMenu = DrawerMenu.addMenu(this, R.id.myInfo_menu, R.id.myInfo_drawer);
        } else {
            drawerMenu.restartMenu(this, R.id.myInfo_menu, R.id.myInfo_drawer);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        activity = null;
    }

    private void initInfo(Member member){
        if(member==null){
            ((MainActivity)MainActivity.activity).backHomeActivity(this);
        }
        idTxt.setText(member.getLogin_id());
        nameTxt.setText(member.getName());
        emailEdt.setText(member.getEmail());
        localEdt.setText(member.getLocal());
        profileImg.setBackgroundColor(Color.parseColor("#0fffff"));

        //가공 필요
        ageTxt.setText(member.getBirthday());

        String genderStr = "";
        switch (member.getGender()) {
            case 0:
                genderStr="남";
                break;
            case 1:
                genderStr="여";
                break;
            default:
        }
        genderTxt.setText(genderStr);

    }

}
