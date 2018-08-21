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

public class ModifyNickNameActivity extends AppCompatActivity {

    @BindView(R.id.modify_nick_name_img_Profile)
    ImageView modifyNickNameImgProfile;
    @BindView(R.id.modify_nick_name_txt_Name)
    TextView modifyNickNameTxtName;
    @BindView(R.id.modify_nick_nameo_txt_Age)
    TextView modifyNickNameoTxtAge;
    @BindView(R.id.modify_nick_name_txt_Gender)
    TextView modifyNickNameTxtGender;
    @BindView(R.id.modify_nick_name_txt_nick_name)
    EditText modifyNickNameTxtNickName;
    @BindView(R.id.modify_nick_name_btn_modify)
    Button modifyNickNameBtnModify;
    @BindView(R.id.modifty_nick_name_menu)
    FrameLayout modiftyNickNameMenu;
    @BindView(R.id.modify_nick_name_drawer)
    DrawerLayout modifyNickNameDrawer;
    private DrawerMenu drawerMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_nick_name);
        ButterKnife.bind(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (drawerMenu == null) {
            drawerMenu = DrawerMenu.addMenu(this, R.id.modifty_nick_name_menu, R.id.modify_nick_name_drawer);
        } else {
            drawerMenu.restartMenu(this, R.id.modifty_nick_name_menu, R.id.modify_nick_name_drawer);
        }
    }
}
