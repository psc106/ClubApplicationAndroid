package teamproject.com.clubapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import teamproject.com.clubapplication.R;

public class GroupCalendarAdapter extends BaseAdapter {

    public GroupCalendarAdapter() {
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_group_calendar, parent, false);
            holder= new Holder(convertView);
            convertView.setTag(holder);
        }else{
            holder=(Holder) convertView.getTag();
        }

        return convertView;
    }

    public static class Holder {
        @BindView(R.id.lv_group_calendar_img)
        ImageView lvGroupCalendarImg;
        @BindView(R.id.lv_group_calendar_title)
        TextView lvGroupCalendarTitle;
        @BindView(R.id.lv_group_calendar_btn_join)
        Button lvGroupCalendarBtnJoin;
        @BindView(R.id.lv_group_calendar_maker)
        TextView lvGroupCalendarMaker;
        @BindView(R.id.lv_group_calendar_count)
        TextView lvGroupCalendarCout;
        @BindView(R.id.lv_group_calendar_category)
        TextView lvGroupCalendarCategory;
        @BindView(R.id.lv_group_calendar_location)
        TextView lvGroupCalendarLocation;
        @BindView(R.id.lv_group_calendar_cost)
        TextView lvGroupCalendarCost;

        Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
