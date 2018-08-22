package teamproject.com.clubapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import teamproject.com.clubapplication.R;

public class GvAlbumAdapter extends BaseAdapter {

    ArrayList<Integer> imgs;

    public GvAlbumAdapter(ArrayList<Integer> imgs) {
        this.imgs = imgs;
    }

    @Override
    public int getCount() {
        return imgs.size();
    }

    @Override
    public Object getItem(int position) {
        return imgs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       Holder holder = new Holder();
       if (convertView==null){
             convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gv_group_album,parent,false);
             holder.img=convertView.findViewById(R.id.gv_album_img) ;
             holder.tag=convertView.findViewById(R.id.gv_album_txt_tag);
             convertView.setTag(holder);
       }   else {
            holder=(Holder)convertView.getTag();
       }
       holder.img.setBackgroundResource(imgs.get(position));
       return  convertView;


    }
     public class Holder{
        TextView tag;
        ImageView img;
    }
}