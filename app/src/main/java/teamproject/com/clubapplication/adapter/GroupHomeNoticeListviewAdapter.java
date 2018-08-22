package teamproject.com.clubapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import teamproject.com.clubapplication.R;

public class GroupHomeNoticeListviewAdapter extends BaseAdapter {

    public GroupHomeNoticeListviewAdapter(){

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
        Holder holder = new Holder();
        if(convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_group_home_notice,parent,false);
            holder.lv_group_home_notice_txt_date = convertView.findViewById(R.id.lv_group_home_notice_txt_date);
            holder.lv_group_home_notice_txt_notice = convertView.findViewById(R.id.lv_group_home_notice_txt_notice);
            convertView.setTag(holder);
        }else{
            holder=(Holder) convertView.getTag();
        }
        //날짜 , 공지

        return convertView;

    }
    public class Holder{
        TextView lv_group_home_notice_txt_date;
        TextView lv_group_home_notice_txt_notice;

    }
}
