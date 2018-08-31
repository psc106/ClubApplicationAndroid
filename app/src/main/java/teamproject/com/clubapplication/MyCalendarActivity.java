package teamproject.com.clubapplication;

import android.app.Activity;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;

import android.view.View;

import android.widget.AdapterView;

import android.widget.GridView;

import android.widget.ListView;



import java.util.ArrayList;

import java.util.Calendar;



import butterknife.BindView;

import butterknife.ButterKnife;

import retrofit2.Call;

import retrofit2.Callback;

import retrofit2.Response;

import teamproject.com.clubapplication.adapter.MyCalendarGridviewAdapter;

import teamproject.com.clubapplication.adapter.MyCalendarListviewAdapter;

import teamproject.com.clubapplication.data.CalendarSchedule;

import teamproject.com.clubapplication.data.Member;
import teamproject.com.clubapplication.data.Schedule;

import teamproject.com.clubapplication.utils.DrawerMenu;

import teamproject.com.clubapplication.utils.LoginService;

import teamproject.com.clubapplication.utils.RefreshData;

import teamproject.com.clubapplication.utils.retrofit.RetrofitService;

public class MyCalendarActivity extends AppCompatActivity implements RefreshData {

    public static Activity activity;
    @BindView(R.id.myCalendar_gridV)
    GridView gridView;
    @BindView(R.id.myCalendar_listV)
    ListView listView;

    MyCalendarGridviewAdapter gridviewAdapter;
    ArrayList<CalendarSchedule> calendarArrayList;
    MyCalendarListviewAdapter listviewAdapter;
    ArrayList<Schedule> scheduleArrayList;

    LoginService loginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_calendar);
        ButterKnife.bind(this);
        loginService = LoginService.getInstance();

        calendarArrayList = new ArrayList<>();
        gridviewAdapter = new MyCalendarGridviewAdapter(calendarArrayList);
        gridView.setAdapter(gridviewAdapter);

        scheduleArrayList = new ArrayList<>();
        listviewAdapter = new MyCalendarListviewAdapter(scheduleArrayList);
        listView.setAdapter(listviewAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("로그", "onItemClick: "+1);
                ArrayList<Schedule> daySchedules = calendarArrayList.get(position).getTodaySchedule();
                if(daySchedules==null || daySchedules.isEmpty()) {

                } else {
                    Log.d("로그", "onItemClick: "+3);
                    scheduleArrayList.clear();
                    scheduleArrayList.addAll(daySchedules);
                    listviewAdapter.notifyDataSetChanged();
                }
            }

        });
    }

    DrawerMenu drawerMenu;
    @Override
    protected void onResume() {
        super.onResume();
        refresh();
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

    @Override
    public void refresh() {

        Calendar calendar =  Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        Call<ArrayList<Schedule>> scheduleObserver =
                RetrofitService.getInstance().getRetrofitRequest().selectMySchedule(loginService.getMember().getId());
        scheduleObserver.enqueue(new Callback<ArrayList<Schedule>>() {
            @Override
            public void onResponse(Call<ArrayList<Schedule>> call, Response<ArrayList<Schedule>> response) {
                if (response.isSuccessful()) {
                    scheduleArrayList.clear();
                    scheduleArrayList.addAll(response.body());
                    listviewAdapter.notifyDataSetChanged();
                } else {
                    Log.d("로그", "onResponse: fail");
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Schedule>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        Call<ArrayList<CalendarSchedule>> calendarObserver =
                RetrofitService.getInstance().getRetrofitRequest().selectMyCalendar(loginService.getMember().getId(), year, month);
        calendarObserver.enqueue(new Callback<ArrayList<CalendarSchedule>>() {
            @Override
            public void onResponse(Call<ArrayList<CalendarSchedule>> call, Response<ArrayList<CalendarSchedule>> response) {
                calendarArrayList.clear();
                for(int i = 0 ; i < 7; ++i) {
                    calendarArrayList.add(new CalendarSchedule());
                }
                if (response.isSuccessful()) {
                    calendarArrayList.addAll(response.body());
                    gridviewAdapter.notifyDataSetChanged();
                } else {
                    Log.d("로그", "onResponse: fail");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CalendarSchedule>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        Log.d("로그", "refresh: "+year+month+loginService.getMember().getId());
    }
}