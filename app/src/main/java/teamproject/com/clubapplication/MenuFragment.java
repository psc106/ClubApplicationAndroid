package teamproject.com.clubapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MenuFragment extends Fragment {
    private static MenuFragment menuFragment;
    public static MenuFragment getInstance() {
        if(menuFragment==null) {
            menuFragment = new MenuFragment();
        }
        return menuFragment;
    }
    public MenuFragment(){};

    @BindView(R.id.menu_btn_Login)
    Button loginBtn;
    @BindView(R.id.menu_imgV_Profile)
    ImageView profileImg;
    @BindView(R.id.menu_txt_Name)
    TextView nameTxt;

    @OnClick(R.id.menu_btn_Login)
    void toggleLogin() {
        Toast.makeText(getContext(), "로그인 버튼", Toast.LENGTH_SHORT).show();
    }
    @OnClick(R.id.menu_imgV_Profile)
    void changeImage() {
        Toast.makeText(getContext(), "이미지 변경", Toast.LENGTH_SHORT).show();
    }
    @OnClick(R.id.menu_txt_Name)
    void configInfo() {
        Toast.makeText(getContext(), "정보 변경", Toast.LENGTH_SHORT).show();
    }

    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
