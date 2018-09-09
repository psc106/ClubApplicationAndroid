package teamproject.com.clubapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import teamproject.com.clubapplication.adapter.GroupCommentListviewAdapter;
import teamproject.com.clubapplication.data.CommentView;
import teamproject.com.clubapplication.data.PostView;
import teamproject.com.clubapplication.utils.RefreshData;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;

public class GroupPostDetailActivity extends AppCompatActivity implements RefreshData {
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

    PostView postData;
    ArrayList<String> imgUrl;
    ArrayList<CommentView> commentList;
    GroupCommentListviewAdapter adapter;
    int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_post_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        postData = intent.getParcelableExtra("postData");
        if(postData==null) {
            //실패처리
        } else {
            imgUrl = new ArrayList<>();
            commentList = new ArrayList<>();

            adapter = new GroupCommentListviewAdapter(commentList);
            refresh();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void refresh() {
        page=1;
        getImgs();
        getCommentCount();
    }

    void getCommentCount(){
        Call<Integer> countObserver = RetrofitService.getInstance().getRetrofitRequest().selectPostCommentsCount(postData.getId());
        countObserver.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    if(response.body()>0){
                        getComment();
                    }
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }
    void getComment(){
        Call<ArrayList<CommentView>> commentObserver = RetrofitService.getInstance().getRetrofitRequest().selectPostComments(postData.getId(), page);
        commentObserver.enqueue(new Callback<ArrayList<CommentView>>() {
            @Override
            public void onResponse(Call<ArrayList<CommentView>> call, Response<ArrayList<CommentView>> response) {
                if(response.isSuccessful()){
                    commentList=response.body();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CommentView>> call, Throwable t) {

            }
        });
    }
    void getImgs(){
        Call<ArrayList<String>> imgObserver = RetrofitService.getInstance().getRetrofitRequest().selectPostImgs(postData.getId());
        imgObserver.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                if(response.isSuccessful()){
                    imgUrl = response.body();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {

            }
        });
    }
}
