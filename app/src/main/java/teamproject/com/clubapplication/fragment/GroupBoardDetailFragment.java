package teamproject.com.clubapplication.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamproject.com.clubapplication.GroupActivity;
import teamproject.com.clubapplication.GroupPostDetailActivity;
import teamproject.com.clubapplication.GroupPostModifyActivity;
import teamproject.com.clubapplication.GroupWriteActivity;
import teamproject.com.clubapplication.LoginActivity;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.adapter.GroupBoardImagePagerAdapter;
import teamproject.com.clubapplication.adapter.GroupCommentListviewAdapter;
import teamproject.com.clubapplication.data.CommentView;
import teamproject.com.clubapplication.data.PostView;
import teamproject.com.clubapplication.utils.CommonUtils;
import teamproject.com.clubapplication.utils.LoadingDialog;
import teamproject.com.clubapplication.utils.LoginService;
import teamproject.com.clubapplication.utils.RefreshData;
import teamproject.com.clubapplication.utils.customView.InfiniteViewPager;
import teamproject.com.clubapplication.utils.glide.GlideApp;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;


public class GroupBoardDetailFragment extends Fragment implements RefreshData {
//
//    @BindView(R.id.postDetail_frg_recycleV_img)
//    RecyclerView postDetailFrgRecycleVImg;

    @BindView(R.id.postDetail_frg_viewpager)
    ViewPager viewPager;
    @BindView(R.id.postDetail_frg_scrollView)
    ScrollView scrollView;

    @BindView(R.id.postDetail_frg_img_Profile)
    ImageView postDetailFrgImgProfile;
    @BindView(R.id.group_board_detail_txt_name)
    TextView groupBoardDetailTxtName;
    @BindView(R.id.group_board_detail_txt_date)
    TextView groupBoardDetailTxtDate;
    @BindView(R.id.group_board_detail_text)
    TextView groupBoardDetailText;
//    @BindView(R.id.group_board_detail_txt_tag)
//    TextView groupBoardDetailTxtTag;

    @BindView(R.id.group_board_detail_edt_comment)
    EditText groupBoardDetailEdtComment;
    @BindView(R.id.group_board_detail_btn_comment)
    Button groupBoardDetailBtnComment;

    @BindView(R.id.postDetail_frg_listV_Comment)
    ListView listView;

    @BindView(R.id.group_board_detail_btn_write)
    Button groupBoardDetailBtnWrite;
    @BindView(R.id.group_board_detail_btn_delete)
    Button groupBoardDetailBtnDelete;
    @BindView(R.id.group_board_detail_btn_modify)
    Button groupBoardDetailBtnModify;

    Unbinder unbinder;

    PostView postView;
    Long postId;
    ArrayList<CommentView> commentList;
    ArrayList<String> imgList;
    GroupCommentListviewAdapter adapter;
    GroupBoardImagePagerAdapter imgAdapter;

    int page = 1;
    int count = 0;
    int imgCount = -1;

    public static GroupBoardDetailFragment newInstance(PostView postView) {
        GroupBoardDetailFragment fragment = new GroupBoardDetailFragment();

        Bundle args = new Bundle();
        if (fragment.postView == null) {
            fragment.postView = postView;
        }
        args.putLong("postId", postView.getId());
        Log.d("로그", postView.toString());

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_board_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        postId = getArguments().getLong("postId");
        commentList = new ArrayList<>();
        adapter = new GroupCommentListviewAdapter(getContext(), commentList, postId);
        listView.setAdapter(adapter);
        viewPager.setClipToPadding(false);
        viewPager.setPadding(30, 0, 30, 0);
        viewPager.setPageMargin(getResources().getDisplayMetrics().widthPixels / -9);

        if(postView.getMember_id()!=LoginService.getInstance().getMember().getId()){
            groupBoardDetailBtnDelete.setVisibility(View.GONE);
            groupBoardDetailBtnModify.setVisibility(View.GONE);
        } else {
            groupBoardDetailBtnDelete.setVisibility(View.VISIBLE);
            groupBoardDetailBtnModify.setVisibility(View.VISIBLE);
        }

        LoadingDialog.getInstance().progressON(getActivity(), "로딩중");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollView.getChildAt(0).getBottom()
                            <= (scrollView.getHeight() + scrollView.getScrollY())) {
                        if (page * 7 < count) {
                            page++;
                            getCommentData();

                            Toast.makeText(getContext(), page + "", Toast.LENGTH_SHORT).show();

                            scrollView.fling(0);
                            scrollView.scrollBy(0, 0);
                        }

                        //scroll view is at bottom
                    }
                }
            });
        }
        groupBoardDetailBtnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("로그", "click");

                if(!TextUtils.isEmpty(groupBoardDetailEdtComment.getText())){
                    Log.d("로그", "click in");
                    writeComment(groupBoardDetailEdtComment.getText().toString());
                    groupBoardDetailEdtComment.setText("");
                }
            }
        });
        groupBoardDetailBtnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((GroupPostDetailActivity)getActivity()).getMemberClass() == null) {
                    return;
                } else {
                    String memberClass = ((GroupPostDetailActivity)getActivity()).getMemberClass();
                    Long clubId = ((GroupPostDetailActivity)getActivity()).getClubId();
                    Intent intent = new Intent(getContext(), GroupWriteActivity.class);
                    int category = 1;
                    //관리자
                    if (memberClass.equals("A")) {
                        category = 0;
//            } else if (tabLayout.getSelectedTabPosition() == 2) {
//                category = 2;
//            } else if (tabLayout.getSelectedTabPosition() == 3) {
//                category = 3;
                    } else {
                        category = 1;
                    }
                    intent.putExtra("category", category);
                    intent.putExtra("class", memberClass);
                    intent.putExtra("clubId", clubId);
                    startActivity(intent);
                }
            }
        });
        groupBoardDetailBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Boolean> observer = RetrofitService.getInstance().getRetrofitRequest().deletePost(postView.getId(), LoginService.getInstance().getMember().getId());
                observer.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if(response.isSuccessful()) {
                            Intent intent = new Intent();
                            intent.putExtra("isDel",1);
                            getActivity().setResult(Activity.RESULT_OK, intent);
                            getActivity().finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {

                    }
                });
            }
        });

        groupBoardDetailBtnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), GroupPostModifyActivity.class);
                intent.putExtra("postData", postView);
                startActivity(intent);
            }
        });

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
        if(count==0) {
            page=1;
            getCommentCount();
        } else {
            commentRefresh();
        }
        viewPager.setVisibility(View.VISIBLE);

        imgList = new ArrayList<>();
        imgAdapter = new GroupBoardImagePagerAdapter(getContext(), imgList);
        viewPager.setAdapter(imgAdapter);
        getImageCount(viewPager);

        GlideApp.with(this).load(CommonUtils.serverURL+CommonUtils.attachPath+postView.getImgUrl()).centerCrop().skipMemoryCache(true).into(postDetailFrgImgProfile);
        groupBoardDetailTxtName.setText(postView.getNickname());
        groupBoardDetailTxtDate.setText(postView.getCreate_date());
        groupBoardDetailText.setText(postView.getContent());
        listView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
    }

    void getCommentCount() {

        Call<Integer> observer = RetrofitService.getInstance().getRetrofitRequest().getCommentCount(postId);
        observer.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    count=response.body();
                    if (response.body() > 0) {
                        getCommentData();
                    }
                }

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }

    void getCommentData() {
        Call<ArrayList<CommentView>> commentObserver = RetrofitService.getInstance().getRetrofitRequest().selectPostComment(postId, page, 7);
        commentObserver.enqueue(new Callback<ArrayList<CommentView>>() {
            @Override
            public void onResponse(Call<ArrayList<CommentView>> call, Response<ArrayList<CommentView>> response) {
                if (response.isSuccessful()) {
                    commentList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    CommonUtils.setListviewHeightBasedOnChildren(listView);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CommentView>> call, Throwable t) {

            }
        });
    }


    void getImageCount(final ViewPager viewPager) {
        Call<Integer> observer = RetrofitService.getInstance().getRetrofitRequest().getImageCount(postId);
        observer.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                imgCount = 0;
                if (response.isSuccessful()) {
                    Log.d("로그", "img "+response.body());
                    imgCount = response.body();
                    if (response.body() > 0) {
                        if(viewPager!=null){
                            viewPager.setVisibility(View.VISIBLE);
                        }
                        getImgData();
                    } else {
                        if(viewPager!=null) {
                            viewPager.setVisibility(View.GONE);
                        }
                    }

                }
                LoadingDialog.getInstance().progressOFF();
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                LoadingDialog.getInstance().progressOFF();
            }
        });
    }


    void getImgData() {
        Call<ArrayList<String>> imgObserver = RetrofitService.getInstance().getRetrofitRequest().selectPostImg(postId);
        imgObserver.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                Log.d("로그", ""+response.body().get(0));
                if (response.isSuccessful()) {
                    imgList.clear();
                    imgList.addAll(response.body());
                    imgAdapter.notifyDataSetChanged();
                    LoadingDialog.getInstance().progressOFF();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {

            }
        });
    }

    void writeComment(String commentContents) {
        Call<Void> observer = RetrofitService.getInstance().getRetrofitRequest().insertComment(postId, LoginService.getInstance().getMember().getId(), commentContents);
        observer.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    commentRefresh();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    void commentRefresh() {
        Call<ArrayList<CommentView>> observer = RetrofitService.getInstance().getRetrofitRequest().refreshPostComment(postId, (commentList.size()+1)/7+((commentList.size()+1)%7==0?0:1), 7);
        observer.enqueue(new Callback<ArrayList<CommentView>>() {
            @Override
            public void onResponse(Call<ArrayList<CommentView>> call, Response<ArrayList<CommentView>> response) {
                if (response.isSuccessful()) {
                    commentList.clear();
                    commentList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    CommonUtils.setListviewHeightBasedOnChildren(listView);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CommentView>> call, Throwable t) {

            }
        });
    }
}
