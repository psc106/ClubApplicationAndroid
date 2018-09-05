package teamproject.com.clubapplication.adapter;

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

public class joinMemberListviewAdapter extends BaseAdapter {

    ArrayList<?>arrayList;

    public joinMemberListviewAdapter(ArrayList<?> arrayList) {
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_group_manage_member_join, parent, false);

            holder= new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        //이미지 , 이름 , 나이 , 성별 , 지역


        return convertView;


    }
    static class Holder {
        @BindView(R.id.group_manage_member_join_img)
        ImageView groupManageMemberJoinImg;
        @BindView(R.id.group_manage_member_txt_name)
        TextView groupManageMemberTxtName;
        @BindView(R.id.group_manage_member_btn_join_no)
        Button groupManageMemberBtnJoinNo;
        @BindView(R.id.group_manage_member_btn_join_yes)
        Button groupManageMemberBtnJoinYes;
        @BindView(R.id.group_manage_member_txt_age)
        TextView groupManageMemberTxtAge;
        @BindView(R.id.group_manage_member_txt_gender)
        TextView groupManageMemberTxtGender;
        @BindView(R.id.group_manage_member_txt_location)
        TextView groupManageMemberTxtLocation;

        Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
