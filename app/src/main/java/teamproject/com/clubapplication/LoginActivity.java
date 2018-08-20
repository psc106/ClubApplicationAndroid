package teamproject.com.clubapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamproject.com.clubapplication.data.Member;
import teamproject.com.clubapplication.utils.LoginService;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;

public class LoginActivity extends AppCompatActivity {
    public static Activity activity;

    @BindView(R.id.login_id) EditText login_id;
    @BindView(R.id.login_pw) EditText login_pw;
    @BindView(R.id.btn_login) Button btn_login;

    @BindView(R.id.login_find_id) TextView login_find_id;
    @BindView(R.id.login_find_pw) TextView login_find_pw;
    @BindView(R.id.login_new_join) TextView login_new_join;

    LoginService loginService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginService = LoginService.getInstance();
    }

    @OnClick(R.id.btn_login)
    public void btnLogin() {
        String id, pw;
        if(login_id.getText()!=null && !login_id.getText().toString().equals("")){
            id = login_id.getText().toString();
        } else {
            return;
        }

        if(login_pw.getText()!=null && !login_pw.getText().toString().equals("")){
            pw = login_pw.getText().toString();
        } else {
            return;
        }

        // 아이디 비밀번호가 입력되었는지 체크 후에 이동
        Call<Member> observer = RetrofitService.getInstance().getRetrofitRequest().selectLoginUser(id, pw);
        observer.enqueue(new Callback<Member>() {
            @Override
            public void onResponse(Call<Member> call, Response<Member> response) {
                if(response.isSuccessful()){
                    Member member = response.body();
                    if(member!=null) {
                        loginService.login(activity, member);

                    } else {
                        //포커스?
                        //리셋?
                    }
                }else {
                }
            }

            @Override
            public void onFailure(Call<Member> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @OnClick(R.id.login_find_id)
    public void loginFindId() {
        Intent intent = new Intent(LoginActivity.this, FindIdPwActivity.class);
        intent.putExtra("findValue", 1);
        startActivity(intent);
    }

    @OnClick(R.id.login_find_pw)
    public void loginFindPw() {
        Intent intent = new Intent(LoginActivity.this, FindIdPwActivity.class);
        intent.putExtra("findValue", 2);
        startActivity(intent);
    }

    @OnClick(R.id.login_new_join)
    public void loginNewJoin() {
        Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
        startActivity(intent);
    }


    DrawerMenu drawerMenu;
    @Override
    protected void onResume() {
        super.onResume();

        if (drawerMenu == null) {
            drawerMenu = DrawerMenu.addMenu(this, R.id.login_menu, R.id.login_drawer);
        } else {
            drawerMenu.restartMenu(this, R.id.login_menu, R.id.login_drawer);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activity = null;
    }
}
