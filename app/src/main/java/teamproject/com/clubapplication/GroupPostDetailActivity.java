package teamproject.com.clubapplication;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import teamproject.com.clubapplication.adapter.GroupPostDetailPageAdapter;
import teamproject.com.clubapplication.data.CommentView;
import teamproject.com.clubapplication.data.PostView;
import teamproject.com.clubapplication.fragment.GroupBoardDetailFragment;
import teamproject.com.clubapplication.fragment.GroupBoardLoadingFragment;
import teamproject.com.clubapplication.utils.RefreshData;
import teamproject.com.clubapplication.utils.customView.InfiniteViewPager;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;

public class GroupPostDetailActivity extends AppCompatActivity {
    @BindView(R.id.postDetail_viewPager)
    InfiniteViewPager viewPager;

    GroupPostDetailPageAdapter pageAdapter;
    private PostView currPost;

    public PostView getCurrPost() {
        return currPost;
    }
    public void setCurrPost(PostView currPost) {
        this.currPost = currPost;
    }

    public static int prePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_post_detail);
        ButterKnife.bind(this);


        Intent intent = getIntent();
        currPost=intent.getParcelableExtra("postData");

        pageAdapter = new GroupPostDetailPageAdapter(getSupportFragmentManager(), currPost);
        viewPager.setAdapter(pageAdapter);
        viewPager.setPagingEnabled(true);

        checkPosition(currPost.canMovePosition());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("로그", "onPageScrolled: ");
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("로그", "onPageSelected: ");
                if(position-prePosition<0){
                    //왼쪽이동
                    Fragment fragment = pageAdapter.getItem(position);
                    if(fragment instanceof GroupBoardLoadingFragment){
                        viewPager.setPagingEnabled(false);
                        ((GroupBoardLoadingFragment)fragment).refresh(currPost.getNextId());
                    }
                } else if(position-prePosition>0){
                    //오른쪽이동
                    Fragment fragment = pageAdapter.getItem(position);
                    if(fragment instanceof GroupBoardLoadingFragment){
                        viewPager.setPagingEnabled(false);
                        ((GroupBoardLoadingFragment)fragment).refresh(currPost.getPreviousId());
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d("로그", "onPageScrollStateChanged: ");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void checkPosition(Integer position) {
        if(position==null)
            return;

        viewPager.setPagingEnabled(true);
        if(position==-1) {
            viewPager.setCurrentItem(0, false);
            prePosition=0;
        } else if(position==0) {
            viewPager.setCurrentItem(2, false);
            prePosition=2;
        } else if(position==1) {
            viewPager.setCurrentItem(4, false);
            prePosition=4;
        } else if(position==Integer.MIN_VALUE){
            viewPager.setCurrentItem(2, false);
            prePosition=2;
            viewPager.setPagingEnabled(false);
        }
    }

}
