package teamproject.com.clubapplication;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamproject.com.clubapplication.data.Member;
import teamproject.com.clubapplication.data.MemberView;
import teamproject.com.clubapplication.utils.DrawerMenu;
import teamproject.com.clubapplication.utils.customView.KeyHideActivity;
import teamproject.com.clubapplication.utils.LoginService;
import teamproject.com.clubapplication.utils.glide.GlideApp;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;

public class MyInfoActivity extends KeyHideActivity {
    public static Activity activity;

    @BindView(R.id.myInfo_txt_Id)
    TextView idTxt;
    @BindView(R.id.myInfo_txt_Age)
    TextView ageTxt;
    @BindView(R.id.myInfo_txt_Gender)
    TextView genderTxt;
    @BindView(R.id.myInfo_txt_Name)
    TextView nameTxt;

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
        if(loginService.getMember().getVerify().equals("N")){
            Call<Member> observer = RetrofitService.getInstance().getRetrofitRequest().refreshLoginUser(loginService.getMember().getId());
            observer.enqueue(new Callback<Member>() {
                @Override
                public void onResponse(Call<Member> call, Response<Member> response) {
                    if (response.isSuccessful()) {
                        loginService.refreshMember(response.body());
                    } else {
                        Log.d("로그", "onResponse: fail");
                    }
                }
                @Override
                public void onFailure(Call<Member> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }

        if(member==null){
            ((MainActivity)MainActivity.activity).backHomeActivity(this);
        }
        idTxt.setText(member.getLogin_id());
        nameTxt.setText(member.getName());
        localEdt.setText(member.getLocal());
        GlideApp.with(this).load(R.drawable.profile).into(profileImg);

        //가공 필요
        ageTxt.setText(member.getBirthday());

        String genderStr = "";
        switch (member.getGender()) {
            case 1:
                genderStr="남";
                break;
            case 2:
                genderStr="여";
                break;
            default:
        }
        genderTxt.setText(genderStr);

    }

}
