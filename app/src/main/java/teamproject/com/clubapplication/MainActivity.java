package teamproject.com.clubapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamproject.com.clubapplication.adapter.MainGridviewAdapter;
import teamproject.com.clubapplication.data.Member;
import teamproject.com.clubapplication.db.DBManager;
import teamproject.com.clubapplication.utils.CommonUtils;
import teamproject.com.clubapplication.utils.DrawerMenu;
import teamproject.com.clubapplication.utils.customView.KeyHideActivity;
import teamproject.com.clubapplication.utils.LoginService;
import teamproject.com.clubapplication.utils.RefreshData;
import teamproject.com.clubapplication.utils.customView.ScrollGridview;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;


public class MainActivity extends KeyHideActivity implements RefreshData {
    public static Activity activity;
    private DrawerMenu drawerMenu;

    @BindView(R.id.main_edt_search)
    EditText mainEditSearch;
    @BindView(R.id.main_btn_search)
    Button mainBtnSearch;
    @BindView(R.id.main_btn_AdvancedSearch)
    Button mainBtnAdvancedSearch;
    @BindView(R.id.main_spinner_location)
    Spinner mainSpinnerLocation;
    @BindView(R.id.main_spinner_category)
    Spinner mainSpinnerCategory;
    @BindView(R.id.main_layout_AdvancedSearch)
    LinearLayout mainLayoutAdvancedSearch;
    @BindView(R.id.main_gridV_category)
    ScrollGridview mainGridVCategory;
//    GridView mainGridVCategory;
    @BindView(R.id.main_btn_MakeGroup)
    Button mainBtnMakeGroup;


    @OnClick(R.id.main_btn_AdvancedSearch)
    public void searchDetail(View view) {
        Log.d("asd", "commit");
        if(mainLayoutAdvancedSearch.getVisibility()==View.GONE) {
            mainLayoutAdvancedSearch.setVisibility(View.VISIBLE);
        } else {
            mainLayoutAdvancedSearch.setVisibility(View.GONE);
        }
    }

    //검색
    @OnClick(R.id.main_btn_search)
    public void search(View view) {
        Intent intent = new Intent(this, SearchGroupActivity.class);
        if(mainLayoutAdvancedSearch.getVisibility()!=View.GONE) {
            if(mainSpinnerLocation.getSelectedItemId()!=0)
                intent.putExtra("local", mainSpinnerLocation.getSelectedItemId());
            if(mainSpinnerCategory.getSelectedItemId()!=0)
                intent.putExtra("category", mainSpinnerCategory.getSelectedItemId());
        }
        if(mainEditSearch.getText()!=null && !mainEditSearch.getText().toString().equals("")) {
            intent.putExtra("main", mainEditSearch.getText().toString());
        }
        startActivity(intent);
    }

    //그룹생성
    @OnClick(R.id.main_btn_MakeGroup)
    public void makeGroup(View view) {
        Intent intent = new Intent(MainActivity.this, MakeGroupActivity.class);
        startActivity(intent);

    }

    MainGridviewAdapter mainGridviewAdapter;
    LoginService loginService;
    DBManager dbManager;

    ArrayList<String> arrayList;

    String[] noneSelect = {"선택"};
    String[] items_category = {"취미1", "취미2", "취미3", "취미4", "취미5", "취미6", "취미7", "취미8", "취미9"};
    String[] items_location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loginService = LoginService.getInstance();
        dbManager = new DBManager(this, DBManager.DB_NAME, null, DBManager.CURRENT_VERSION);

        arrayList = new ArrayList<>(new ArrayList<>(Arrays.asList(items_category)));
        mainGridviewAdapter = new MainGridviewAdapter(arrayList);
        mainGridVCategory.setAdapter(mainGridviewAdapter);

        items_location = CommonUtils.concatAll(noneSelect, dbManager.getDoSi());
        items_category = CommonUtils.concatAll(noneSelect, items_category);
        ArrayAdapter<String> adapterLocal = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items_location);
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items_category);
        adapterLocal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mainSpinnerLocation.setAdapter(adapterLocal);
        mainSpinnerCategory.setAdapter(adapterCategory);

        Toolbar toolbar = (Toolbar)findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.main_collapsingLayout);
        collapsingToolbarLayout.setTitle("타이틀");

    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();

        if (loginService.getMember() == null || !loginService.getMember().isVerifyMember()) {
            mainBtnMakeGroup.setVisibility(View.GONE);
        } else {
            if (mainBtnMakeGroup.getVisibility() == View.GONE)
                mainBtnMakeGroup.setVisibility(View.VISIBLE);
        }

        if (drawerMenu == null) {
            drawerMenu = DrawerMenu.addMenu(this, R.id.main_menu, R.id.main_drawer);
        } else {
            drawerMenu.restartMenu(this, R.id.main_menu, R.id.main_drawer);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void backHomeActivity(Activity otherActivity) {
        Intent intent = new Intent(otherActivity, this.getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void refresh() {
        if(loginService.getMember()!=null && loginService.getMember().getVerify().equals("N")){
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
        }
    }

    private void setupToolbar(){
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        // Show menu icon
//        final ActionBar ab = getSupportActionBar();
    }

}
