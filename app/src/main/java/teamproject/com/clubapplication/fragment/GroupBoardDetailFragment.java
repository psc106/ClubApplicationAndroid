package teamproject.com.clubapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import teamproject.com.clubapplication.R;


public class GroupBoardDetailFragment extends Fragment {

    private static GroupBoardDetailFragment curr = null;
    @BindView(R.id.group_board_detail_txt_name)
    TextView groupBoardDetailTxtName;
    Unbinder unbinder;
    @BindView(R.id.group_board_detail_btn_before)
    ImageView groupBoardDetailBtnBefore;
    @BindView(R.id.group_board_detail_btn_next)
    ImageView groupBoardDetailBtnNext;
    @BindView(R.id.group_board_detail_img)
    ImageView groupBoardDetailImg;
    @BindView(R.id.group_board_detail_text)
    TextView groupBoardDetailText;
    @BindView(R.id.group_board_detail_txt_tag)
    TextView groupBoardDetailTxtTag;
    @BindView(R.id.group_board_detail_txt_date)
    TextView groupBoardDetailTxtDate;
    @BindView(R.id.lv_group_board_detail)
    ListView lvGroupBoardDetail;
    @BindView(R.id.group_board_detail_edt_re)
    EditText groupBoardDetailEdtRe;
    @BindView(R.id.group_board_detail_btn_re)
    Button groupBoardDetailBtnRe;
    @BindView(R.id.group_board_detail_btn_delete)
    Button groupBoardDetailBtnDelete;
    @BindView(R.id.group_board_detail_btn_write)
    Button groupBoardDetailBtnWrite;

    public static GroupBoardDetailFragment getinstance() {
        if (curr == null) {
            curr = new GroupBoardDetailFragment();
        }
        return curr;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_board_detail, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
