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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import teamproject.com.clubapplication.adapter.GroupHomeNoticeListviewAdapter;
import teamproject.com.clubapplication.R;


public class GroupHomeFragment extends Fragment {
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
    ArrayList<?>arrayList;

    public static GroupHomeFragment getInstance() {
        if (curr == null) {
            curr = new GroupHomeFragment();
        }

        return curr;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_home, container, false);

        arrayList= new ArrayList<>();
        groupHomeNoticeListviewAdapter = new GroupHomeNoticeListviewAdapter(arrayList);
        groupHomeLvNotice.setAdapter(groupHomeNoticeListviewAdapter);


//        groupHomeNoticeListviewAdapter = new GroupHomeNoticeListviewAdapter();
//        groupHomeLvNotice.setAdapter(groupHomeNoticeListviewAdapter);



        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
