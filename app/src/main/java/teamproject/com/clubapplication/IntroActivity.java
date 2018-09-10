package teamproject.com.clubapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamproject.com.clubapplication.data.Member;
import teamproject.com.clubapplication.utils.LoginService;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;

public class IntroActivity extends AppCompatActivity {
    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();

        SharedPreferences sharedPreferences = getSharedPreferences("login_setting", Context.MODE_PRIVATE);
        String pw = sharedPreferences.getString("user_pw_save", null);
        String id = sharedPreferences.getString("user_id_save", null);
        if(!TextUtils.isEmpty(pw) && !TextUtils.isEmpty(id)) {
            // 아이디 비밀번호가 입력되었는지 체크 후에 이동
            Call<Member> observer = RetrofitService.getInstance().getRetrofitRequest().selectLoginUser(id, pw);
            observer.enqueue(new Callback<Member>() {
                @Override
                public void onResponse(Call<Member> call, Response<Member> response) {
                    if (response.isSuccessful()) {
                        Member member = response.body();
                        if (member != null) {
                            LoginService.getInstance().login(null, member);
                        } else {
                            //포커스?
                            //리셋?
                        }
                    } else {
                    }
                }

                @Override
                public void onFailure(Call<Member> call, Throwable t) {

                }
            });
        }

//
//        final Animation ani_intro = AnimationUtils.loadAnimation(IntroActivity.this, R.anim.fade_out);
//        final Intent intent = new Intent(IntroActivity.this,MainActivity.class);
//
//        intro.startAnimation(ani_intro);
//        ani_intro.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                startActivity(intent);
//                intro.setVisibility(View.INVISIBLE);
//                finish();
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });


    }
}
