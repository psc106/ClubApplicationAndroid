package teamproject.com.clubapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import teamproject.com.clubapplication.GroupHomeActivity;
import teamproject.com.clubapplication.adapter.GroupHomeNoticeListviewAdapter;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.data.Club;
import teamproject.com.clubapplication.data.ClubMemberClass;
import teamproject.com.clubapplication.utils.RefreshData;
import teamproject.com.clubapplication.utils.bus.BusProvider;
import teamproject.com.clubapplication.utils.bus.event.ClubLoadEvent;


public class GroupHomeFragment extends Fragment  implements RefreshData {
    private static GroupHomeFragment curr = null;
    @BindView(R.id.group_home_img)
    ImageView groupHomeImg;
    @BindView(R.id.group_home_txt_group_name)
    LinearLayout groupHomeTxtGroupName;
    @BindView(R.id.group_home_txt_group_count)
    TextView groupHomeTxtGroupCount;
    @BindView(R.id.group_home_txt_master)
    TextView groupHomeTxtMaster;
    @BindView(R.id.group_home_txt_category)
    TextView groupHomeTxtCategory;
    @BindView(R.id.group_home_txt_location)
    TextView groupHomeTxtLocation;
    @BindView(R.id.group_home_txt_info)
    TextView groupHomeTxtInfo;
    @BindView(R.id.group_home_lv_notice)
    ListView groupHomeLvNotice;
    @BindView(R.id.group_home_btn_join)
    Button groupHomeBtnJoin;
    Unbinder unbinder;

    GroupHomeNoticeListviewAdapter groupHomeNoticeListviewAdapter;
    ArrayList<?> arrayList;
    Bus bus;
    ClubMemberClass clubMemberClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_home, container, false);

        unbinder = ButterKnife.bind(this, view);
        bus = BusProvider.getInstance().getBus();
        bus.register(this);

        arrayList= new ArrayList<>();
        groupHomeNoticeListviewAdapter = new GroupHomeNoticeListviewAdapter(arrayList);
        groupHomeLvNotice.setAdapter(groupHomeNoticeListviewAdapter);
        clubMemberClass = ((GroupHomeActivity)getActivity()).getClubMemberClass();

//        groupHomeNoticeListviewAdapter = new GroupHomeNoticeListviewAdapter();
//        groupHomeLvNotice.setAdapter(groupHomeNoticeListviewAdapter);
        refresh();

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
        if(clubMemberClass!=null) {

        }
    }

    @Subscribe
    void finishLoad(ClubLoadEvent clubLoadEvent) {
        this.clubMemberClass = clubLoadEvent.getClubMemberClass();
        refresh();
    }
}
