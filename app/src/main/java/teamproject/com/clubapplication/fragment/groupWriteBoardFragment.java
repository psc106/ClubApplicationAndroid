package teamproject.com.clubapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import teamproject.com.clubapplication.R;


public class groupWriteBoardFragment extends Fragment {

    private static groupWriteBoardFragment curr = null;
    @BindView(R.id.group_board_write_board_edt)
    EditText groupBoardWriteBoardEdt;
    @BindView(R.id.group_board_write_board_img)
    ImageView groupBoardWriteBoardImg;
    @BindView(R.id.group_board_write_board_check)
    CheckBox groupBoardWriteBoardCheck;
    @BindView(R.id.group_board_write_board_tag)
    EditText groupBoardWriteBoardTag;
    Unbinder unbinder;

    public static groupWriteBoardFragment getInstance() {

        if (curr == null) {
            curr = new groupWriteBoardFragment();
        }

        return curr;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_write_board, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
