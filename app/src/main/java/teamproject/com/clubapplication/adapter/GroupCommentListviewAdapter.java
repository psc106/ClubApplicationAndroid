package teamproject.com.clubapplication.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.data.CommentView;
import teamproject.com.clubapplication.utils.CommonUtils;
import teamproject.com.clubapplication.utils.LoginService;
import teamproject.com.clubapplication.utils.bus.BusProvider;
import teamproject.com.clubapplication.utils.bus.event.CommentEvent;
import teamproject.com.clubapplication.utils.glide.GlideApp;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;

public class GroupCommentListviewAdapter extends BaseAdapter {
    ArrayList<CommentView> list;
    Context context;
    Long postId;

    public GroupCommentListviewAdapter(Context context, ArrayList<CommentView> list, Long postId) {
        this.list = list;
        this.context = context;
        this.postId = postId;
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_group_comment, parent, false);

            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.groupCommentEdtContent.setVisibility(View.GONE);
        holder.groupCommentTxtContent.setVisibility(View.VISIBLE);
        holder.groupCommentImgBtnDel.setVisibility(View.VISIBLE);

        final CommentView currCommentView = (CommentView)getItem(position);

        holder.groupCommentEdtContent.setText(currCommentView.getContent());
        holder.groupCommentTxtContent.setText(currCommentView.getContent());
        holder.groupCommentTxtDate.setText(currCommentView.getCreate_date());
        holder.groupCommentTxtNickname.setText(currCommentView.getNickname());
        GlideApp.with(context).load(currCommentView.getImgUrl()).centerCrop().placeholder(R.drawable.profile).skipMemoryCache(true).error(R.drawable.profile).into(holder.groupCommentImgProfile);


        holder.groupCommentImgBtnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Boolean> observer = RetrofitService.getInstance().getRetrofitRequest().deleteComment(currCommentView.getId(), LoginService.getInstance().getMember().getId());
                observer.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if(response.isSuccessful()) {
                            list.remove(position);

                            Call<ArrayList<CommentView>> observer = RetrofitService.getInstance().getRetrofitRequest().refreshPostComment(postId, list.size()/7+(list.size()%7==0?0:1), 7);
                            observer.enqueue(new Callback<ArrayList<CommentView>>() {
                                @Override
                                public void onResponse(Call<ArrayList<CommentView>> call, Response<ArrayList<CommentView>> response) {
                                    if (response.isSuccessful()) {
                                        list.clear();
                                        list.addAll(response.body());
                                        notifyDataSetChanged();
                                        CommonUtils.setListviewHeightBasedOnChildren((ListView)parent);
                                    }
                                }

                                @Override
                                public void onFailure(Call<ArrayList<CommentView>> call, Throwable t) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {

                    }
                });
            }
        });


        holder.groupCommentImgBtnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.groupCommentImgBtnDel.getVisibility()==View.VISIBLE) {
                    holder.groupCommentEdtContent.setVisibility(View.VISIBLE);
                    holder.groupCommentTxtContent.setVisibility(View.GONE);
                    holder.groupCommentImgBtnDel.setVisibility(View.GONE);
                    holder.groupCommentEdtContent.requestFocus();
                } else {
                    holder.groupCommentEdtContent.clearFocus();
                    holder.groupCommentEdtContent.setVisibility(View.GONE);
                    holder.groupCommentTxtContent.setVisibility(View.VISIBLE);
                    if(!TextUtils.isEmpty(holder.groupCommentEdtContent.getText()) && !holder.groupCommentEdtContent.getText().toString().equals( holder.groupCommentTxtContent.getText().toString())) {
                        Call<Boolean> observer = RetrofitService.getInstance().getRetrofitRequest().updateComment(currCommentView.getId(), LoginService.getInstance().getMember().getId(), holder.groupCommentEdtContent.getText().toString());
                        observer.enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                if(response.isSuccessful()){
                                    list.get(position).setContent(holder.groupCommentEdtContent.getText().toString());
                                    notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {

                            }
                        });
                    }
                    holder.groupCommentImgBtnDel.setVisibility(View.VISIBLE);
                }
            }
        });

        return convertView;

    }

    class Holder {
        @BindView(R.id.groupComment_img_profile)
        ImageView groupCommentImgProfile;
        @BindView(R.id.groupComment_txt_nickname)
        TextView groupCommentTxtNickname;
        @BindView(R.id.groupComment_txt_date)
        TextView groupCommentTxtDate;
        @BindView(R.id.groupComment_imgBtn_del)
        Button groupCommentImgBtnDel;
        @BindView(R.id.groupComment_imgBtn_modify)
        Button groupCommentImgBtnModify;
        @BindView(R.id.groupComment_edt_content)
        EditText groupCommentEdtContent;
        @BindView(R.id.groupComment_txt_content)
        TextView groupCommentTxtContent;

        Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
