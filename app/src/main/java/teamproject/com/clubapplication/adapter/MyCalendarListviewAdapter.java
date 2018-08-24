package teamproject.com.clubapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.data.Schedule;

public class MyCalendarListviewAdapter extends BaseAdapter {

    ArrayList<Schedule> list;

    public MyCalendarListviewAdapter(ArrayList<Schedule> list) {
        this.list = list;
    }

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
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_my_calendar, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }


        return convertView;
    }

    static class Holder {
        @BindView(R.id.lv_group_calendar_img)
        ImageView lvGroupCalendarImg;
        @BindView(R.id.lv_my_calendar_title)
        TextView lvMyCalendarTitle;
        @BindView(R.id.lv_my_calendar_btn_join)
        Button lvMyCalendarBtnJoin;
        @BindView(R.id.lv_my_calendar_btn_out)
        Button lvMyCalendarBtnOut;
        @BindView(R.id.lv_my_calendar_maker)
        TextView lvMyCalendarMaker;
        @BindView(R.id.lv_my_calendar_date)
        TextView lvMyCalendarDate;
        @BindView(R.id.lv_my_calendar_category)
        TextView lvMyCalendarCategory;
        @BindView(R.id.lv_my_calendar_location)
        TextView lvMyCalendarLocation;
        @BindView(R.id.lv_my_calendar_cost)
        TextView lvMyCalendarCost;

        Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
