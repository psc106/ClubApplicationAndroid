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
import teamproject.com.clubapplication.data.MemberView;

public class MemberWaitingListviewAdapter extends BaseAdapter {

    ArrayList<MemberView> arrayList;

    public MemberWaitingListviewAdapter(ArrayList<MemberView> arrayList) {
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_group_manage_member_check, parent, false);

            holder= new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        //이미지 , 이름 , 나이 , 성별 , 지역


        return convertView;


    }

    static class Holder {
        @BindView(R.id.group_manage_member_check_img)
        ImageView groupManageMemberCheckImg;
        @BindView(R.id.group_manage_member_check_txt_name)
        TextView groupManageMemberCheckTxtName;
        @BindView(R.id.group_manage_member_btn_check_ban)
        Button groupManageMemberBtnCheckBan;
        @BindView(R.id.group_manage_member_btn_check_nominate)
        Button groupManageMemberBtnCheckNominate;
        @BindView(R.id.group_manage_member_check_txt_age)
        TextView groupManageMemberCheckTxtAge;
        @BindView(R.id.group_manage_member_check_txt_gender)
        TextView groupManageMemberCheckTxtGender;
        @BindView(R.id.group_manage_member_check_txt_location)
        TextView groupManageMemberCheckTxtLocation;

        Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
