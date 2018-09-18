package teamproject.com.clubapplication.adapter;

import android.content.Context;
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
import teamproject.com.clubapplication.data.Club;
import teamproject.com.clubapplication.data.ClubView;
import teamproject.com.clubapplication.db.DBManager;
import teamproject.com.clubapplication.utils.CommonUtils;
import teamproject.com.clubapplication.utils.glide.GlideApp;

public class SearchGroupListviewAdapter extends BaseAdapter {

    ArrayList<ClubView> arrayList;
    Context context;
    DBManager dbManager;

    public SearchGroupListviewAdapter(Context context, ArrayList<ClubView> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
        dbManager = new DBManager(context, DBManager.DB_NAME, null, DBManager.CURRENT_VERSION);
    }

    //데이터 받아오기

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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_search_group, parent, false);
            holder = new Holder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        ClubView clubView = (ClubView)getItem(position);

        //이미지 , 모임이름 , 모임장 , 인원수 , 주제 , 장소
        holder.lvSearchGroupCategory.setText(dbManager.getCategoryFromId(clubView.getCategory_id()));
        holder.lvSearchGroupCount.setText(clubView.getCur_people()+"/"+clubView.getMax_people());
        holder.lvSearchGroupLocation.setText(clubView.getLocal());
        holder.lvSearchGroupMaker.setText(clubView.getNickname());
        holder.lvSearchGroupTitle.setText(clubView.getName());
        GlideApp.with(context).load(CommonUtils.serverURL+CommonUtils.attachPath+clubView.getImgUrl()).placeholder(R.drawable.club_default).error(R.drawable.club_default).skipMemoryCache(true).centerCrop().into(holder.lvSearchGroupImg);

        return convertView;
    }


    class Holder {
        @BindView(R.id.lv_search_group_img)
        ImageView lvSearchGroupImg;
        @BindView(R.id.lv_search_group_title)
        TextView lvSearchGroupTitle;
        @BindView(R.id.lv_search_group_maker)
        TextView lvSearchGroupMaker;
        @BindView(R.id.lv_search_group_count)
        TextView lvSearchGroupCount;
        @BindView(R.id.lv_search_group_category)
        TextView lvSearchGroupCategory;
        @BindView(R.id.lv_search_group_location)
        TextView lvSearchGroupLocation;

        Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
