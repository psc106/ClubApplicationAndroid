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
import teamproject.com.clubapplication.utils.CommonUtils;
import teamproject.com.clubapplication.utils.glide.GlideApp;

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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_group_manage_member_wait, parent, false);

            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        MemberView currMember = (MemberView) getItem(position);
        holder.groupManageMemberTxtName.setText(currMember.getName());
        holder.groupManageMemberTxtAge.setText(currMember.getBirthday());
        holder.groupManageMemberTxtGender.setText(currMember.getGender() == 0 ? "남자" : "여자");
        holder.groupManageMemberTxtLocation.setText(currMember.getLocal());
        GlideApp.with(parent.getContext()).load(CommonUtils.serverURL + CommonUtils.attachPath + currMember.getImgUrl()).placeholder(R.drawable.profile).skipMemoryCache(true).error(R.drawable.profile).circleCrop().into(holder.groupManageMemberJoinImg);


        return convertView;


    }

    class Holder {
        @BindView(R.id.group_manage_member_join_img)
        ImageView groupManageMemberJoinImg;
        @BindView(R.id.group_manage_member_txt_name)
        TextView groupManageMemberTxtName;
        @BindView(R.id.group_manage_member_txt_age)
        TextView groupManageMemberTxtAge;
        @BindView(R.id.group_manage_member_txt_gender)
        TextView groupManageMemberTxtGender;
        @BindView(R.id.group_manage_member_txt_location)
        TextView groupManageMemberTxtLocation;

        @BindView(R.id.group_manage_member_btn_join_yes)
        Button groupManageMemberBtnJoinYes;
        @BindView(R.id.group_manage_member_btn_join_no)
        Button groupManageMemberBtnJoinNo;
        Holder(View view) {
            ButterKnife.bind(this, view);
            groupManageMemberBtnJoinYes.setFocusable(false);
            groupManageMemberBtnJoinYes.setFocusableInTouchMode(false);
            groupManageMemberBtnJoinNo.setFocusable(false);
            groupManageMemberBtnJoinNo.setFocusableInTouchMode(false);
        }
    }

}
