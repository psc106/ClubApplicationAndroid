package teamproject.com.clubapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JoinActivity extends AppCompatActivity {
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

    private DrawerMenu drawerMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

    }
    @Override
    protected void onResume() {
        super.onResume();
        if (drawerMenu == null) {
            drawerMenu = DrawerMenu.addMenu(this,R.id.join_menu, R.id.join_drawer);
        } else {
            drawerMenu.restartMenu(this, R.id.join_menu, R.id.join_drawer);
        }
    }
}
