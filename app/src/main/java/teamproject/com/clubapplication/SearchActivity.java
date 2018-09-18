package teamproject.com.clubapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamproject.com.clubapplication.adapter.SearchGroupListviewAdapter;
import teamproject.com.clubapplication.data.ClubView;
import teamproject.com.clubapplication.data.Notice;
import teamproject.com.clubapplication.db.DBManager;
import teamproject.com.clubapplication.utils.CommonUtils;
import teamproject.com.clubapplication.utils.DrawerMenu;
import teamproject.com.clubapplication.utils.LoadingDialog;
import teamproject.com.clubapplication.utils.LoginService;
import teamproject.com.clubapplication.utils.RefreshData;
import teamproject.com.clubapplication.utils.customView.KeyHideActivity;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;

public class SearchActivity extends KeyHideActivity implements RefreshData {
    private DrawerMenu drawerMenu;

    @BindView(R.id.search_edt_search)
    EditText edtSearch;
    @BindView(R.id.search_btn_search)
    Button btnSearch;
    @BindView(R.id.search_btn_AdvancedSearch)
    Button btnAdvancedSearch;
    @BindView(R.id.search_spinner_category)
    Spinner spinnerCategory;
    @BindView(R.id.search_spinner_locationDoSi)
    Spinner spinnerLocationDoSi;
    @BindView(R.id.search_spinner_locationSiGunGu)
    Spinner spinnerLocationSiGunGu;
    @BindView(R.id.list_view_search_group)
    ListView listViewSearchGroup;
    @BindView(R.id.btn_make_group_in_search_group)
    Button btnMakeGroupInSearchGroup;
    @BindView(R.id.search_layout_AdvancedSearch)
    LinearLayout advancedLayout;
    @BindView(R.id.search_scroll)
    NestedScrollView scrollView;

    @OnClick(R.id.search_btn_search)
    void research() {
        String tmpMain = null;
        Integer tmpCategory = 0;
        Integer tmpLocalDoSi = 0;
        Integer tmpLocalSiGunGu = 0;

        Intent intent = new Intent(this, SearchActivity.class);
        if (advancedLayout.getVisibility() != View.GONE) {
            if (spinnerLocationDoSi.getSelectedItemPosition() > 0) {
                intent.putExtra("localDoSi", spinnerLocationDoSi.getSelectedItemPosition());
                tmpLocalDoSi = spinnerLocationDoSi.getSelectedItemPosition();
            }
            if (spinnerLocationSiGunGu.getSelectedItemPosition() >0) {
                intent.putExtra("localSiGunGu", spinnerLocationSiGunGu.getSelectedItemPosition());
                tmpLocalSiGunGu = spinnerLocationSiGunGu.getSelectedItemPosition();
            }
            if (spinnerCategory.getSelectedItemId() > 0) {
                intent.putExtra("category", spinnerCategory.getSelectedItemPosition());
                tmpCategory = spinnerCategory.getSelectedItemPosition();
            }
        }
        if (!TextUtils.isEmpty(edtSearch.getText())) {
            intent.putExtra("main", edtSearch.getText().toString());
            tmpMain = edtSearch.getText().toString();
        }
        Log.d("로그", "5");
        if (    (tmpLocalDoSi==localDoSi) && (tmpLocalSiGunGu==localSiGunGu) && (tmpCategory==category) &&
                ((main == null && tmpMain == null) || ((main != null && tmpMain != null) && main.equals(tmpMain)))) {
            Log.d("로그", "6");
            refresh();
        } else {
            Log.d("로그", "7");
            startActivity(intent);
        }
    }

    @OnClick(R.id.search_btn_AdvancedSearch)
    public void searchDetail(View view) {
        if (advancedLayout.getVisibility() != View.GONE) {
            advancedLayout.setVisibility(View.GONE);
        } else {
            advancedLayout.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.btn_make_group_in_search_group)
    public void makeGroup() {
        Intent intent = new Intent(SearchActivity.this, GroupMakeActivity.class);
        startActivity(intent);
    }

    String[] noneSelect = {"선택"};
    String[] items_category;
    String[] items_locationDoSi;
    String[] items_locationSiGunGu;
    int page;
    int resultCount;

    ArrayList<ClubView> arrayList;
    DBManager dbManager;
    LoginService loginService;

    SearchGroupListviewAdapter searchGroupListviewAdapter;
    String local;
    String main;
    int category;
    int localDoSi, localSiGunGu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        dbManager = new DBManager(this, DBManager.DB_NAME, null, DBManager.CURRENT_VERSION);
        loginService = LoginService.getInstance();


        Intent intent = getIntent();
        main = intent.getStringExtra("main");
        category = intent.getIntExtra("category", 0);
        localDoSi = intent.getIntExtra("localDoSi", 0);
        localSiGunGu = intent.getIntExtra("localSiGunGu", 0);

        Log.d("로그", "onCreate: "+main);
        Log.d("로그", "onCreate: "+category);
        Log.d("로그", "onCreate: "+localDoSi);
        Log.d("로그", "onCreate: "+localSiGunGu);

        items_locationDoSi = dbManager.getDoSi();
        items_category = dbManager.getCategory();

        CommonUtils.initSpinner(this, spinnerLocationDoSi, items_locationDoSi, noneSelect);
        CommonUtils.initSpinner(this, spinnerCategory, items_category, noneSelect);

        spinnerCategory.setSelection(category);
        spinnerLocationDoSi.setSelection(localDoSi);
        edtSearch.setText(main);
        if (localDoSi > 0) {
            spinnerLocationSiGunGu.setVisibility(View.VISIBLE);
            items_locationSiGunGu = dbManager.getSiGunGuFromDoSi((String) spinnerLocationDoSi.getSelectedItem());
            CommonUtils.initSpinner(this, spinnerLocationSiGunGu, items_locationSiGunGu, noneSelect);
            spinnerLocationSiGunGu.setSelection(localSiGunGu);
        }

        arrayList = new ArrayList<>();
        searchGroupListviewAdapter = new SearchGroupListviewAdapter(this, arrayList);
        listViewSearchGroup.setAdapter(searchGroupListviewAdapter);

        listViewSearchGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), GroupActivity.class);
                intent.putExtra("clubId", arrayList.get(position).getId());
                startActivity(intent);
            }
        });

        spinnerLocationDoSi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position>0) {
                    String selectDoSi = (String) spinnerLocationDoSi.getSelectedItem();
                    if(dbManager!=null) {
                        spinnerLocationSiGunGu.setVisibility(View.VISIBLE);
                        items_locationSiGunGu = dbManager.getSiGunGuFromDoSi(selectDoSi);
                        Log.d("로그", "onItemSelected: "+items_locationSiGunGu.toString());
                        CommonUtils.initSpinner(SearchActivity.this, spinnerLocationSiGunGu, items_locationSiGunGu, noneSelect);
                        spinnerLocationSiGunGu.setSelection(localSiGunGu);
                    }
                } else {
                    localSiGunGu = 0;
                    spinnerLocationSiGunGu.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollView.getChildAt(0).getBottom()
                        <= (scrollView.getHeight() + scrollView.getScrollY())) {
                    if(page*10<resultCount) {
                        page++;
                        getData();

                        scrollView.fling(0);
                        scrollView.scrollBy(0, 0);
//                    }
                    }

                }
            }
        });


        refresh();
    }

    @Override
    protected void onResume() {
        super.onResume();



        if (loginService.getMember() == null || !loginService.getMember().isVerifyMember()) {
            btnMakeGroupInSearchGroup.setVisibility(View.GONE);
        } else {
            if (btnMakeGroupInSearchGroup.getVisibility() == View.GONE)
                btnMakeGroupInSearchGroup.setVisibility(View.VISIBLE);
        }
        if (drawerMenu == null) {
            drawerMenu = DrawerMenu.addMenu(this, R.id.search_group_menu, R.id.search_group_drawer);
        } else {
            drawerMenu.restartMenu(this, R.id.search_group_menu, R.id.search_group_drawer);
        }
    }

    @Override
    public void refresh() {
        page = 1;
        getCount();
    }

    public void getData() {
        local = "";
        if(spinnerLocationDoSi.getSelectedItemPosition()>0) {
            local = (String) spinnerLocationDoSi.getSelectedItem();
            if (spinnerLocationSiGunGu.getSelectedItemPosition() > 0) {
                local += " " + (String) spinnerLocationSiGunGu.getSelectedItem();
            }
        }
        Call<ArrayList<ClubView>> observer = RetrofitService.getInstance().getRetrofitRequest().selectClubInPage(main, local, category, page);
        observer.enqueue(new Callback<ArrayList<ClubView>>() {
            @Override
            public void onResponse(Call<ArrayList<ClubView>> call, Response<ArrayList<ClubView>> response) {
                if (response.isSuccessful()) {
                    arrayList.addAll(response.body());
                    searchGroupListviewAdapter.notifyDataSetChanged();
                    CommonUtils.setListviewHeightBasedOnChildren(listViewSearchGroup);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ClubView>> call, Throwable t) {
            }
        });
    }

    public void getCount() {
        local = "";
        if(spinnerLocationDoSi.getSelectedItemPosition()>0) {
            local = (String) spinnerLocationDoSi.getSelectedItem();
            if (spinnerLocationSiGunGu.getSelectedItemPosition() > 0) {
                local += " " + (String) spinnerLocationSiGunGu.getSelectedItem();
            }
        }
        Call<Integer> observer = RetrofitService.getInstance().getRetrofitRequest().getResultCount(main, local, category);
        observer.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    resultCount = response.body();
                    if (resultCount > 0) {
                        arrayList.clear();
                        getData();
                    }
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
            }
        });

    }

    @Override
    public void onBackPressed() {
        LoadingDialog.getInstance().progressOFF();
        super.onBackPressed();
    }
}
