package teamproject.com.clubapplication.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.utils.customView.XYFitImageView;
import teamproject.com.clubapplication.utils.customView.XYFitTextView;
import teamproject.com.clubapplication.utils.glide.GlideApp;

public class MainGridviewAdapter extends BaseAdapter {
    ArrayList<String> arrayList;

    public MainGridviewAdapter(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
        Log.d("로그", "constructor: "+arrayList.size());
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
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gv_main, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

//        GlideApp.with(convertView).load("http://placehold.it/100").into(holder.imgGv);
        holder.textView.setText((String)getItem(position));
        return convertView;

    }



    class Holder {
//        @BindView(R.id.img_gv)
//        XYFitImageView imgGv;
        @BindView(R.id.main_GridV_ctext_Text)
        XYFitTextView textView;

        Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
