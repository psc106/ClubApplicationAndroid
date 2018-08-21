package teamproject.com.clubapplication.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.utils.RefreshData;
import teamproject.com.clubapplication.adapter.MyContentPostListviewAdapter;
import teamproject.com.clubapplication.data.Post;
import teamproject.com.clubapplication.utils.LoginService;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;

public class MyContentPostFragment extends Fragment implements RefreshData{

    private static MyContentPostFragment fragment = null;
    public static MyContentPostFragment getInstance() {
        if(fragment==null) {
            fragment = new MyContentPostFragment();
        }
        return fragment;
    }

    @BindView(R.id.myContentPost_listV)
    StickyListHeadersListView stickyListV;


    ArrayList<Post> arrayList ;
    MyContentPostListviewAdapter adapter;
    Unbinder unbinder;
    LoginService loginService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_content_post, container, false);
        unbinder = ButterKnife.bind(this, view);
        loginService = LoginService.getInstance();
        arrayList = new ArrayList<>();
        adapter = new MyContentPostListviewAdapter(arrayList);
        stickyListV.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void refresh() {
        Call<ArrayList<Post>> observer =
                RetrofitService.getInstance().getRetrofitRequest().selectMyPost(loginService.getMember().getId());
        observer.enqueue(new Callback<ArrayList<Post>>() {
            @Override
            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                if (response.isSuccessful()) {
                    arrayList.clear();
                    arrayList.addAll(response.body());
                    adapter.notifyDataSetChanged();

                } else {
                    Log.d("로그", "onResponse: fail");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
