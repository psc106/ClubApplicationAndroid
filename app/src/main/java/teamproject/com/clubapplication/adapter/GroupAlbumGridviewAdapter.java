package teamproject.com.clubapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import teamproject.com.clubapplication.R;

public class GroupAlbumGridviewAdapter extends BaseAdapter {

    ArrayList<?> arrayList;

    public GroupAlbumGridviewAdapter(ArrayList<?> arrayList) {
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder(convertView);
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gv_group_album, parent, false);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        return convertView;


    }



    static class Holder {
        @BindView(R.id.gv_album_img)
        ImageView gvAlbumImg;
        @BindView(R.id.gv_album_txt_tag)
        TextView gvAlbumTxtTag;

        Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}