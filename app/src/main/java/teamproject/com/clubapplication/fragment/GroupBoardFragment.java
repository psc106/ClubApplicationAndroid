package teamproject.com.clubapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import teamproject.com.clubapplication.GroupBoardWriteActivity;
import teamproject.com.clubapplication.GroupHomeActivity;
import teamproject.com.clubapplication.R;


public class GroupBoardFragment extends Fragment {

    private static GroupBoardFragment curr = null;
    @BindView(R.id.list_view_group_board)
    ListView listViewGroupBoard;
    @BindView(R.id.group_board_txt_search)
    EditText groupBoardTxtSearch;
    @BindView(R.id.group_board_btn_search)
    Button groupBoardBtnSearch;
    @BindView(R.id.group_board_btn_write)
    Button groupBoardBtnWrite;
    Unbinder unbinder;

    public static GroupBoardFragment getInstance() {

        if (curr == null) {
            curr = new GroupBoardFragment();
        }

        return curr;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_board, container, false);


        unbinder = ButterKnife.bind(this, view);
        return view;

    }
    @OnClick(R.id.group_board_btn_write)
    void write(){
        Intent intent = new Intent(getActivity(), GroupBoardWriteActivity.class);
        startActivity(intent);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
