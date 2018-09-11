package teamproject.com.clubapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamproject.com.clubapplication.adapter.SearchGroupListviewAdapter;
import teamproject.com.clubapplication.data.Club;
import teamproject.com.clubapplication.db.DBManager;
import teamproject.com.clubapplication.utils.CommonUtils;
import teamproject.com.clubapplication.utils.DrawerMenu;
import teamproject.com.clubapplication.utils.LoginService;
import teamproject.com.clubapplication.utils.RefreshData;
import teamproject.com.clubapplication.utils.customView.KeyHideActivity;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;

public class SearchActivity extends KeyHideActivity implements RefreshData {
    private DrawerMenu drawerMenu;

    @BindView(R.id.text_search_in_search_group)
    EditText textSearchInSearchGroup;

    @BindView(R.id.btn_search_in_search_group)
    Button btnSearchInSearchGroup;

    @BindView(R.id.search_btn_AdvancedSearch)
    Button advancedBtn;

    @BindView(R.id.spinner_location_in_search_group)
    Spinner spinnerLocationInSearchGroup;

    @BindView(R.id.spinner_category_in_search_group)
    Spinner spinnerCategoryInSearchGroup;

    @BindView(R.id.list_view_search_group)
    ListView listViewSearchGroup;

    @BindView(R.id.btn_make_group_in_search_group)
    Button btnMakeGroupInSearchGroup;

    @BindView(R.id.search_layout_AdvancedSearch)
    LinearLayout advancedLayout;

    @OnClick(R.id.btn_search_in_search_group)
    void research() {
        String tmpLocal = null;
        String tmpMain = null;
        Long tmpCategory = -1L;

        Intent intent = new Intent(this, SearchActivity.class);
        if (advancedLayout.getVisibility() != View.GONE) {
            Log.d("로그", "1");
            if (spinnerLocationInSearchGroup.getSelectedItemId() != 0) {
                Log.d("로그", "2");
                tmpLocal = (String) spinnerLocationInSearchGroup.getSelectedItem();
                intent.putExtra("local", tmpLocal);
            }
            if (spinnerCategoryInSearchGroup.getSelectedItemId() != 0) {
                Log.d("로그", "3");
                tmpCategory = spinnerCategoryInSearchGroup.getSelectedItemId();
                intent.putExtra("category", tmpCategory);
            }
        }
        if (textSearchInSearchGroup.getText() != null && !textSearchInSearchGroup.getText().toString().equals("")) {
            Log.d("로그", "4");
            tmpMain = textSearchInSearchGroup.getText().toString();
            intent.putExtra("main", tmpMain);
        }
        Log.d("로그", "5");
        if (((local == null && tmpLocal == null) || ((local != null && tmpLocal != null) && local.equals(tmpLocal))) &&
                ((main == null && tmpMain == null) || ((main != null && tmpMain != null) && main.equals(tmpMain))) &&
            category == tmpCategory) {
            Log.d("로그", "6");
            refresh();
        } else {
            Log.d("로그", "7");
            startActivity(intent);
        }
    }

    @OnClick(R.id.search_btn_AdvancedSearch)
    public void searchDetail(View view) {
        if(advancedLayout.getVisibility()!=View.GONE) {
            advancedLayout.setVisibility(View.GONE);
        } else {
            advancedLayout.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.btn_make_group_in_search_group)
    public  void makeGroup(){
        Intent intent = new Intent(SearchActivity.this,GroupMakeActivity.class);
        startActivity(intent);
    }

    String[] noneSelect = {"선택"};
    String[] items_category;
    String[] items_location;
    int page;
    int resultCount;

    ArrayList<Club> arrayList;
    DBManager dbManager;
    LoginService loginService;

    SearchGroupListviewAdapter searchGroupListviewAdapter;
    String local;
    String main;
    Long category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        dbManager = new DBManager(this, DBManager.DB_NAME, null, DBManager.CURRENT_VERSION);
        loginService = LoginService.getInstance();
        items_location = dbManager.getDoSi();

        Intent intent = getIntent();
        local = intent.getStringExtra("local");
        main = intent.getStringExtra("main");
        category = intent.getLongExtra("category", -1L);


        arrayList = new ArrayList<>();
        searchGroupListviewAdapter = new SearchGroupListviewAdapter(arrayList);
        listViewSearchGroup.setAdapter(searchGroupListviewAdapter);

        listViewSearchGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), GroupActivity.class);
                intent.putExtra("clubId", arrayList.get(position).getId());
                startActivity(intent);
            }
        });
        items_location = dbManager.getDoSi();
        items_category = dbManager.getCategory();

        CommonUtils.initSpinner(this, spinnerLocationInSearchGroup, items_location, noneSelect);
        CommonUtils.initSpinner(this, spinnerCategoryInSearchGroup, items_category, noneSelect);

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
        Call<ArrayList<Club>> observer = RetrofitService.getInstance().getRetrofitRequest().selectClubInPage(main, local, category, page);
        observer.enqueue(new Callback<ArrayList<Club>>() {
            @Override
            public void onResponse(Call<ArrayList<Club>> call, Response<ArrayList<Club>> response) {
                if(response.isSuccessful()){
                    arrayList.addAll(response.body());
                    CommonUtils.setListviewHeightBasedOnChildren(listViewSearchGroup);
                    searchGroupListviewAdapter.notifyDataSetChanged();
                    CommonUtils.setListviewHeightBasedOnChildren(listViewSearchGroup);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Club>> call, Throwable t) {
            }
        });
    }

    public void getCount(){
        Call<Integer> observer = RetrofitService.getInstance().getRetrofitRequest().getResultCount(main, local, category);
        observer.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    resultCount = response.body();
                    if(resultCount>0) {
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
}
