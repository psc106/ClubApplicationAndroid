package teamproject.com.clubapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;



import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import teamproject.com.clubapplication.R;

import teamproject.com.clubapplication.data.Alarm;



public class MyAlarmListviewAdapter extends BaseAdapter {

    ArrayList<Alarm> list;
    public MyAlarmListviewAdapter(ArrayList<Alarm> list) {
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

        Holder holder ;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_my_alarm, parent, false);
            holder= new Holder(convertView);
            convertView.setTag(holder);
        }else{
            holder=(Holder) convertView.getTag();
        }


        return convertView;
    }

    static class Holder {
        @BindView(R.id.lv_my_alarm_img)
        ImageView lvMyAlarmImg;
        @BindView(R.id.lv_my_alarm_date)
        TextView lvMyAlarmDate;
        @BindView(R.id.lv_my_alarm_txt)
        TextView lvMyAlarmTxt;

        Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
