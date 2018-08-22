package teamproject.com.clubapplication;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupAlbumDetailActivity extends AppCompatActivity {

    @BindView(R.id.group_album_detail_img)
    ImageView groupAlbumDetailImg;
    @BindView(R.id.group_album_detail_btn_out)
    Button groupAlbumDetailBtnOut;
    @BindView(R.id.img_layout)
    RelativeLayout imgLayout;
    @BindView(R.id.group_album_detail_btn_next)
    Button groupAlbumDetailBtnNext;
    @BindView(R.id.group_album_detail_btn_before)
    Button groupAlbumDetailBtnBefore;
    @BindView(R.id.group_album_detail_tag)
    TextView groupAlbumDetailTag;
    @BindView(R.id.tag_box)
    LinearLayout tagBox;
    @BindView(R.id.group_album_detail_writer)
    TextView groupAlbumDetailWriter;
    @BindView(R.id.group_album_detail_date)
    TextView groupAlbumDetailDate;
    @BindView(R.id.group_album_detail_menu)
    FrameLayout groupAlbumDetailMenu;
    @BindView(R.id.group_album_detail_drawer)
    DrawerLayout groupAlbumDetailDrawer;
    private DrawerMenu drawerMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_album_detail);
        ButterKnife.bind(this);


    }

    @Override
    protected void onResume() {
        if (drawerMenu == null) {
            drawerMenu = DrawerMenu.addMenu(this, R.id.group_album_detail_menu, R.id.group_album_detail_drawer);
        } else {
            drawerMenu.restartMenu(this, R.id.group_album_detail_menu, R.id.group_album_detail_drawer);
        }
        super.onResume();

    }
}
