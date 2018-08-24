package teamproject.com.clubapplication;

import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import teamproject.com.clubapplication.utils.DrawerMenu;
import teamproject.com.clubapplication.utils.KeyHideActivity;

public class MakeGroupActivity extends KeyHideActivity {


    @BindView(R.id.img_group_photo)
    ImageView imgGroupPhoto;
    @BindView(R.id.btn_select_group_photo)
    Button btnSelectGroupPhoto;
    @BindView(R.id.text_group_name)
    EditText textGroupName;
    @BindView(R.id.spinner_group_category)
    Spinner spinnerGroupCategory;
    @BindView(R.id.spinner_group_count)
    Spinner spinnerGroupCount;
    @BindView(R.id.spinner_group_location)
    Spinner spinnerGroupLocation;
    @BindView(R.id.text_group_info)
    EditText textGroupInfo;

    String[]items_location={"서울","경기","이천","전라도","경상도","충청도","강원도","제주"};
    String[]itmes_category={"여행","음식","음악","문화","기타","등등","모르","겄다","...."};
    String[]items_count=new String[19];

    private DrawerMenu drawerMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_group);
        ButterKnife.bind(this);



        for(int i = 0; i <=18;i++){
            Integer num =i+2;
            String str = num.toString();
            items_count[i]=str;
        }
        Spinner spinnerGroupCategory = (Spinner)findViewById(R.id.spinner_group_category);
        Spinner spinnerGroupCount = (Spinner)findViewById(R.id.spinner_group_count);
        Spinner spinnerGroupLocation = (Spinner)findViewById(R.id.spinner_group_location);

        ArrayAdapter<String> adapter_location = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,items_location);
        adapter_location.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> adapter_category = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,itmes_category);
        adapter_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> adapter_count = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,items_count);

        adapter_count.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGroupCategory.setAdapter(adapter_category);
        adapter_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGroupCount.setAdapter(adapter_count);
        adapter_location.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGroupLocation.setAdapter(adapter_location);

    }
    @Override
    protected void onResume() {
        super.onResume();
        if (drawerMenu == null) {
            drawerMenu = DrawerMenu.addMenu(this, R.id.make_group_menu, R.id.make_group_drawer);
        } else {
            drawerMenu.restartMenu(this, R.id.make_group_menu, R.id.make_group_drawer);
        }
    }


}
