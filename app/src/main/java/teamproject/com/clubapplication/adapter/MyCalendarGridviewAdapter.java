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
        Holder holder;
        if(convertView==null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gridview_my_calendar, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder=(Holder)convertView.getTag();
        }

        if(position<7){
            holder.date.setText(weekEng[position]);
            holder.scheduleCount.setVisibility(View.GONE);
        } else {
            if(holder.scheduleCount.getVisibility()==View.GONE)
                holder.scheduleCount.setVisibility(View.VISIBLE);
            holder.background.setBackgroundColor(Color.TRANSPARENT);

            if (list.get(position).getDay()!=null &&!list.get(position).getDay().equals("")) {
                ArrayList<Schedule> schedules = list.get(position).getTodaySchedule();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                Calendar calendar = Calendar.getInstance();
                int today = Integer.parseInt(sdf.format(calendar.getTime()));
                int scheduleDay = Integer.parseInt(list.get(position).getDay());
                holder.date.setText(list.get(position).getDay().substring(4, 6) + "/" + list.get(position).getDay().substring(6));

                if (schedules.isEmpty()) {
                    holder.scheduleCount.setText("");
                } else {
                    holder.scheduleCount.setText("" + schedules.size());
                    if (today > scheduleDay) {
                        holder.background.setBackgroundColor(Color.parseColor("#787878"));
                    } else if (today == scheduleDay) {
                        holder.background.setBackgroundColor(Color.parseColor("#AAFA82"));
                    } else {
                        holder.background.setBackgroundColor(Color.parseColor("#BEEFFF"));
                    }
                }
            }
        }
        return convertView;
    }

    class Holder {
        @BindView(R.id.calendar_layout_Background)
        RelativeLayout background;
        @BindView(R.id.calendar_txt_Date)
        TextView date;
        @BindView(R.id.calendar_txt_ScheduleCount)
        TextView scheduleCount;

        public Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
