package teamproject.com.clubapplication.adapter;

import android.app.Activity;
import android.content.Intent;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamproject.com.clubapplication.GroupActivity;
import teamproject.com.clubapplication.MainActivity;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.data.ClubMemberClass;
import teamproject.com.clubapplication.data.MemberView;
import teamproject.com.clubapplication.utils.CommonUtils;
import teamproject.com.clubapplication.utils.LoginService;
import teamproject.com.clubapplication.utils.bus.BusProvider;
import teamproject.com.clubapplication.utils.bus.event.ClubLoadEvent;
import teamproject.com.clubapplication.utils.glide.GlideApp;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;

public class MemberJoinListviewAdapter extends BaseAdapter {

    ArrayList<MemberView> arrayList;
    Long clubId;

    public MemberJoinListviewAdapter(ArrayList<MemberView> arrayList, Long clubId) {
        this.arrayList = arrayList;
        this.clubId = clubId;
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_group_manage_member_join, parent, false);

            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        //이미지 , 이름 , 나이 , 성별 , 지역

        final MemberView currMember = (MemberView) getItem(position);
        holder.groupManageMemberCheckTxtName.setText(currMember.getName());
        holder.groupManageMemberCheckTxtAge.setText(currMember.getBirthday());
        holder.groupManageMemberCheckTxtGender.setText(currMember.getGender() == 1 ? "남자" : "여자");
        holder.groupManageMemberCheckTxtLocation.setText(currMember.getLocal());
        GlideApp.with(parent.getContext()).load(CommonUtils.serverURL + CommonUtils.attachPath + currMember.getImgUrl()).placeholder(R.drawable.profile).skipMemoryCache(true).error(R.drawable.profile).circleCrop().into(holder.groupManageMemberCheckImg);

        holder.groupManageMemberBtnCheckBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Void> observer = RetrofitService.getInstance().getRetrofitRequest().deleteMember(currMember.getMemberId(), LoginService.getInstance().getMember().getId(), clubId);
                observer.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            arrayList.remove(position);
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });

        holder.groupManageMemberBtnCheckNominate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Void> observer = RetrofitService.getInstance().getRetrofitRequest().updateAdmin(currMember.getMemberId(), LoginService.getInstance().getMember().getId(), clubId);
                observer.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            Call<ClubMemberClass> observer = RetrofitService.getInstance().getRetrofitRequest().selectClub(clubId, LoginService.getInstance().getMember().getId());
                            observer.enqueue(new Callback<ClubMemberClass>() {
                                @Override
                                public void onResponse(Call<ClubMemberClass> call, Response<ClubMemberClass> response) {
                                    if (response.isSuccessful()) {
                                        BusProvider.getInstance().getBus().post(new ClubLoadEvent(response.body()));
                                        ((Activity)parent.getContext()).finish();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ClubMemberClass> call, Throwable t) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });

        return convertView;


    }

    class Holder {

        @BindView(R.id.group_manage_member_check_img)
        ImageView groupManageMemberCheckImg;
        @BindView(R.id.group_manage_member_check_txt_name)
        TextView groupManageMemberCheckTxtName;
        @BindView(R.id.group_manage_member_check_txt_age)
        TextView groupManageMemberCheckTxtAge;
        @BindView(R.id.group_manage_member_check_txt_gender)
        TextView groupManageMemberCheckTxtGender;
        @BindView(R.id.group_manage_member_check_txt_location)
        TextView groupManageMemberCheckTxtLocation;

        @BindView(R.id.group_manage_member_btn_check_ban)
        Button groupManageMemberBtnCheckBan;
        @BindView(R.id.group_manage_member_btn_check_nominate)
        Button groupManageMemberBtnCheckNominate;


        Holder(View view) {
            ButterKnife.bind(this, view);
            groupManageMemberBtnCheckBan.setFocusableInTouchMode(false);
            groupManageMemberBtnCheckBan.setFocusable(false);
            groupManageMemberBtnCheckNominate.setFocusableInTouchMode(false);
            groupManageMemberBtnCheckNominate.setFocusable(false);
        }
    }

}
