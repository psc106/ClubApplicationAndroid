package teamproject.com.clubapplication.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamproject.com.clubapplication.LoginActivity;
import teamproject.com.clubapplication.MainActivity;
import teamproject.com.clubapplication.MyAlarmActivity;
import teamproject.com.clubapplication.MyCalendarActivity;
import teamproject.com.clubapplication.MyGroupActivity;
import teamproject.com.clubapplication.MyContentActivity;
import teamproject.com.clubapplication.MyOptionActivity;
import teamproject.com.clubapplication.utils.RefreshData;
import teamproject.com.clubapplication.data.Member;
import teamproject.com.clubapplication.utils.LoginService;
import teamproject.com.clubapplication.utils.bus.BusProvider;
import teamproject.com.clubapplication.utils.DrawerMenu;
import teamproject.com.clubapplication.MyInfoActivity;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.utils.bus.event.LoginEvent;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;

@SuppressLint("ValidFragment")
public class MenuFragment extends Fragment {
    public MenuFragment() {
    }

    ;

    public MenuFragment(DrawerMenu drawerMenu) {
        this.drawerMenu = drawerMenu;
    }

    ;
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
        if (loginService.getMember() == null) {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            if (closeMenu(LoginActivity.class)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        } else {
            loginService.logout(getActivity());
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("login_setting", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
        }
    }

    @OnClick(R.id.menu_imgV_Profile)
    void onClickProfileImg() {
//        refreshLogin();
//        loginService.login(getActivity(), new Member(Member.testData()));
    }

    @OnClick(R.id.menu_txt_Name)
    void onClickMemeberInfo() {
        if (loginService.getMember() != null) {
            refreshLogin();
            Intent intent = new Intent(getContext(), MyInfoActivity.class);
            if (closeMenu(MyInfoActivity.class)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        }
    }

    @OnItemClick(R.id.menu_listV_MenuList)
    public void onItemClick(AdapterView<?> parent, int position) {

        Intent intent = null;
        Class<?> closeClass = null;
        if (position == 0) {
            intent = new Intent(getContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            closeClass = MainActivity.class;
        } else if (position == 1) {
            intent = new Intent(getContext(), MyGroupActivity.class);
            closeClass = MyGroupActivity.class;
        } else if (position == 2) {
            intent = new Intent(getContext(), MyCalendarActivity.class);
            closeClass = MyCalendarActivity.class;
        } else if (position == 3) {
            intent = new Intent(getContext(), MyAlarmActivity.class);
            closeClass = MyAlarmActivity.class;
        } else if (position == 4) {
            intent = new Intent(getContext(), MyContentActivity.class);
            closeClass = MyContentActivity.class;
        } else if (position == 5) {
            intent = new Intent(getContext(), MyOptionActivity.class);
            closeClass = MyOptionActivity.class;
        } else {
            return;
        }

        if (loginService.getMember() == null && (position >= 1 && position <= 4)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            builder.setTitle("정보")        // 제목 설정
                    .setMessage("로그인이 필요한 서비스입니다.")        // 메세지 설정
                    .setCancelable(true)        // 뒤로 버튼 클릭시 취소 가능 설정
                    .setPositiveButton("확인", null);
            AlertDialog dialog = builder.create();    // 알림창 객체 생성
            dialog.show();    // 알림창 띄우기

            return;
        } else if (loginService.getMember() != null) {
            refreshLogin();

            if (loginService.getMember().getVerify().equals("N") && (position >= 1 && position <= 4)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("정보")        // 제목 설정
                        .setMessage("메일 인증이 필요한 서비스입니다.")        // 메세지 설정
                        .setCancelable(true)        // 뒤로 버튼 클릭시 취소 가능 설정
                        .setPositiveButton("확인", null);
                AlertDialog dialog = builder.create();    // 알림창 객체 생성
                dialog.show();    // 알림창 띄우기

                return;
            }
        }

        if (intent != null) {
            if (closeMenu(closeClass)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        }
    }

    String TAG = "로그출력";
    Unbinder unbinder;
    Bus bus;
    ArrayAdapter adapter;
    LoginService loginService;
    String[] menuList = {"홈", "내 동호회", "내 일정", "내 알림", "내 글", "설정"};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        unbinder = ButterKnife.bind(this, view);
        bus = BusProvider.getInstance().getBus();
        bus.register(this);

        adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, menuList);
        menuListV.setAdapter(adapter);

        loginService = LoginService.getInstance();
        if (loginService.getMember() == null) {
            setLogoutMenu();
        } else {
            setLoginMenu();
        }

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        bus.unregister(this);
    }

    public boolean closeMenu(Class<?> activity) {
        if (getActivity().getClass() != activity) {
            drawerMenu.getMenuDrawer().closeDrawer(Gravity.LEFT, false);
            return true;
        } else {
            drawerMenu.getMenuDrawer().closeDrawers();
            if (getActivity() instanceof RefreshData) {
                RefreshData refreshData = (RefreshData) getActivity();
                refreshData.refresh();
                Log.d(TAG, "closeMenu: " + 1);
            }
            return false;
        }

    }

    public void setLogoutMenu() {
        // profileImg.setVisibility(View.GONE);
//        menuListV.setVisibility(View.GONE);
        nameTxt.setText("로그인이 필요합니다.");
    }

    public void setLoginMenu() {
        //profileImg.setVisibility(View.VISIBLE);
//        menuListV.setVisibility(View.VISIBLE);
        nameTxt.setText(loginService.getMember().getName() + " 님(" + loginService.getMember().getLogin_id() + ")");
    }

    @Subscribe
    public void FinishLoad(LoginEvent event) {
        if (event.getState() == 0) {
            setLogoutMenu();
        } else if (event.getState() == 1) {
            setLoginMenu();
        }
    }

    public void refreshLogin() {

        if (loginService.getMember().getVerify().equals("N")) {
            Call<Member> observer = RetrofitService.getInstance().getRetrofitRequest().refreshLoginUser(loginService.getMember().getId());
            observer.enqueue(new Callback<Member>() {
                @Override
                public void onResponse(Call<Member> call, Response<Member> response) {
                    if (response.isSuccessful()) {
                        loginService.refreshMember(response.body());
                    } else {
                        Log.d("로그", "onResponse: fail");
                    }
                }

                @Override
                public void onFailure(Call<Member> call, Throwable t) {
                    t.printStackTrace();
                }
            });
            return;
        }
    }
}
