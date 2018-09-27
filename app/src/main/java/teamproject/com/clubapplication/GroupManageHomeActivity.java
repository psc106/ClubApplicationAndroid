package teamproject.com.clubapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupManageHomeActivity extends AppCompatActivity {


    @BindView(R.id.group_manage_modify_img)
    ImageView groupManageModifyImg;
    @BindView(R.id.group_manage_modify_name)
    EditText groupManageModifyName;
    @BindView(R.id.group_manage_modify_count)
    EditText groupManageModifyCount;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_manage_modify);
        ButterKnife.bind(this);
    }

}
