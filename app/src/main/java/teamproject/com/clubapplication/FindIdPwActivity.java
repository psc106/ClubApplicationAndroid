package teamproject.com.clubapplication;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import teamproject.com.clubapplication.fragment.IdFindFragment;
import teamproject.com.clubapplication.fragment.PwFindFragment;

public class FindIdPwActivity extends AppCompatActivity {
    Fragment fragment;


    @BindView(R.id.btn_find_id) Button btn_find_id;
    @BindView(R.id.btn_find_pw) Button btn_find_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id_pw);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Integer findIdentifier = intent.getIntExtra("findValue", 1);

        if(findIdentifier == 1) {
            fragment = new IdFindFragment();
        }else if(findIdentifier == 2) {
            fragment = new PwFindFragment();
        }

        goFragment();


    }


    @OnClick(R.id.btn_find_id)
    public void onClick_btn_find_id(View view) {
        fragment = new IdFindFragment();

        goFragment();
    }

    @OnClick(R.id.btn_find_pw)
    public void onClick_btn_find_pw(View view) {
        fragment = new PwFindFragment();

        goFragment();
    }

    public void goFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.find_container, fragment);
        ft.commit();
    }

}
