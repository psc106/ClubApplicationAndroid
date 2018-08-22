package teamproject.com.clubapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import teamproject.com.clubapplication.R;

public class SearchGroupListviewAdapter extends BaseAdapter {

    public SearchGroupListviewAdapter(){

    }

//데이터 받아오기

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
                convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_search_group,parent,false);
                holder.lv_search_group_img=convertView.findViewById(R.id.lv_search_group_img);
                holder.lv_search_group_title=convertView.findViewById(R.id.lv_search_group_title);
                holder.lv_search_group_maker=convertView.findViewById(R.id.lv_search_group_maker);
                holder.lv_search_group_count=convertView.findViewById(R.id.lv_search_group_count);
                holder.lv_search_group_category=convertView.findViewById(R.id.lv_search_group_category);
                holder.lv_search_group_location=convertView.findViewById(R.id.lv_search_group_location);

                convertView.setTag(holder);
        }else{
                holder=(Holder) convertView.getTag();
        }

        //이미지 , 모임이름 , 모임장 , 인원수 , 주제 , 장소


        return convertView;



        }

        public class Holder{
                ImageView lv_search_group_img;
                TextView lv_search_group_title;
                TextView lv_search_group_maker;
                TextView lv_search_group_count;
                TextView lv_search_group_category;
                TextView lv_search_group_location;
        }
}
