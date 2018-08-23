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

public class GroupBoardListviewAdapter extends BaseAdapter {

    ArrayList<?> arrayList;

    public GroupBoardListviewAdapter(ArrayList<?> arrayList) {
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_gorup_board, parent, false);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        //게시물 이미지 , 게시글 작성자 , 날짜 ,게시물 내용

        return convertView;

    }

    static class Holder {
        @BindView(R.id.group_board_img)
        ImageView groupBoardImg;
        @BindView(R.id.group_board_txt_name)
        TextView groupBoardTxtName;
        @BindView(R.id.group_board_txt_date)
        TextView groupBoardTxtDate;
        @BindView(R.id.group_board_txt_content)
        TextView groupBoardTxtContent;

        Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
