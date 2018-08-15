package teamproject.com.clubapplication;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import teamproject.com.clubapplication.data.Post;

public class MyContentPostListviewAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    private ArrayList<Post> list;
    public MyContentPostListviewAdapter(ArrayList<Post> list) {
        this.list = list;
        Post P = new Post();
        P.getId();
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
        return null;
    }


    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    //header sorting
    //시간은 yyyymmddhhmmss로 받는다
    @Override
    public long getHeaderId(int position) {
        return 0;
    }

    private class Horder {
        Horder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private class HeaderHorder {
        HeaderHorder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
