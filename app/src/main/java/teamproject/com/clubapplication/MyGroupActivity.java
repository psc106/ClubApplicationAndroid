package teamproject.com.clubapplication;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamproject.com.clubapplication.adapter.MyClubListviewAdapter;
import teamproject.com.clubapplication.data.Club;
import teamproject.com.clubapplication.data.Member;
import teamproject.com.clubapplication.utils.DrawerMenu;
import teamproject.com.clubapplication.utils.LoginService;
import teamproject.com.clubapplication.utils.RefreshData;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;

public class MyGroupActivity extends AppCompatActivity implements RefreshData {
    public static Activity activity;

    @BindView(R.id.myGroup_listV)
    ListView listView;

    MyClubListviewAdapter listviewAdapter;
    ArrayList<Club> arrayList;

    LoginService loginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_group);
        ButterKnife.bind(this);
        loginService = LoginService.getInstance();

        arrayList = new ArrayList<>();

        listviewAdapter = new MyClubListviewAdapter(arrayList);
        listView.setAdapter(listviewAdapter);
    }

    DrawerMenu drawerMenu;
    @Override
    protected void onResume() {
        super.onResume();

        if (drawerMenu == null) {
            drawerMenu = DrawerMenu.addMenu(this, R.id.myGroup_menu, R.id.myGroup_drawer);
        } else {
            drawerMenu.restartMenu(this, R.id.myGroup_menu, R.id.myGroup_drawer);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        activity = null;
    }

    @Override
    public void refresh() {
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

        Call<ArrayList<Club>> observer = RetrofitService.getInstance().getRetrofitRequest().selectMyClub(loginService.getMember().getId());
        observer.enqueue(new Callback<ArrayList<Club>>() {
            @Override
            public void onResponse(Call<ArrayList<Club>> call, Response<ArrayList<Club>> response) {
                if (response.isSuccessful()) {
                    arrayList.addAll(response.body());
                } else {
                    Log.d("로그", "onResponse: fail");
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Club>> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }
}
