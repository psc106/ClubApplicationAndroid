package teamproject.com.clubapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamproject.com.clubapplication.GroupActivity;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.adapter.GroupAlbumGridviewAdapter;
import teamproject.com.clubapplication.data.AlbumView;
import teamproject.com.clubapplication.data.ClubMemberClass;
import teamproject.com.clubapplication.data.PostView;
import teamproject.com.clubapplication.utils.RefreshData;
import teamproject.com.clubapplication.utils.bus.BusProvider;
import teamproject.com.clubapplication.utils.bus.event.ClubLoadEvent;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;


public class GroupAlbumFragment extends Fragment implements RefreshData {


    private static GroupAlbumFragment curr = null;
    @BindView(R.id.group_album_gv)
    GridView groupAlbumGv;
    Unbinder unbinder;
    Bus bus;
    ClubMemberClass clubMemberClass;
    int page = 1;
    int count = 0;

    ArrayList<AlbumView> arrayList;
    GroupAlbumGridviewAdapter groupAlbumGridviewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_album, container, false);

        unbinder = ButterKnife.bind(this, view);
        bus = BusProvider.getInstance().getBus();
        bus.register(this);
        arrayList=new ArrayList<>();
        groupAlbumGridviewAdapter=new GroupAlbumGridviewAdapter(arrayList);
        groupAlbumGv.setAdapter(groupAlbumGridviewAdapter);
        clubMemberClass = ((GroupActivity)getActivity()).getClubMemberClass();


        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        bus.unregister(this);
    }

    @Override
    public void refresh() {
        page=1;
        if(clubMemberClass!=null) {
            getCount();
        }
    }


    public void getData() {
        Call<ArrayList<AlbumView>> observer  = RetrofitService.getInstance().getRetrofitRequest().selectClubAlbum(clubMemberClass.getClubView().getId(), page);
        observer.enqueue(new Callback<ArrayList<AlbumView>>() {
            @Override
            public void onResponse(Call<ArrayList<AlbumView>> call, Response<ArrayList<AlbumView>> response) {
                if(response.isSuccessful()) {
                    arrayList.addAll(response.body());
                    groupAlbumGridviewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AlbumView>> call, Throwable t) {
            }
        });
    }

    public void getCount(){
        Call<Integer> observer = RetrofitService.getInstance().getRetrofitRequest().getAlbumCount(clubMemberClass.getClubView().getId());
        observer.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    count = response.body();
                    if(count>0) {
                        arrayList.clear();
                        getData();
                    }
                }
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
            }
        });
    }

    @Subscribe
    void finishLoad(ClubLoadEvent clubLoadEvent) {
        this.clubMemberClass = clubLoadEvent.getClubMemberClass();
        refresh();
    }
}
