package teamproject.com.clubapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import teamproject.com.clubapplication.GroupManageMemberActivity;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.utils.RefreshData;


public class GroupManageFragment extends Fragment  implements RefreshData {


    private static GroupManageFragment curr = null;
    @BindView(R.id.group_manage_btn_nickname)
    Button groupManageBtnNickname;
    @BindView(R.id.group_manage_btn_club)
    Button groupManageBtnClub;
    @BindView(R.id.group_manage_btn_member)
    Button groupManageBtnMember;
    @BindView(R.id.group_manage_btn_delete)
    Button groupManageBtnDelete;
    Unbinder unbinder;

    @OnClick(R.id.group_manage_btn_member)
    void memberManage() {
        Intent intent = new Intent(getActivity(), GroupManageMemberActivity.class);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_manage, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void refresh() {

    }
}
