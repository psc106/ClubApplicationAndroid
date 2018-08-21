package teamproject.com.clubapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.data.Alarm;
import teamproject.com.clubapplication.data.Schedule;

public class MyCalendarListViewAdapter extends BaseAdapter {
    ArrayList<Schedule> list;
    public MyCalendarListViewAdapter(ArrayList<Schedule> list) {
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
        Horder horder;
        if(convertView==null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_my_calendar, parent, false);
            horder = new Horder(convertView);
            convertView.setTag(horder);
        } else {
            horder=(Horder)convertView.getTag();
        }
        horder.date.setText(list.get(position).getCreate_date());
        return convertView;
    }

    class Horder {
        @BindView(R.id.schedule_txt_date)
        TextView date;

        public Horder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
