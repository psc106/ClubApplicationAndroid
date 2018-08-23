package teamproject.com.clubapplication;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import teamproject.com.clubapplication.adapter.MyCalendarListviewAdapter;

public class MyCalendarActivity extends AppCompatActivity {


    @BindView(R.id.my_calendar_menu)
    FrameLayout myCalendarMenu;
    @BindView(R.id.my_calendar_drawer)
    DrawerLayout myCalendarDrawer;
    private DrawerMenu drawerMenu;
    @BindView(R.id.my_calendar)
    MaterialCalendarView myCalendar;
    @BindView(R.id.lv_group_calendar)
    ListView lvGroupCalendar;

    ArrayList<?>arrayList;
    MyCalendarListviewAdapter myCalendarListviewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_calendar);
        ButterKnife.bind(this);

        arrayList=new ArrayList<>();
        myCalendarListviewAdapter = new MyCalendarListviewAdapter(arrayList);
        lvGroupCalendar.setAdapter(myCalendarListviewAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (drawerMenu == null) {
            drawerMenu = DrawerMenu.addMenu(this, R.id.my_calendar_menu, R.id.my_calendar_drawer);
        } else {
            drawerMenu.restartMenu(this, R.id.my_calendar_menu, R.id.my_calendar_drawer);
        }
    }
}
