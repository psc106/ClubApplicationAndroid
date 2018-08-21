package teamproject.com.clubapplication.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.data.Alarm;
import teamproject.com.clubapplication.data.CalendarSchedule;
import teamproject.com.clubapplication.data.Schedule;

public class MyCalendarGridviewAdapter extends BaseAdapter {
    ArrayList<CalendarSchedule> list;
    public MyCalendarGridviewAdapter(ArrayList<CalendarSchedule> list) {
        this.list = list;
    }

    String[] weekEng = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    String[] weekKor = {"일", "월", "화", "수", "목", "금", "토"};

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Horder horder;
        if(convertView==null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gridview_my_calendar, parent, false);
            horder = new Horder(convertView);
            convertView.setTag(horder);
        } else {
            horder=(Horder)convertView.getTag();
        }

        if(position<7){
            horder.date.setText(weekEng[position]);
            horder.scheduleCount.setVisibility(View.GONE);
        } else {
            if(horder.scheduleCount.getVisibility()==View.GONE)
                horder.scheduleCount.setVisibility(View.VISIBLE);
            horder.background.setBackgroundColor(Color.TRANSPARENT);

            if (list.get(position).getDay()!=null &&!list.get(position).getDay().equals("")) {
                ArrayList<Schedule> schedules = list.get(position).getTodaySchedule();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                Calendar calendar = Calendar.getInstance();
                int today = Integer.parseInt(sdf.format(calendar.getTime()));
                int scheduleDay = Integer.parseInt(list.get(position).getDay());
                horder.date.setText(list.get(position).getDay().substring(4, 6) + "/" + list.get(position).getDay().substring(6));

                if (schedules.isEmpty()) {
                    horder.scheduleCount.setText("");
                } else {
                    horder.scheduleCount.setText("" + schedules.size());
                    if (today > scheduleDay) {
                        horder.background.setBackgroundColor(Color.parseColor("#787878"));
                    } else if (today == scheduleDay) {
                        horder.background.setBackgroundColor(Color.parseColor("#AAFA82"));
                    } else {
                        horder.background.setBackgroundColor(Color.parseColor("#BEEFFF"));
                    }
                }
            }
        }
        return convertView;
    }

    class Horder {
        @BindView(R.id.calendar_layout_Background)
        RelativeLayout background;
        @BindView(R.id.calendar_txt_Date)
        TextView date;
        @BindView(R.id.calendar_txt_ScheduleCount)
        TextView scheduleCount;

        public Horder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
