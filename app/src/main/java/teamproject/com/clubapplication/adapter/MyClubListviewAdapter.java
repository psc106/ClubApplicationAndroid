package teamproject.com.clubapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.data.Alarm;
import teamproject.com.clubapplication.data.Club;
import teamproject.com.clubapplication.data.ClubView;

public class MyClubListviewAdapter extends BaseAdapter {
    ArrayList<ClubView> list;
    public MyClubListviewAdapter(ArrayList<ClubView> list) {
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
        Holder horder;
        if(convertView==null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_my_group, parent, false);
            horder = new Holder(convertView);
            convertView.setTag(horder);
        } else {
            horder=(Holder)convertView.getTag();
        }

        return convertView;
    }

    class Holder {
        @BindView(R.id.lv_my_group_title)
        TextView groupName;
        @BindView(R.id.lv_my_group_category)
        TextView category;
        @BindView(R.id.lv_my_group_count)
        TextView memberCount;
        @BindView(R.id.lv_my_group_img)
        ImageView profileImg;
        @BindView(R.id.lv_my_group_location)
        TextView local;
        @BindView(R.id.lv_my_group_maker)
        TextView admin;

        Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
