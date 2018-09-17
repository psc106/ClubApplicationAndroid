package teamproject.com.clubapplication.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.data.ClubView;
import teamproject.com.clubapplication.db.DBManager;
import teamproject.com.clubapplication.utils.CommonUtils;
import teamproject.com.clubapplication.utils.glide.GlideApp;

public class MyClubListviewAdapter extends BaseAdapter {
    ArrayList<ClubView> list;
    Context context;
    DBManager dbManager;

    public MyClubListviewAdapter(Context context, ArrayList<ClubView> list) {
        this.list = list;
        this.context = context;
        dbManager = new DBManager(context, DBManager.DB_NAME, null, DBManager.CURRENT_VERSION);
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
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_my_group, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        ClubView clubView = (ClubView)getItem(position);

        //이미지 , 모임이름 , 모임장 , 인원수 , 주제 , 장소
        holder.lvMyGroupCategory.setText(dbManager.getCategoryFromId(clubView.getCategory_id()));
        holder.lvMyGroupCount.setText(clubView.getCur_people()+"/"+clubView.getMax_people());
        holder.lvMyGroupLocation.setText(clubView.getLocal());
        holder.lvMyGroupMaker.setText(clubView.getNickname());
        holder.lvMyGroupTitle.setText(clubView.getName());
        GlideApp.with(context).load(CommonUtils.serverURL+CommonUtils.attachPath+clubView.getImgUrl()).centerCrop().into(holder.lvMyGroupImg);


        return convertView;
    }

    class Holder {
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
