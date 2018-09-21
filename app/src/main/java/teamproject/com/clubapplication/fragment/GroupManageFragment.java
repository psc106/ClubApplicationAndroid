package teamproject.com.clubapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamproject.com.clubapplication.GroupActivity;
import teamproject.com.clubapplication.GroupManageMemberActivity;
import teamproject.com.clubapplication.GroupManageHomeActivity;
import teamproject.com.clubapplication.GroupManageProfileActivity;
import teamproject.com.clubapplication.MainActivity;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.adapter.GroupManageListviewAdapter;
import teamproject.com.clubapplication.data.ClubMemberClass;
import teamproject.com.clubapplication.data.GroupManageMenu;
import teamproject.com.clubapplication.utils.LoginService;
import teamproject.com.clubapplication.utils.RefreshData;
import teamproject.com.clubapplication.utils.bus.BusProvider;
import teamproject.com.clubapplication.utils.bus.event.ClubLoadEvent;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;


public class GroupManageFragment extends Fragment implements RefreshData{

    @BindView(R.id.groupManage_listV)
    ListView listView;

    Unbinder unbinder;
    Bus bus;
    ClubMemberClass clubMemberClass;
    GroupManageListviewAdapter adapter;

    ArrayList<GroupManageMenu> admin = new ArrayList<GroupManageMenu>(Arrays.asList(new GroupManageMenu[]{GroupManageMenu.profile, GroupManageMenu.home, GroupManageMenu.member, GroupManageMenu.delete}));
    ArrayList<GroupManageMenu> normal = new ArrayList<GroupManageMenu>(Arrays.asList(new GroupManageMenu[]{GroupManageMenu.profile, GroupManageMenu.member, GroupManageMenu.out}));
    ArrayList<GroupManageMenu> wait = new ArrayList<GroupManageMenu>(Arrays.asList(new GroupManageMenu[]{GroupManageMenu.profile, GroupManageMenu.out}));

    ArrayList<GroupManageMenu> list;

    LoginService loginService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_manage, container, false);

        loginService = LoginService.getInstance();
        unbinder = ButterKnife.bind(this, view);
        bus = BusProvider.getInstance().getBus();
        bus.register(this);
        clubMemberClass = ((GroupActivity) getActivity()).getClubMemberClass();

        list = new ArrayList<>();
        adapter = new GroupManageListviewAdapter(list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;

                switch (list.get(position).getId()){
                    case 0:
                            intent = new Intent(getActivity(), GroupManageProfileActivity.class);
                        intent.putExtra("memberId", loginService.getMember().getId());
                        startActivity(intent);
                        break;
                    case 1:
//                        Call<Void> outObserver  = RetrofitService.getInstance().getRetrofitRequest().outClub(clubMemberClass.getClubView().getId(), loginService.getMember().getId());
//                        outObserver.enqueue(new Callback<Void>() {
//                            @Override
//                            public void onResponse(Call<Void> call, Response<Void> response) {
//                                if(response.isSuccessful()){
//                                    ((MainActivity)MainActivity.activity).backHomeActivity(getActivity());
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<Void> call, Throwable t) {
//                            }
//                        });
                        break;
                    case 2:
                        intent = new Intent(getActivity(), GroupManageMemberActivity.class);
                        intent.putExtra("clubId", clubMemberClass.getClubView().getId());
                        startActivity(intent);
                        break;
                    case  3:
                        intent = new Intent(getActivity(), GroupManageHomeActivity.class);
                        intent.putExtra("clubId", clubMemberClass.getClubView().getId());
                        startActivity(intent);
                        break;
                    case  4:
//                        Call<Void> delObserver  = RetrofitService.getInstance().getRetrofitRequest().deleteClub(clubMemberClass.getClubView().getId());
//                        delObserver.enqueue(new Callback<Void>() {
//                            @Override
//                            public void onResponse(Call<Void> call, Response<Void> response) {
//                                if(response.isSuccessful()){
//                                    ((MainActivity)MainActivity.activity).backHomeActivity(getActivity());
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<Void> call, Throwable t) {
//                            }
//                        });
                        break;
                }
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
        bus.unregister(this);
    }

    @Subscribe
    void finishLoad(ClubLoadEvent clubLoadEvent) {
        this.clubMemberClass = clubLoadEvent.getClubMemberClass();
        refresh();
    }

    @Override
    public void refresh() {
        if(clubMemberClass==null)
            return;
        if(clubMemberClass.getMemberClass().equals("W")){
            list.clear();
            list.addAll(wait);
            adapter.notifyDataSetChanged();
        } else if(clubMemberClass.getMemberClass().equals("Y")) {
            list.clear();
            list.addAll(normal);
            adapter.notifyDataSetChanged();
        } else {
            list.clear();
            list.addAll(admin);
            adapter.notifyDataSetChanged();
        }
    }
}
