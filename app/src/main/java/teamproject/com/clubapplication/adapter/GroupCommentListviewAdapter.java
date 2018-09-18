package teamproject.com.clubapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.data.CommentView;
import teamproject.com.clubapplication.utils.glide.GlideApp;

public class GroupCommentListviewAdapter extends BaseAdapter {
    ArrayList<CommentView> list;
    Context context;

    public GroupCommentListviewAdapter(Context context, ArrayList<CommentView> list) {
        this.list = list;
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_group_comment, parent, false);

            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        CommentView currCommentView = (CommentView)getItem(position);

        holder.groupCommentEdtContent.setText(currCommentView.getContent());
        holder.groupCommentTxtDate.setText(currCommentView.getCreate_date());
        holder.groupCommentTxtNickname.setText(currCommentView.getNickname());
        GlideApp.with(context).load(currCommentView.getImgUrl()).centerCrop().placeholder(R.drawable.profile).skipMemoryCache(true).error(R.drawable.profile).into(holder.groupCommentImgProfile);

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
        ImageButton groupCommentImgBtnDel;
        @BindView(R.id.groupComment_imgBtn_modify)
        ImageButton groupCommentImgBtnModify;
        @BindView(R.id.groupComment_edt_content)
        EditText groupCommentEdtContent;

        Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
