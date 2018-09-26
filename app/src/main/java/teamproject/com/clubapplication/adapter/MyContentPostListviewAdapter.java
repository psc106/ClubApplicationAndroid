package teamproject.com.clubapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.data.Post;
import teamproject.com.clubapplication.data.PostView;

public class MyContentPostListviewAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    private ArrayList<PostView> list;
    public MyContentPostListviewAdapter(ArrayList<PostView> list) {
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
        Horder horder;
        if(convertView==null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_my_content_post, parent, false);
            horder = new Horder(convertView);
            convertView.setTag(horder);
        } else {
            horder=(Horder)convertView.getTag();
        }

        return convertView;
    }


    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderHorder horder;
        if(convertView==null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_my_content_post, parent, false);
            horder = new HeaderHorder(convertView);
            convertView.setTag(horder);
        } else {
            horder=(HeaderHorder)convertView.getTag();
        }

        return convertView;
    }

    //header sorting
    //시간은 yyyymmddhhmmss로 받는다
    //최근일수록 높은 값을 갖는다.
    @Override
    public long getHeaderId(int position) {
        return position;
    }

    class Horder {
        Horder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class HeaderHorder {
        HeaderHorder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
