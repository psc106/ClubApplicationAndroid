package teamproject.com.clubapplication;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import teamproject.com.clubapplication.utils.SaturdayDecorator;
import teamproject.com.clubapplication.utils.SundayDecorator;

public class MyCalendar extends AppCompatActivity {


    @BindView(R.id.my_calendar_menu)
    FrameLayout myCalendarMenu;
    @BindView(R.id.my_calendar_drawer)
    DrawerLayout myCalendarDrawer;
    private DrawerMenu drawerMenu;
    @BindView(R.id.my_calendar)
    MaterialCalendarView myCalendar;
    @BindView(R.id.lv_group_calendar)
    ListView lvGroupCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_calendar);
        ButterKnife.bind(this);

        myCalendar.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2018, 0, 1))
                .setMaximumDate(CalendarDay.from(2030, 11, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        myCalendar.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator());


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
