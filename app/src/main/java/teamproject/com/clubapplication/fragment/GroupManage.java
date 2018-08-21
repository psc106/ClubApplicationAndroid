package teamproject.com.clubapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import teamproject.com.clubapplication.R;


public class GroupManage extends Fragment {


    private static GroupManage curr = null;
    @BindView(R.id.group_manage_btn_nickname)
    Button groupManageBtnNickname;
    @BindView(R.id.group_manage_btn_club)
    Button groupManageBtnClub;
    @BindView(R.id.group_manage_btn_member)
    Button groupManageBtnMember;
    @BindView(R.id.group_manage_btn_delete)
    Button groupManageBtnDelete;
    Unbinder unbinder;

    public static GroupManage getInstance() {

        if (curr == null) {
            curr = new GroupManage();
        }

        return curr;
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
}
