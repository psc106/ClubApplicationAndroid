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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamproject.com.clubapplication.GroupPostDetailActivity;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.adapter.GroupCommentListviewAdapter;
import teamproject.com.clubapplication.data.CommentView;
import teamproject.com.clubapplication.data.PostView;
import teamproject.com.clubapplication.utils.RefreshData;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;


public class GroupBoardDetailFragment extends Fragment implements RefreshData {

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
    @BindView(R.id.postDetail_frg_listV_Comment)
    ListView listView;

    Unbinder unbinder;

    PostView postView;
    Long postId;
    ArrayList<CommentView> commentList;
    ArrayList<String> imgList;
    GroupCommentListviewAdapter adapter;
    int page = 1;

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
        commentList = new ArrayList<>();
        adapter = new GroupCommentListviewAdapter(getContext(), commentList);
        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void refresh() {
        page=1;
        commentList.clear();
        getCommentCount();
    }

    void getCommentCount(){
        Call<Integer> observer = RetrofitService.getInstance().getRetrofitRequest().getCommentCount(postId);
        observer.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    if(response.body()>0){
                        getCommentData();
                    }
                }

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }
    void getCommentData(){
        Call<ArrayList<CommentView>> commentObserver = RetrofitService.getInstance().getRetrofitRequest().selectPostComment(postId, page);
        commentObserver.enqueue(new Callback<ArrayList<CommentView>>() {
            @Override
            public void onResponse(Call<ArrayList<CommentView>> call, Response<ArrayList<CommentView>> response) {
                if(response.isSuccessful()) {
                    commentList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CommentView>> call, Throwable t) {

            }
        });
    }
    void getImgData(){
        Call<ArrayList<String>> imgObserver = RetrofitService.getInstance().getRetrofitRequest().selectPostImg(postId);
        imgObserver.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                if(response.isSuccessful()){
                    imgList.clear();
                    imgList.addAll(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {

            }
        });
    }
}
