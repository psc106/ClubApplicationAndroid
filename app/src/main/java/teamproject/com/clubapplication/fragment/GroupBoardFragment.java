package teamproject.com.clubapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamproject.com.clubapplication.GroupActivity;
import teamproject.com.clubapplication.GroupBoardWriteActivity;
import teamproject.com.clubapplication.GroupPostDetailActivity;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.adapter.GroupBoardListviewAdapter;
import teamproject.com.clubapplication.data.ClubMemberClass;
import teamproject.com.clubapplication.data.PostView;
import teamproject.com.clubapplication.utils.RefreshData;
import teamproject.com.clubapplication.utils.bus.BusProvider;
import teamproject.com.clubapplication.utils.bus.event.ClubLoadEvent;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;


public class GroupBoardFragment extends Fragment  implements RefreshData {

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

    ArrayList<PostView> arrayList;
    GroupBoardListviewAdapter groupBoardListviewAdapter;
    Bus bus;
    ClubMemberClass clubMemberClass;
    int page = 1;
    int count = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_board, container, false);
        unbinder = ButterKnife.bind(this, view);
        bus = BusProvider.getInstance().getBus();
        bus.register(this);
        arrayList= new ArrayList<>();


//        PostView test = new PostView();
//        test.setId(10l);
//        test.setNextId(11l);
//        test.setPreviousId(9l);
//        arrayList.add(test);

        groupBoardListviewAdapter=new GroupBoardListviewAdapter(arrayList);
        listViewGroupBoard.setAdapter(groupBoardListviewAdapter);
        clubMemberClass = ((GroupActivity)getActivity()).getClubMemberClass();

        listViewGroupBoard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), GroupPostDetailActivity.class);
                intent.putExtra("postData", arrayList.get(position));
                startActivity(intent);
            }
        });

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
        Call<ArrayList<PostView>> observer  = RetrofitService.getInstance().getRetrofitRequest().selectClubPost(clubMemberClass.getClub().getId(), page);
        observer.enqueue(new Callback<ArrayList<PostView>>() {
            @Override
            public void onResponse(Call<ArrayList<PostView>> call, Response<ArrayList<PostView>> response) {
                if(response.isSuccessful()) {
                    arrayList.addAll(response.body());
                    groupBoardListviewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PostView>> call, Throwable t) {
            }
        });
    }

    public void getCount(){
        Call<Integer> observer = RetrofitService.getInstance().getRetrofitRequest().getPostCount(clubMemberClass.getClub().getId());
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
