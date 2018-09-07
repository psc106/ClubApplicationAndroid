package teamproject.com.clubapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.data.Notice;

public class GroupHomeListviewAdapter extends BaseAdapter {

   ArrayList<Notice>arrayList;

    public GroupHomeListviewAdapter(ArrayList<Notice> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_group_home_notice, parent, false);

            holder= new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        //날짜 , 공지

        return convertView;

    }

    class Holder {
        @BindView(R.id.lv_group_home_notice_txt_date)
        TextView lvGroupHomeNoticeTxtDate;
        @BindView(R.id.lv_group_home_notice_txt_notice)
        TextView lvGroupHomeNoticeTxtNotice;

        Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
