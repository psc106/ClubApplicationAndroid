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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.data.PostView;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;


public class GroupBoardDetailFragment extends Fragment {

    @BindView(R.id.group_board_detail_txt_name)
    TextView groupBoardDetailTxtName;
    @BindView(R.id.group_board_detail_text)
    TextView groupBoardDetailText;
    @BindView(R.id.group_board_detail_txt_tag)
    TextView groupBoardDetailTxtTag;
    @BindView(R.id.group_board_detail_txt_date)
    TextView groupBoardDetailTxtDate;
    @BindView(R.id.group_board_detail_edt_re)
    EditText groupBoardDetailEdtRe;
    @BindView(R.id.group_board_detail_btn_re)
    Button groupBoardDetailBtnRe;
    @BindView(R.id.group_board_detail_btn_delete)
    Button groupBoardDetailBtnDelete;
    @BindView(R.id.group_board_detail_btn_write)
    Button groupBoardDetailBtnWrite;

    Unbinder unbinder;

    PostView postView;
    Long postId;

    public static GroupBoardDetailFragment newInstance(PostView postView) {
        GroupBoardDetailFragment fragment = new GroupBoardDetailFragment();

        Bundle args = new Bundle();
        if(fragment.postView==null){
            fragment.postView=postView;
        }
        args.putLong("postId", postView.getId());

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_board_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        postId = getArguments().getLong("postId");

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
