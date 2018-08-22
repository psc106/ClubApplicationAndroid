package teamproject.com.clubapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import teamproject.com.clubapplication.R;

public class GroupManageMemberJoinAdapter extends BaseAdapter {

    public GroupManageMemberJoinAdapter(){

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
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_group_manage_member_join,parent,false);
            holder.group_manage_member_join_img=convertView.findViewById(R.id.group_manage_member_join_img);
            holder.group_manage_member_btn_join_no=convertView.findViewById(R.id.group_manage_member_btn_join_no);
            holder.group_manage_member_btn_join_yes=convertView.findViewById(R.id.group_manage_member_btn_join_yes);
            holder.group_manage_member_txt_name=convertView.findViewById(R.id.group_manage_member_txt_name);
            holder.group_manage_member_txt_age=convertView.findViewById(R.id.group_manage_member_txt_age);
            holder.group_manage_member_txt_gender=convertView.findViewById(R.id.group_manage_member_txt_gender);
            holder.group_manage_member_txt_location=convertView.findViewById(R.id.group_manage_member_txt_location);
            convertView.setTag(holder);
        }else{
           holder=(Holder) convertView.getTag();
        }

        //이미지 , 이름 , 나이 , 성별 , 지역



        return convertView;



    }
    public class Holder{

        ImageView group_manage_member_join_img;
        Button group_manage_member_btn_join_no;
        Button group_manage_member_btn_join_yes;
        TextView group_manage_member_txt_name;
        TextView group_manage_member_txt_age;
        TextView group_manage_member_txt_gender;
        TextView group_manage_member_txt_location;

    }
}
