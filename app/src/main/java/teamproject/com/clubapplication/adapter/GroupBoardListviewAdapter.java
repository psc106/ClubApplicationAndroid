package teamproject.com.clubapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
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
import teamproject.com.clubapplication.LoginActivity;
import teamproject.com.clubapplication.MainActivity;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.data.CommentView;
import teamproject.com.clubapplication.data.PostFrame;
import teamproject.com.clubapplication.data.PostView;
import teamproject.com.clubapplication.utils.CommonUtils;
import teamproject.com.clubapplication.utils.LoginService;
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
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
                if (loginService.getMember() == null) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                }

                if (!TextUtils.isEmpty(holder.groupBoardLvEdtComment.getText())) {
                    String comment = holder.groupBoardLvEdtComment.getText().toString();
                    writeComment(currPostView, comment);
                }
            }
        });

        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        CommentHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_group_comment, parent, false);

            holder = new CommentHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (CommentHolder) convertView.getTag();
        }

        CommentView currCommentView = (CommentView)getChild(groupPosition, childPosition);

        holder.groupCommentEdtContent.setText(currCommentView.getContent());
        holder.groupCommentTxtDate.setText(currCommentView.getCreate_date());
        holder.groupCommentTxtNickname.setText(currCommentView.getNickname());
        GlideApp.with(context).load(currCommentView.getImgUrl()).centerCrop().placeholder(R.drawable.profile).skipMemoryCache(true).error(R.drawable.profile).into(holder.groupCommentImgProfile);

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


    class PostHolder {
        @BindView(R.id.groupBoard_lv_img_profileImg)
        ImageView groupBoardLvImgProfileImg;
        @BindView(R.id.groupBoard_lv_txt_profileName)
        TextView groupBoardLvTxtProfileName;
        @BindView(R.id.groupBoard_lv_txt_date)
        TextView groupBoardLvTxtDate;
        @BindView(R.id.groupBoard_lv_txt_content)
        TextView groupBoardLvTxtContent;

        @BindView(R.id.groupBoard_lv_edt_comment)
        EditText groupBoardLvEdtComment;
        @BindView(R.id.groupBoard_lv_btn_comment)
        Button groupBoardLvBtnComment;

        @BindView(R.id.groupBoard_lv_img_thumbnail)
        ImageView groupBoardLvImgThumbnail;
        @BindView(R.id.groupBoard_lv_img_count)
        ImageView groupBoardLvImgCount;

        public PostHolder(View view) {
            ButterKnife.bind(this, view);
            groupBoardLvEdtComment.setFocusable(false);
            groupBoardLvEdtComment.setFocusableInTouchMode(false);
            groupBoardLvBtnComment.setFocusable(false);
            groupBoardLvBtnComment.setFocusableInTouchMode(false);
        }
    }


    class CommentHolder {
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

        CommentHolder(View view) {
            ButterKnife.bind(this, view);
            groupCommentImgBtnDel.setFocusable(false);
            groupCommentImgBtnDel.setFocusableInTouchMode(false);
            groupCommentImgBtnModify.setFocusable(false);
            groupCommentImgBtnModify.setFocusableInTouchMode(false);
        }
    }
}
