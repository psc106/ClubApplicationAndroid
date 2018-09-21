package teamproject.com.clubapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamproject.com.clubapplication.GroupPostDetailActivity;
import teamproject.com.clubapplication.LoginActivity;
import teamproject.com.clubapplication.MainActivity;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.data.CommentView;
import teamproject.com.clubapplication.data.PostFrame;
import teamproject.com.clubapplication.data.PostView;
import teamproject.com.clubapplication.utils.CommonUtils;
import teamproject.com.clubapplication.utils.LoginService;
import teamproject.com.clubapplication.utils.bus.BusProvider;
import teamproject.com.clubapplication.utils.bus.event.CommentEvent;
import teamproject.com.clubapplication.utils.glide.GlideApp;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;

public class GroupBoardListviewAdapter extends BaseExpandableListAdapter {

    ArrayList<PostFrame> arrayList;
    Context context;
    LoginService loginService;

    public GroupBoardListviewAdapter(Context context, ArrayList<PostFrame> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
        loginService = LoginService.getInstance();
    }


    @Override
    public int getGroupCount() {
        return arrayList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return arrayList.get(groupPosition).getCommentView().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return arrayList.get(groupPosition).getPostView();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return arrayList.get(groupPosition).getCommentView().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final PostHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_gorup_board, parent, false);

            holder = new PostHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (PostHolder) convertView.getTag();
        }

        final PostView currPostView = (PostView) getGroup(groupPosition);

        holder.groupBoardLvTxtContent.setText(currPostView.getContent());
        holder.groupBoardLvTxtDate.setText(currPostView.getCreate_date());
        holder.groupBoardLvTxtProfileName.setText(currPostView.getNickname());
        GlideApp.with(context).load(currPostView.getImgUrl()).centerCrop().placeholder(R.drawable.profile).skipMemoryCache(true).error(R.drawable.profile).into(holder.groupBoardLvImgProfileImg);

        holder.groupBoardLvBtnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("로그", "btn click");
                if (loginService.getMember() == null) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                }

                if (!TextUtils.isEmpty(holder.groupBoardLvEdtComment.getText())) {
                    String comment = holder.groupBoardLvEdtComment.getText().toString();
                    writeComment(currPostView, comment);

                    holder.groupBoardLvEdtComment.clearFocus();
                    holder.groupBoardLvEdtComment.setText("");

                    BusProvider.getInstance().getBus().post(new CommentEvent(0, groupPosition, currPostView.getId(), -1));
                }
            }
        });

        holder.groupBoardLvTxtContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostHolder holder = (PostHolder) v.getTag();
                Intent intent = new Intent(context, GroupPostDetailActivity.class);
                Log.d("로그", arrayList.get(groupPosition).toString());
                intent.putExtra("postData", arrayList.get(groupPosition).getPostView());
                context.startActivity(intent);
            }
        });


        return convertView;

    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final CommentHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_group_comment, parent, false);

            holder = new CommentHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (CommentHolder) convertView.getTag();
        }

        if(holder.groupCommentImgBtnDel.getVisibility()==View.GONE) {
            holder.groupCommentEdtContent.setVisibility(View.GONE);
            holder.groupCommentTxtContent.setVisibility(View.VISIBLE);
            holder.groupCommentImgBtnDel.setVisibility(View.VISIBLE);
        }

        final PostView currPostView = (PostView) getGroup(groupPosition);
        final CommentView currCommentView = (CommentView) getChild(groupPosition, childPosition);

        holder.groupCommentEdtContent.setText(currCommentView.getContent());
        holder.groupCommentTxtContent.setText(currCommentView.getContent());
        holder.groupCommentTxtDate.setText(currCommentView.getCreate_date());
        holder.groupCommentTxtNickname.setText(currCommentView.getNickname());
        GlideApp.with(context).load(currCommentView.getImgUrl()).centerCrop().placeholder(R.drawable.profile).skipMemoryCache(true).error(R.drawable.profile).into(holder.groupCommentImgProfile);


        holder.groupCommentImgBtnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Boolean> observer = RetrofitService.getInstance().getRetrofitRequest().deleteComment(currCommentView.getId(), loginService.getMember().getId());
                observer.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if(response.isSuccessful()) {
                            arrayList.get(groupPosition).getCommentView().remove(childPosition);
                            BusProvider.getInstance().getBus().post(new CommentEvent(2, groupPosition, currPostView.getId(), currCommentView.getId()));
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {

                    }
                });
            }
        });

//        holder.groupCommentEdtContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(hasFocus){
//                    holder.groupCommentTxtContent.setVisibility(View.GONE);
//                    holder.groupCommentEdtContent.setVisibility(View.VISIBLE);
//                } else {
//                    holder.groupCommentTxtContent.setVisibility(View.VISIBLE);
//                    holder.groupCommentEdtContent.setVisibility(View.GONE);
//                }
//            }
//        });

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
                        Call<Boolean> observer = RetrofitService.getInstance().getRetrofitRequest().updateComment(currCommentView.getId(), loginService.getMember().getId(), holder.groupCommentEdtContent.getText().toString());
                        observer.enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                if(response.isSuccessful()){
                                    arrayList.get(groupPosition).getCommentView().get(childPosition).setContent(holder.groupCommentEdtContent.getText().toString());
                                    BusProvider.getInstance().getBus().post(new CommentEvent(1, groupPosition, currPostView.getId(), currCommentView.getId()));
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

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    void writeComment(final PostView post, String commentContents) {
        Call<Void> observer = RetrofitService.getInstance().getRetrofitRequest().insertComment(post.getId(), loginService.getMember().getId(), commentContents);
        observer.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }


    public class PostHolder {
        @BindView(R.id.groupBoard_lv_img_profileImg)
        public ImageView groupBoardLvImgProfileImg;
        @BindView(R.id.groupBoard_lv_txt_profileName)
        public TextView groupBoardLvTxtProfileName;
        @BindView(R.id.groupBoard_lv_txt_date)
        public TextView groupBoardLvTxtDate;

        @BindView(R.id.groupBoard_lv_txt_content)
        public TextView groupBoardLvTxtContent;

        @BindView(R.id.groupBoard_lv_edt_comment)
        public EditText groupBoardLvEdtComment;
        @BindView(R.id.groupBoard_lv_btn_comment)
        public Button groupBoardLvBtnComment;

        @BindView(R.id.groupBoard_lv_img_thumbnail)
        public ImageView groupBoardLvImgThumbnail;
        @BindView(R.id.groupBoard_lv_img_count)
        public ImageView groupBoardLvImgCount;

        public PostHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    public class CommentHolder {
        @BindView(R.id.groupComment_img_profile)
        public ImageView groupCommentImgProfile;
        @BindView(R.id.groupComment_txt_nickname)
        public TextView groupCommentTxtNickname;
        @BindView(R.id.groupComment_txt_date)
        public TextView groupCommentTxtDate;

        @BindView(R.id.groupComment_imgBtn_del)
        public Button groupCommentImgBtnDel;
        @BindView(R.id.groupComment_imgBtn_modify)
        public Button groupCommentImgBtnModify;

        @BindView(R.id.groupComment_txt_content)
        public TextView groupCommentTxtContent;
        @BindView(R.id.groupComment_edt_content)
        public EditText groupCommentEdtContent;

        public CommentHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
