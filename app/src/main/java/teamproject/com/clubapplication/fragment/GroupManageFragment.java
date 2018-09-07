package teamproject.com.clubapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import teamproject.com.clubapplication.GroupActivity;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.adapter.GroupManageListviewAdapter;
import teamproject.com.clubapplication.data.ClubMemberClass;
import teamproject.com.clubapplication.data.GroupManageMenu;
import teamproject.com.clubapplication.utils.RefreshData;
import teamproject.com.clubapplication.utils.bus.BusProvider;
import teamproject.com.clubapplication.utils.bus.event.ClubLoadEvent;


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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_manage, container, false);

        unbinder = ButterKnife.bind(this, view);
        bus = BusProvider.getInstance().getBus();
        bus.register(this);
        clubMemberClass = ((GroupActivity) getActivity()).getClubMemberClass();

        list = new ArrayList<>();
        adapter = new GroupManageListviewAdapter(list);
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
        bus.unregister(this);
    }

    @Subscribe
    void finishLoad(ClubLoadEvent clubLoadEvent) {
        this.clubMemberClass = clubLoadEvent.getClubMemberClass();
        refresh();
    }

    @Override
    public void refresh() {
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
