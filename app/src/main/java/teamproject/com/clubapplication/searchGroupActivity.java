package teamproject.com.clubapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class searchGroupActivity extends AppCompatActivity {

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

    String[]items_location={"서울","경기","인천","전라도","경상도","충청도","강원도","제주"};
    String[]itmes_category={"여행","음식","음악","문화","기타","등등","모르","겄다","...."};

    int check_detail=0;

    private DrawerMenu drawerMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_group);
        ButterKnife.bind(this);

        Spinner adt_spinner_category = (Spinner)findViewById(R.id.spinner_category_in_search_group);
        Spinner adt_spinner_location = (Spinner)findViewById(R.id.spinner_location_in_search_group);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,items_location);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,itmes_category);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adt_spinner_category.setAdapter(adapter2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adt_spinner_location.setAdapter(adapter);

    }
    @OnClick(R.id.btn_search_detail_in_search_group)
    public void searchDetail(View view) {
        Log.d("asd", "commit");
        if (check_detail == 0) {
            searchLocationInSearchGroup.setVisibility(View.VISIBLE);
            searchCategoryInSearchGroup.setVisibility(View.VISIBLE);
            check_detail = 1;
        } else if (check_detail == 1) {
            searchLocationInSearchGroup.setVisibility(View.GONE);
            searchCategoryInSearchGroup.setVisibility(View.GONE);
            check_detail = 0;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (drawerMenu == null) {
            drawerMenu = DrawerMenu.addMenu(this, R.id.search_group_menu, R.id.search_group_drawer);
        } else {
            drawerMenu.restartMenu(this, R.id.search_group_menu, R.id.search_group_drawer);
        }
    }
}
