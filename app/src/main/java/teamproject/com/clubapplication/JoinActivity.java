package teamproject.com.clubapplication;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamproject.com.clubapplication.utils.DrawerMenu;
import teamproject.com.clubapplication.utils.LoadingDialog;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;

public class JoinActivity extends AppCompatActivity {
    public static Activity activity;
    Context context = this;

    @BindView(R.id.join_id) EditText join_id;
    @BindView(R.id.btn_join_check_id) Button btn_join_check_id;//

    @BindView(R.id.join_pw1) EditText join_pw1;
    @BindView(R.id.join_pw2) EditText join_pw2;

    @BindView(R.id.join_name) EditText join_name;

    @BindView(R.id.join_birth_year) EditText join_birth_year;
    @BindView(R.id.join_birth_month) Spinner join_birth_month;
    @BindView(R.id.join_birth_day) EditText join_birth_day;

    @BindView(R.id.join_gender) Switch join_gender;

    @BindView(R.id.join_mail_id) EditText join_mail_id;
    @BindView(R.id.join_mail_address) EditText join_mail_address;
    @BindView(R.id.btn_join_mail_check) Button btn_join_mail_check;//

    @BindView(R.id.join_area_code) Spinner join_area_code;
    @BindView(R.id.join_phoneNum1) EditText join_phoneNum1;
    @BindView(R.id.join_phoneNum2) EditText join_phoneNum2;

    @BindView(R.id.join_region) Spinner join_region;

    @BindView(R.id.btn_join_ok) Button btn_join_ok;//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_join_check_id)
    public void btnJoinCheckId() {

    }

    @OnClick(R.id.btn_join_mail_check)
    public void btnJoinMailCheck() {

    }

    @OnClick(R.id.btn_join_ok)
    public void btnJoinOk() {
        String loginId = "11";
        String loginPw = "1";
        String name = "1";
        String birthday = "1";
        int gender = 1;
        String local ="1";
        String email = "psc106@naver.com";
        String phone = "1";

        final LoadingDialog loadingDialog = LoadingDialog.getInstance();
        loadingDialog.progressON(this, "메일 발송중");

        Call<Void> observer = RetrofitService.getInstance().getRetrofitRequest().insertMember(loginId, loginPw, name, birthday, gender, local, email, phone);
        observer.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "가입?", Toast.LENGTH_SHORT).show();
                    loadingDialog.progressOFF();
                    finish();
                } else {
                    Log.d("로그", "onResponse: fail");
                    loadingDialog.progressOFF();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
                loadingDialog.progressOFF();
            }
        });


    }

    DrawerMenu drawerMenu;
    @Override
    protected void onResume() {
        super.onResume();

        if (drawerMenu == null) {
            drawerMenu = DrawerMenu.addMenu(this, R.id.join_menu, R.id.join_drawer);
        } else {
            drawerMenu.restartMenu(this, R.id.join_menu, R.id.join_drawer);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activity = null;
    }
}
