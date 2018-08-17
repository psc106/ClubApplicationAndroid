package teamproject.com.clubapplication;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamproject.com.clubapplication.adapter.MyAlarmListviewAdapter;
import teamproject.com.clubapplication.adapter.MyCalendarGridviewAdapter;
import teamproject.com.clubapplication.adapter.MyCalendarListViewAdapter;
import teamproject.com.clubapplication.data.Alarm;
import teamproject.com.clubapplication.data.CalendarSchedule;
import teamproject.com.clubapplication.data.Schedule;
import teamproject.com.clubapplication.utils.LoginService;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;

public class MyCalendarActivity extends AppCompatActivity {
    public static Activity activity;

    @BindView(R.id.myCalendar_gridV)
    GridView gridView;
    @BindView(R.id.myCalendar_listV)
    ListView listView;

    MyCalendarGridviewAdapter gridviewAdapter;
    MyCalendarListViewAdapter listviewAdapter;
    ArrayList<CalendarSchedule> calendarArrayList;
    ArrayList<Schedule> scheduleArrayList;

    LoginService loginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_calendar);
        ButterKnife.bind(this);
        loginService = LoginService.getInstance();
//
        calendarArrayList = new ArrayList<>();
        gridviewAdapter = new MyCalendarGridviewAdapter(new ArrayList<Schedule>());
        gridView.setAdapter(gridviewAdapter);

        scheduleArrayList = new ArrayList<>();
        Call<ArrayList<Schedule>> scheduleObserver =
                RetrofitService.getInstance().getRetrofitRequest().selectMySchedule(loginService.getMember().getId());
        scheduleObserver.enqueue(new Callback<ArrayList<Schedule>>() {
            @Override
            public void onResponse(Call<ArrayList<Schedule>> call, Response<ArrayList<Schedule>> response) {
                if (response.isSuccessful()) {
                    scheduleArrayList.addAll(response.body());
                } else {
                    Log.d("로그", "onResponse: fail");
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Schedule>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        listviewAdapter = new MyCalendarListViewAdapter(scheduleArrayList);
        listView.setAdapter(listviewAdapter);
    }

    DrawerMenu drawerMenu;
    @Override
    protected void onResume() {
        super.onResume();

        if (drawerMenu == null) {
            drawerMenu = DrawerMenu.addMenu(this, R.id.myCalendar_menu, R.id.myCalendar_drawer);
        } else {
            drawerMenu.restartMenu(this, R.id.myCalendar_menu, R.id.myCalendar_drawer);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activity = null;
    }
}
