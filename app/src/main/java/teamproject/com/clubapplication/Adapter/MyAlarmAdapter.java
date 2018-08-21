package teamproject.com.clubapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import teamproject.com.clubapplication.R;

public class MyAlarmAdapter extends BaseAdapter {


    public MyAlarmAdapter() {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
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
