package teamproject.com.clubapplication.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Bus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.Unbinder;
import teamproject.com.clubapplication.MyAlarmActivity;
import teamproject.com.clubapplication.MyCalendarActivity;
import teamproject.com.clubapplication.MyContentActivity;
import teamproject.com.clubapplication.MyGroupActivity;
import teamproject.com.clubapplication.MyOptionActivity;
import teamproject.com.clubapplication.utils.bus.BusProvider;
import teamproject.com.clubapplication.DrawerMenu;
import teamproject.com.clubapplication.MyInfoActivity;
import teamproject.com.clubapplication.R;

@SuppressLint("ValidFragment")
public class MenuFragment extends Fragment {
    public MenuFragment(){};
    public MenuFragment(DrawerMenu drawerMenu){
        this.drawerMenu = drawerMenu;
    };
    private DrawerMenu drawerMenu;

    @BindView(R.id.menu_btn_Login)
    Button loginBtn;
    @BindView(R.id.menu_imgV_Profile)
    ImageView profileImg;
    @BindView(R.id.menu_txt_Name)
    TextView nameTxt;
    @BindView(R.id.menu_listV_MenuList)
    ListView menuListV;

    @OnClick(R.id.menu_btn_Login)
    void onClickLoginBtn() {
    }
    @OnClick(R.id.menu_imgV_Profile)
    void onClickProfileImg() {
    }
    @OnClick(R.id.menu_txt_Name)
    void onClickMemeberInfo() {
        Intent intent = new Intent(getContext(), MyInfoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        closeMenu(MyInfoActivity.class);
    }

    @OnItemClick(R.id.menu_listV_MenuList)
    public void onItemClick(AdapterView<?> parent, int position) {

        Intent intent = null;
        Object closeClass = null;
        if(position==0) {
            intent = new Intent(getContext(), MyGroupActivity.class);
            closeClass = MyGroupActivity.class;
        } else if(position==1) {
            intent = new Intent(getContext(), MyCalendarActivity.class);
            closeClass = MyCalendarActivity.class;
        } else if(position==2) {
            intent = new Intent(getContext(), MyAlarmActivity.class);
            closeClass = MyAlarmActivity.class;
        } else if(position==3) {
            intent = new Intent(getContext(), MyContentActivity.class);
            closeClass = MyContentActivity.class;
        } else if(position==4) {
            intent = new Intent(getContext(), MyOptionActivity.class);
            closeClass = MyOptionActivity.class;
        } else  {
            return;
        }
        if(intent!=null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            closeMenu(closeClass);
        }
    }

    String TAG = "로그출력";
    Unbinder unbinder;
    Bus bus;
    String[] menuList = {"내 동호회", "내 일정", "내 알림", "내 글", "설정"};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        unbinder = ButterKnife.bind(this, view);
        bus = BusProvider.getInstance().getBus();
        bus.register(this);

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, menuList);
        menuListV.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        bus.unregister(this);
    }

    public  void closeMenu(Object activity) {
        if(getContext().getClass() != activity) {
            drawerMenu.getMenuDrawer().closeDrawer(Gravity.LEFT, false);
        } else {
            drawerMenu.getMenuDrawer().closeDrawers();
        }

    }
}
