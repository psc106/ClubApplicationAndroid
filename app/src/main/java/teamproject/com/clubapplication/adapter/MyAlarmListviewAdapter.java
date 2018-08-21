package teamproject.com.clubapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

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
        Horder horder;
        if(convertView==null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_my_alarm, parent, false);
            horder = new Horder(convertView);
            convertView.setTag(horder);
        } else {
            horder=(Horder)convertView.getTag();
        }

        return convertView;
    }

    class Horder {
        Horder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
