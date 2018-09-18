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
import android.widget.Button;
import android.widget.EditText;
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

public class GroupBoardListviewAdapter extends BaseAdapter {

    ArrayList<PostFrame> arrayList;
    Context context;
    LoginService loginService;

    public GroupBoardListviewAdapter(Context context, ArrayList<PostFrame> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
        loginService = LoginService.getInstance();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_gorup_board, parent, false);

            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        final PostFrame currPostFrame = (PostFrame) getItem(position);
        final PostView currPostView = currPostFrame.getPostView();

        holder.groupBoardLvTxtContent.setText(currPostView.getContent());
        holder.groupBoardLvTxtDate.setText(currPostView.getCreate_date());
        holder.groupBoardLvTxtProfileName.setText(currPostView.getNickname());
        GlideApp.with(context).load(currPostView.getImgUrl()).centerCrop().placeholder(R.drawable.profile).skipMemoryCache(true).error(R.drawable.profile).into(holder.groupBoardLvImgProfileImg);

        holder.listviewAdapter.notifyDataSetChanged();

        if(currPostFrame.getCommentView()!=null && currPostFrame.getCommentView().size()>0) {
            Log.d("로그", "onResponse: "+1);
            holder.commentList.clear();
            holder.commentList.addAll(currPostFrame.getCommentView());
            holder.listviewAdapter.notifyDataSetChanged();
        }

        holder.groupBoardLvBtnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loginService.getMember()==null){
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                }

                if(!TextUtils.isEmpty(holder.groupBoardLvEdtComment.getText())){
                    String comment = holder.groupBoardLvEdtComment.getText().toString();
                    Call<Void> observer = RetrofitService.getInstance().getRetrofitRequest().insertComment(currPostView.getId(), loginService.getMember().getId(), comment);
                    observer.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.isSuccessful()){

                                if(arrayList.get(position).getCommentView().size()<=5){

                                    Call<ArrayList<CommentView>> observer = RetrofitService.getInstance().getRetrofitRequest().refreshPostComment(currPostView.getId());
                                    observer.enqueue(new Callback<ArrayList<CommentView>>() {
                                        @Override
                                        public void onResponse(Call<ArrayList<CommentView>> call, Response<ArrayList<CommentView>> response) {
                                            if(response.isSuccessful()){

                                                currPostFrame.getCommentView().clear();
                                                currPostFrame.setCommentView(response.body());
                                                holder.listviewAdapter.notifyDataSetChanged();
                                                CommonUtils.setListviewHeightBasedOnChildren(holder.groupBoardLvListVComment);
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ArrayList<CommentView>> call, Throwable t) {

                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });
                }
            }
        });

        return convertView;

    }

    class Holder {
        @BindView(R.id.groupBoard_lv_img_profileImg)
        ImageView groupBoardLvImgProfileImg;
        @BindView(R.id.groupBoard_lv_txt_profileName)
        TextView groupBoardLvTxtProfileName;
        @BindView(R.id.groupBoard_lv_txt_date)
        TextView groupBoardLvTxtDate;
        @BindView(R.id.groupBoard_lv_txt_content)
        TextView groupBoardLvTxtContent;
        @BindView(R.id.groupBoard_lv_listV_comment)
        ListView groupBoardLvListVComment;

        @BindView(R.id.groupBoard_lv_edt_comment)
        EditText groupBoardLvEdtComment;
        @BindView(R.id.groupBoard_lv_btn_comment)
        Button groupBoardLvBtnComment;

        @BindView(R.id.groupBoard_lv_img_thumbnail)
        ImageView groupBoardLvImgThumbnail;
        @BindView(R.id.groupBoard_lv_img_count)
        ImageView groupBoardLvImgCount;

        GroupCommentListviewAdapter listviewAdapter;
        ArrayList<CommentView> commentList;

        public Holder(View view) {
            ButterKnife.bind(this, view);
            commentList = new ArrayList<>();
            listviewAdapter = new GroupCommentListviewAdapter(context, commentList);
            groupBoardLvListVComment.setAdapter(listviewAdapter);
        }
    }

}
