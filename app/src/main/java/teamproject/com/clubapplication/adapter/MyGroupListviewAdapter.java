package teamproject.com.clubapplication.adapter;

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

public class MyGroupListviewAdapter extends BaseAdapter {

    public MyGroupListviewAdapter() {
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_my_group, parent, false);
            holder= new Holder(convertView);
            convertView.setTag(holder);
        }else{
            holder=(Holder) convertView.getTag();
        }

        return convertView;
    }


    static class Holder {
        @BindView(R.id.lv_my_group_img)
        ImageView lvMyGroupImg;
        @BindView(R.id.lv_my_group_title)
        TextView lvMyGroupTitle;
        @BindView(R.id.lv_my_group_btn_out)
        Button lvMyGroupBtnOut;
        @BindView(R.id.lv_my_group_maker)
        TextView lvMyGroupMaker;
        @BindView(R.id.lv_my_group_count)
        TextView lvMyGroupCount;
        @BindView(R.id.lv_my_group_category)
        TextView lvMyGroupCategory;
        @BindView(R.id.lv_my_group_location)
        TextView lvMyGroupLocation;

        Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
