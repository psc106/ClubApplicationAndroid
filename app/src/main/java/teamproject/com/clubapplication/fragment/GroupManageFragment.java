package teamproject.com.clubapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import teamproject.com.clubapplication.GroupActivity;
import teamproject.com.clubapplication.GroupManageMemberActivity;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.data.ClubMemberClass;
import teamproject.com.clubapplication.utils.LoginService;
import teamproject.com.clubapplication.utils.RefreshData;
import teamproject.com.clubapplication.utils.bus.BusProvider;
import teamproject.com.clubapplication.utils.bus.event.ClubLoadEvent;


public class GroupManageFragment extends Fragment {

    @BindView(R.id.group_manage_btn_nickname)
    Button groupManageBtnNickname;
    @BindView(R.id.group_manage_btn_club)
    Button groupManageBtnClub;
    @BindView(R.id.group_manage_btn_member)
    Button groupManageBtnMember;
    @BindView(R.id.group_manage_btn_delete)
    Button groupManageBtnDelete;

    Unbinder unbinder;
    LoginService loginService;
    Bus bus;
    ClubMemberClass clubMemberClass;

    @OnClick(R.id.group_manage_btn_member)
    void memberManage() {
        if(clubMemberClass!=null) {
            Intent intent = new Intent(getActivity(), GroupManageMemberActivity.class);
            intent.putExtra("clubId", clubMemberClass.getClub().getId());
            startActivity(intent);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_manage, container, false);

        unbinder = ButterKnife.bind(this, view);
        bus = BusProvider.getInstance().getBus();
        bus.register(this);
        loginService = LoginService.getInstance();
        clubMemberClass = ((GroupActivity) getActivity()).getClubMemberClass();

        return view;
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
    }
}
