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
import teamproject.com.clubapplication.fragment.GroupBoardWriteAlbumFragment;
import teamproject.com.clubapplication.fragment.GroupBoardWriteBoardFragment;

public class GroupBoardWriteActivity extends AppCompatActivity {

    private DrawerMenu drawerMenu;

    @BindView(R.id.group_board_spinner)
    Spinner groupBoardSpinner;

    String [] item = {"게시판","앨범"};
    FragmentTransaction fragmentTransaction;
    Fragment currentFragment;

    String TAG = "오류";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_board_write);
        ButterKnife.bind(this);

        currentFragment = GroupBoardWriteBoardFragment.getInstance();


        Spinner adt_spinner = (Spinner)findViewById(R.id.group_board_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adt_spinner.setAdapter(adapter);


        groupBoardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                if(position==0) {
                    currentFragment = GroupBoardWriteBoardFragment.getInstance();
                } else if(position==1){
                    currentFragment = GroupBoardWriteAlbumFragment.getinstance();
                }
                Log.d(TAG, "onItemSelected: 1 : " + position);
                fragmentTransaction.replace(R.id.groupWrite_fragment, currentFragment);
                fragmentTransaction.commit();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                currentFragment = GroupBoardWriteBoardFragment.getInstance();
                Log.d(TAG, "onItemSelected: 2");
            }
        });


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
