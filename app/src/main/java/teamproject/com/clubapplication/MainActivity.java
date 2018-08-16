package teamproject.com.clubapplication;

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
import butterknife.Unbinder;
import teamproject.com.clubapplication.Adapter.GvAdapter;


public class MainActivity extends AppCompatActivity {
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
    int a = 0;
    GvAdapter gvAdapter;
    int [] img = {R.drawable.ic_launcher_background,R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_background,R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_background,R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_background,R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_background};
    String[]items_location={"서울","경기","이천","전라도","경상도","충청도","강원도","제주"};
    String[]itmes_category={"여행","음식","음악","문화","기타","등등","모르","겄다","...."};
    ArrayList<Integer> imgs = new ArrayList<>();

    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        gvAdapter= new GvAdapter(imgs);
        gvCategory.setAdapter(gvAdapter);
        for(int i = 0 ; i <img.length;i++){
            imgs.add(img[i]);


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

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("asd","aaaa");
            }
        });
    }


    @OnClick(R.id.btn_search_detail)
    public void searchDetail(View view) {
        Log.d("asd","commit");
        if (a == 0) {
            search_location.setVisibility(View.VISIBLE);
            search_category.setVisibility(View.VISIBLE);
            a = 1;
        } else if (a == 1) {
            search_location.setVisibility(View.GONE);
            search_category.setVisibility(View.GONE);
            a = 0;
        }

    }
}
