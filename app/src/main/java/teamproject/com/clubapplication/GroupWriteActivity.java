package teamproject.com.clubapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import teamproject.com.clubapplication.fragment.GroupWriteAlbumFragment;
import teamproject.com.clubapplication.fragment.groupWriteBoardFragment;
import teamproject.com.clubapplication.utils.DrawerMenu;

public class GroupWriteActivity extends AppCompatActivity {

    private DrawerMenu drawerMenu;


    String [] item = {"게시판","앨범"};
    FragmentTransaction fragmentTransaction;
    Fragment currentFragment;

    String TAG = "오류";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_write);
        ButterKnife.bind(this);

    }
    @Override
    protected void onResume() {
        if (drawerMenu == null) {
            drawerMenu = DrawerMenu.addMenu(this, R.id.group_album_board_write_menu, R.id.group_album_board_write_drawer);
        } else {
            drawerMenu.restartMenu(this, R.id.group_album_board_write_menu, R.id.group_album_board_write_drawer);
        }
        super.onResume();

    }
}
