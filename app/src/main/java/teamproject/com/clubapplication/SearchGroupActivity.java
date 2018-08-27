package teamproject.com.clubapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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
import teamproject.com.clubapplication.utils.RefreshData;
import teamproject.com.clubapplication.utils.customView.KeyHideActivity;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;

public class SearchGroupActivity extends KeyHideActivity implements RefreshData {
    private DrawerMenu drawerMenu;

    @BindView(R.id.text_search_in_search_group)
    EditText textSearchInSearchGroup;
    @BindView(R.id.btn_search_in_search_group)
    Button btnSearchInSearchGroup;
    @BindView(R.id.btn_search_detail_in_search_group)
    Button btnSearchDetailInSearchGroup;
    @BindView(R.id.spinner_location_in_search_group)
    Spinner spinnerLocationInSearchGroup;
    @BindView(R.id.search_location_in_search_group)
    LinearLayout searchLocationInSearchGroup;
    @BindView(R.id.spinner_category_in_search_group)
    Spinner spinnerCategoryInSearchGroup;
    @BindView(R.id.search_category_in_search_group)
    LinearLayout searchCategoryInSearchGroup;
    @BindView(R.id.list_view_search_group)
    ListView listViewSearchGroup;
    @BindView(R.id.btn_make_group_in_search_group)
    Button btnMakeGroupInSearchGroup;

    String[] noneSelect = {"선택"};
    String[] items_category = {"취미1", "취미2", "취미3", "취미4", "취미5", "취미6", "취미7", "취미8", "취미9"};
    String[] items_location;
    int page;

    ArrayList<Club> arrayList;
    DBManager dbManager;

    SearchGroupListviewAdapter searchGroupListviewAdapter;
    String local;
    String main;
    Long category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_group);
        ButterKnife.bind(this);
        dbManager = new DBManager(this, DBManager.DB_NAME, null, DBManager.CURRENT_VERSION);
        items_location = dbManager.getDoSi();
        Intent intent = getIntent();
        local = intent.getStringExtra("local");
        main = intent.getStringExtra("main");
        category = intent.getLongExtra("category", -1);
        page = 1;

        arrayList = new ArrayList<>();
        searchGroupListviewAdapter = new SearchGroupListviewAdapter(arrayList);
        listViewSearchGroup.setAdapter(searchGroupListviewAdapter);


        items_location = CommonUtils.concatAll(noneSelect, dbManager.getDoSi());
        items_category = CommonUtils.concatAll(noneSelect, items_category);
        ArrayAdapter<String> adapterLocal = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items_location);
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items_category);
        adapterLocal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocationInSearchGroup.setAdapter(adapterLocal);
        spinnerCategoryInSearchGroup.setAdapter(adapterCategory);

    }
    @OnClick(R.id.btn_search_detail_in_search_group)
    public void searchDetail(View view) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (drawerMenu == null) {
            drawerMenu = DrawerMenu.addMenu(this, R.id.search_group_menu, R.id.search_group_drawer);
        } else {
            drawerMenu.restartMenu(this, R.id.search_group_menu, R.id.search_group_drawer);
        }

        Call<ArrayList<Club>> observer = RetrofitService.getInstance().getRetrofitRequest().selectSearchClub(main, local, category);
        observer.enqueue(new Callback<ArrayList<Club>>() {
            @Override
            public void onResponse(Call<ArrayList<Club>> call, Response<ArrayList<Club>> response) {
                if(response.isSuccessful()){
                    arrayList.addAll(response.body());
                    searchGroupListviewAdapter.notifyDataSetChanged();

                }
            }
            @Override
            public void onFailure(Call<ArrayList<Club>> call, Throwable t) {

            }
        });

    }
    @OnClick(R.id.btn_make_group_in_search_group)
    public  void makeGroup(){
        Intent intent = new Intent(SearchGroupActivity.this,MakeGroupActivity.class);
        startActivity(intent);
    }


    @Override
    public void refresh() {

    }
}
