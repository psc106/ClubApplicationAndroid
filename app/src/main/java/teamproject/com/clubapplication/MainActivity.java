package teamproject.com.clubapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
    @BindView(R.id.main_spinner_category)
    Spinner mainSpinnerCategory;
    @BindView(R.id.main_spinner_locationDoSi)
    Spinner mainSpinnerLocationDoSi;
    @BindView(R.id.main_spinner_locationSiGunGu)
    Spinner mainSpinnerLocationSiGunGu;
    @BindView(R.id.main_layout_AdvancedSearch)
    LinearLayout mainLayoutAdvancedSearch;
    @BindView(R.id.main_gridV_category)
    ScrollGridview mainGridVCategory;
    @BindView(R.id.main_gridV_local)
    ScrollGridview mainGridVLocal;
    //    GridView mainGridVCategory;
    @BindView(R.id.main_btn_MakeGroup)
    Button mainBtnMakeGroup;


    @OnClick(R.id.main_btn_AdvancedSearch)
    public void searchDetail(View view) {
        if (mainLayoutAdvancedSearch.getVisibility() == View.GONE) {
            mainLayoutAdvancedSearch.setVisibility(View.VISIBLE);
        } else {
            mainLayoutAdvancedSearch.setVisibility(View.GONE);
        }
    }

    //검색
    @OnClick(R.id.main_btn_search)
    public void search(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        if (mainLayoutAdvancedSearch.getVisibility() != View.GONE) {
            if (mainSpinnerLocationDoSi.getSelectedItemPosition() > 0)
                intent.putExtra("localDoSi", mainSpinnerLocationDoSi.getSelectedItemPosition());
            if (mainSpinnerLocationSiGunGu.getSelectedItemPosition() > 0)
                intent.putExtra("localSiGunGu", mainSpinnerLocationSiGunGu.getSelectedItemPosition());
            if (mainSpinnerCategory.getSelectedItemId() > 0)
                intent.putExtra("category", mainSpinnerCategory.getSelectedItemPosition());
        }
        if (mainEditSearch.getText() != null && !mainEditSearch.getText().toString().equals("")) {
            intent.putExtra("main", mainEditSearch.getText().toString());
        }
        startActivity(intent);
    }

    //그룹생성
    @OnClick(R.id.main_btn_MakeGroup)
    public void makeGroup(View view) {
        Intent intent = new Intent(MainActivity.this, GroupMakeActivity.class);
        startActivity(intent);
    }

    MainGridviewAdapter mainGridviewCategoryAdapter;
    MainGridviewAdapter mainGridviewLocalAdapter;
    LoginService loginService;
    DBManager dbManager;

    ArrayList<String> arrayListLocal;
    ArrayList<String> arrayListCategory;

    String[] noneSelect = {"선택"};
    String[] items_category;
    String[] items_locationDoSi;
    String[] items_locationSiGunGu;
    int selectDoSiPosition;
    boolean isSiGunGu = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loginService = LoginService.getInstance();
        dbManager = new DBManager(this, DBManager.DB_NAME, null, DBManager.CURRENT_VERSION);


        items_locationDoSi = dbManager.getDoSi();
        items_category = dbManager.getCategory();

        arrayListCategory = new ArrayList<>(new ArrayList<>(Arrays.asList(items_category)));
        mainGridviewCategoryAdapter = new MainGridviewAdapter(arrayListCategory);
        mainGridVCategory.setAdapter(mainGridviewCategoryAdapter);

        arrayListLocal = new ArrayList<>();
        arrayListLocal.addAll(new ArrayList<>(Arrays.asList(items_locationDoSi)));
        mainGridviewLocalAdapter = new MainGridviewAdapter(arrayListLocal);
        mainGridVLocal.setNumColumns(((int) Math.sqrt(arrayListLocal.size())) + 1);
        mainGridVLocal.setAdapter(mainGridviewLocalAdapter);

        CommonUtils.initSpinner(this, mainSpinnerLocationDoSi, items_locationDoSi, noneSelect);
        CommonUtils.initSpinner(this, mainSpinnerCategory, items_category, noneSelect);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        mainGridVCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("category", position + 1);
                startActivity(intent);

            }
        });

        mainGridVLocal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!isSiGunGu) {
                    selectDoSiPosition = position + 1;
                    String selectDoSi = arrayListLocal.get(position);
                    String[] tmpItem = dbManager.getSiGunGuFromDoSi(selectDoSi);
                    ArrayList<String> tmpArrayList = new ArrayList<>();
                    tmpArrayList.add("뒤로");
                    tmpArrayList.add("검색");
                    tmpArrayList.addAll(new ArrayList<>(Arrays.asList(tmpItem)));
                    arrayListLocal.clear();
                    arrayListLocal.addAll(tmpArrayList);
                    mainGridVLocal.setNumColumns(((int) Math.sqrt(arrayListLocal.size())) + 1);
                    mainGridviewLocalAdapter.notifyDataSetChanged();
                    isSiGunGu = true;
                } else {
                    if (position == 0) {
                        selectDoSiPosition = 0;
                        arrayListLocal.clear();
                        arrayListLocal.addAll(new ArrayList<>(Arrays.asList(items_locationDoSi)));
                        double count = Math.sqrt(arrayListLocal.size());
                        if (count - (int) count == 0) {
                            mainGridVLocal.setNumColumns(((int) Math.sqrt(arrayListLocal.size())));
                        } else {
                            mainGridVLocal.setNumColumns(((int) Math.sqrt(arrayListLocal.size())) + 1);
                        }
                        mainGridviewLocalAdapter.notifyDataSetChanged();
                        isSiGunGu = false;
                    } else if (position == 1) {
                        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                        intent.putExtra("localDoSi", selectDoSiPosition);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                        intent.putExtra("localDoSi", selectDoSiPosition);
                        intent.putExtra("localSiGunGu", position - 1);
                        startActivity(intent);
                    }

                }
            }
        });

        mainSpinnerLocationDoSi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    String selectDoSi = (String) mainSpinnerLocationDoSi.getSelectedItem();
                    if (dbManager != null) {
                        mainSpinnerLocationSiGunGu.setVisibility(View.VISIBLE);
                        items_locationSiGunGu = dbManager.getSiGunGuFromDoSi(selectDoSi);
                        Log.d("로그", "onItemSelected: " + items_locationSiGunGu.toString());
                        CommonUtils.initSpinner(activity, mainSpinnerLocationSiGunGu, items_locationSiGunGu, noneSelect);
                    }
                } else {
                    mainSpinnerLocationSiGunGu.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

        Intent intent;
        intent = new Intent(otherActivity, this.getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void refresh() {
        if (loginService.getMember() != null && loginService.getMember().getVerify().equals("N")) {
            Call<Member> observer = RetrofitService.getInstance().getRetrofitRequest().refreshLoginUser(loginService.getMember().getId());
            observer.enqueue(new Callback<Member>() {
                @Override
                public void onResponse(@NonNull Call<Member> call, @NonNull Response<Member> response) {
                    if (response.isSuccessful()) {
                        loginService.refreshMember(response.body());
                    } else {
                        Log.d("로그", "onResponse: fail");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Member> call, @NonNull Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);     // 여기서 this는 Activity의 this

// 여기서 부터는 알림창의 속성 설정
        builder.setTitle("종료 확인")        // 제목 설정
                .setMessage("앱을 종료 하시 겠습니까?")        // 메세지 설정
                .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    // 확인 버튼 클릭시 설정
                    public void onClick(DialogInterface dialog, int whichButton) {
                        finish();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    // 취소 버튼 클릭시 설정
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();    // 알림창 객체 생성
        dialog.show();    // 알림창 띄우기
    }
}
