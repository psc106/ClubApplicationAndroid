package teamproject.com.clubapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import teamproject.com.clubapplication.R;

public class GroupManageMemberCheckAdapter extends BaseAdapter {

    public GroupManageMemberCheckAdapter(){

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
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_group_manage_member_check,parent,false);
            holder.group_manage_member_btn_check_ban=convertView.findViewById(R.id.group_manage_member_btn_check_ban);
            holder.group_manage_member_check_img=convertView.findViewById(R.id.group_manage_member_check_img);
            holder.group_manage_member_btn_check_nominate=convertView.findViewById(R.id.group_manage_member_btn_check_nominate);
            holder.group_manage_member_check_txt_name=convertView.findViewById(R.id.group_manage_member_check_txt_name);
            holder.group_manage_member_check_txt_age=convertView.findViewById(R.id.group_manage_member_check_txt_age);
            holder.group_manage_member_check_txt_gender=convertView.findViewById(R.id.group_manage_member_check_txt_gender);
            holder.group_manage_member_check_txt_location=convertView.findViewById(R.id.group_manage_member_check_txt_location);
            convertView.setTag(holder);
        }else{
            holder=(Holder) convertView.getTag();
        }

        //이미지 , 이름 , 나이 , 성별 , 지역



        return convertView;



    }
    public class Holder{

        ImageView group_manage_member_check_img;
        Button group_manage_member_btn_check_ban;
        Button group_manage_member_btn_check_nominate;
        TextView group_manage_member_check_txt_name;
        TextView group_manage_member_check_txt_age;
        TextView group_manage_member_check_txt_gender;
        TextView group_manage_member_check_txt_location;

    }
}
