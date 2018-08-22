package teamproject.com.clubapplication;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import teamproject.com.clubapplication.utils.DrawerMenu;

public class GroupManageModifyActivity extends AppCompatActivity {

    @BindView(R.id.group_manage_modify_img)
    ImageView groupManageModifyImg;
    @BindView(R.id.group_manage_modify_name)
    TextView groupManageModifyName;
    @BindView(R.id.group_manage_modify_count)
    TextView groupManageModifyCount;
    @BindView(R.id.group_manage_modify_maker)
    TextView groupManageModifyMaker;
    @BindView(R.id.group_manage_modify_category)
    TextView groupManageModifyCategory;
    @BindView(R.id.group_manage_modify_location)
    TextView groupManageModifyLocation;
    @BindView(R.id.group_manage_modify_info)
    EditText groupManageModifyInfo;
    @BindView(R.id.group_manage_modify_btn_ok)
    Button groupManageModifyBtnOk;
    @BindView(R.id.group_manage_modify_menu)
    FrameLayout groupManageModifyMenu;
    @BindView(R.id.group_manage_modify_drawer)
    DrawerLayout groupManageModifyDrawer;
    private DrawerMenu drawerMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_manage_modify);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (drawerMenu == null) {
            drawerMenu = DrawerMenu.addMenu(this, R.id.group_manage_modify_menu, R.id.group_manage_modify_drawer);
        } else {
            drawerMenu.restartMenu(this, R.id.group_manage_modify_menu, R.id.group_manage_modify_drawer);
        }
    }
}
