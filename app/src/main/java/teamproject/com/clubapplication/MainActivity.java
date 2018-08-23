package teamproject.com.clubapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import teamproject.com.clubapplication.adapter.MainGridviewAdapter;
import teamproject.com.clubapplication.utils.DrawerMenu;


public class MainActivity extends AppCompatActivity {
    public static Activity activity;

    @BindView(R.id.text_search)
    EditText search;
    @BindView(R.id.btn_search)
    Button btn_search;
    @BindView(R.id.btn_search_detail)
    Button btn_search_detail;
    @BindView(R.id.search_location)
    LinearLayout search_location;
    @BindView(R.id.search_category)
    LinearLayout search_category;
    @BindView(R.id.spinner_category)
    Spinner spinner_category;
    @BindView(R.id.spinner_location)
    Spinner spinner_location;
    @BindView(R.id.btn_make_group)
    Button btn_make_group;
    @BindView(R.id.gv_category)
    GridView gvCategory;
    int check_detail = 0;
    MainGridviewAdapter mainGridviewAdapter;
    int [] img = {R.drawable.ic_launcher_background,R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_background,R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_background,R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_background,R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_background};

    String[]items_location={"서울","경기","인천","전라도","경상도","충청도","강원도","제주"};
    String[]itmes_category={"여행","음식","음악","문화","기타","등등","모르","겄다","...."};

    ArrayList<?> arrayList;


    private DrawerMenu drawerMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        arrayList= new ArrayList<>();
        mainGridviewAdapter = new MainGridviewAdapter(arrayList);
        gvCategory.setAdapter(mainGridviewAdapter);



            Spinner adt_spinner_category = (Spinner)findViewById(R.id.spinner_category);
            Spinner adt_spinner_location = (Spinner)findViewById(R.id.spinner_location);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,items_location);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,itmes_category);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adt_spinner_category.setAdapter(adapter2);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adt_spinner_location.setAdapter(adapter);



    }


    @OnClick(R.id.btn_search_detail)
    public void searchDetail(View view) {
        Log.d("asd","commit");
        if (check_detail == 0) {
            search_location.setVisibility(View.VISIBLE);
            search_category.setVisibility(View.VISIBLE);
            check_detail = 1;
        } else if (check_detail == 1) {
            search_location.setVisibility(View.GONE);
            search_category.setVisibility(View.GONE);
            check_detail = 0;
        }

    }
    //검색
    @OnClick(R.id.btn_search)
    public void search(View view){

        if(check_detail==0){
            //검색어
            String str_search = search.getText().toString();

        }else if(check_detail==1){
            //검색어 , 위치 , 카테고리 지정
            String str_search = search.getText().toString();
            String str_location = spinner_location.getSelectedItem().toString();
            String str_category = spinner_category.getSelectedItem().toString();
        }


    }
    //그룹생성
    @OnClick(R.id.btn_make_group)
    public void makeGroup(View view){
        Intent intent = new Intent(MainActivity.this,MakeGroupActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (drawerMenu == null) {
            drawerMenu = DrawerMenu.addMenu(this, R.id.main_menu, R.id.main_drawer);
        } else {
            drawerMenu.restartMenu(this, R.id.main_menu, R.id.main_drawer);
        }
    }
    public void backHomeActivity(Activity otherActivity) {
        Intent intent = new Intent(otherActivity, this.getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
