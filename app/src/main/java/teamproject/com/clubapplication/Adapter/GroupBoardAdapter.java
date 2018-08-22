package teamproject.com.clubapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import teamproject.com.clubapplication.R;

public class GroupBoardAdapter extends BaseAdapter{

    public GroupBoardAdapter(){

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        if(convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_gorup_board,parent,false);
            holder.group_board_img = convertView.findViewById(R.id.group_board_img);
            holder.group_board_txt_name = convertView.findViewById(R.id.group_board_txt_name);
            holder.group_board_txt_date = convertView.findViewById(R.id.group_board_txt_date);
            holder.group_board_txt_content = convertView.findViewById(R.id.group_board_txt_content);
            holder.group_board_txt_search = convertView.findViewById(R.id.group_board_txt_search);
            holder.group_board_btn_search = convertView.findViewById(R.id.group_board_btn_search);
            holder.group_board_btn_write = convertView.findViewById(R.id.group_board_btn_write);
            convertView.setTag(holder);
        }else{
            holder=(Holder) convertView.getTag();
        }
        //게시물 이미지 , 게시글 작성자 , 날짜 ,게시물 내용

        return convertView;

    }
    public class Holder{

        ImageView group_board_img;
        TextView group_board_txt_name;
        TextView group_board_txt_date;
        TextView group_board_txt_content;
        TextView group_board_txt_search;
        Button group_board_btn_search;
        Button group_board_btn_write;

    }
}
