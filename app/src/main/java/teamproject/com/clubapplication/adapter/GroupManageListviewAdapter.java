package teamproject.com.clubapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.data.GroupManageMenu;

public class GroupManageListviewAdapter extends BaseAdapter {
    private ArrayList<GroupManageMenu> arrayList;
    public GroupManageListviewAdapter(ArrayList<GroupManageMenu> arrayList) {
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

        return convertView;

    }

    class Holder {

        Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
