package teamproject.com.clubapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.login_id) EditText login_id;
    @BindView(R.id.login_pw) EditText login_pw;
    @BindView(R.id.btn_login) Button btn_login;

    @BindView(R.id.login_find_id) TextView login_find_id;
    @BindView(R.id.login_find_pw) TextView login_find_pw;
    @BindView(R.id.login_new_join) TextView login_new_join;

    private DrawerMenu drawerMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);



    }

    @OnClick(R.id.btn_login)
    public void btnLogin() {
        // 아이디 비밀번호가 입력되었는지 체크 후에 이동

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
    @Override
    protected void onResume() {
        super.onResume();
        if (drawerMenu == null) {
            drawerMenu = DrawerMenu.addMenu(this,R.id.login_menu, R.id.login_drawer);
        } else {
            drawerMenu.restartMenu(this, R.id.login_menu, R.id.login_drawer);
        }
    }

}
