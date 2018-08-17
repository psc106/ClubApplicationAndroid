package teamproject.com.clubapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamproject.com.clubapplication.adapter.MyAlarmListviewAdapter;
import teamproject.com.clubapplication.data.Alarm;
import teamproject.com.clubapplication.data.TestData;
import teamproject.com.clubapplication.utils.LoginService;
import teamproject.com.clubapplication.utils.bus.BusProvider;
import teamproject.com.clubapplication.utils.bus.event.LoginEvent;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;

public class MyAlarmActivity extends AppCompatActivity {
    public static Activity activity;

    @BindView(R.id.myAlarm_listV)
    ListView listView;

    MyAlarmListviewAdapter listviewAdapter;
    ArrayList<Alarm> arrayList;

    LoginService loginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_alarm);
        ButterKnife.bind(this);
        loginService = LoginService.getInstance();

        arrayList = new ArrayList<>();
        Call<ArrayList<Alarm>> observer = RetrofitService.getInstance().getRetrofitRequest().selectMyAlarm(loginService.getMember().getId());
        observer.enqueue(new Callback<ArrayList<Alarm>>() {
            @Override
            public void onResponse(Call<ArrayList<Alarm>> call, Response<ArrayList<Alarm>> response) {
                if (response.isSuccessful()) {
                    arrayList.addAll(response.body());
                } else {
                    Log.d("로그", "onResponse: fail");
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Alarm>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        listviewAdapter = new MyAlarmListviewAdapter(arrayList);
        listView.setAdapter(listviewAdapter);
    }

    DrawerMenu drawerMenu;
    @Override
    protected void onResume() {
        super.onResume();

        if (drawerMenu == null) {
            drawerMenu = DrawerMenu.addMenu(this, R.id.myAlarm_menu, R.id.myAlarm_drawer);
        } else {
            drawerMenu.restartMenu(this, R.id.myAlarm_menu, R.id.myAlarm_drawer);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activity = null;
    }
}
