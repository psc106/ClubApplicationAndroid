package teamproject.com.clubapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.data.CommentView;

public class GroupCommentListviewAdapter extends BaseAdapter {
    ArrayList<CommentView> list;
    public GroupCommentListviewAdapter(ArrayList<CommentView> list) {
        this.list = list;
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

            holder= new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        //날짜 , 공지
//        setCompoundDrawablesWithIntrinsicBounds
        return convertView;

    }

    class Holder {
        Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
