package teamproject.com.clubapplication.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamproject.com.clubapplication.GroupPostDetailActivity;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.data.PostView;
import teamproject.com.clubapplication.utils.LoadingDialog;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;

public class GroupBoardLoadingFragment extends Fragment {
    public static GroupBoardLoadingFragment newInstance() {
        GroupBoardLoadingFragment fragment = new GroupBoardLoadingFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_empty, container, false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void refresh(final Activity activity, Long postId) {
        Log.d("로그", "refresh: "+postId);
        LoadingDialog.getInstance().progressON(getActivity(),"로딩중");
        Call<PostView> observer = RetrofitService.getInstance().getRetrofitRequest().selectCurrPost(postId);
        observer.enqueue(new Callback<PostView>() {
            @Override
            public void onResponse(Call<PostView> call, Response<PostView> response) {
                if(response.isSuccessful()) {
                    if (response.body() == null) {
                        Log.d("로그", "fail: ");

                    } else {
                        Log.d("로그", "start: "+activity.getClass().toString());
                        ((GroupPostDetailActivity) activity).setCurrPost(response.body());
                        ((GroupPostDetailActivity) activity).checkPosition(response.body().canMovePosition());
                    }
                }
                LoadingDialog.getInstance().progressOFF();
            }

            @Override
            public void onFailure(Call<PostView> call, Throwable t) {
                LoadingDialog.getInstance().progressOFF();
            }
        });
    }
}
