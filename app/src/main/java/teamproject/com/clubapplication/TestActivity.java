package teamproject.com.clubapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamproject.com.clubapplication.data.TestData;
import teamproject.com.clubapplication.utils.DrawerMenu;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;

public class TestActivity extends AppCompatActivity {
    @BindView(R.id.test_txt_Test)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        Call<ArrayList<TestData>> observer = RetrofitService.getInstance().getRetrofitRequest().getTest();
        observer.enqueue(new Callback<ArrayList<TestData>>() {
             @Override
             public void onResponse(Call<ArrayList<TestData>> call, Response<ArrayList<TestData>> response) {
                 if (response.isSuccessful()) {
                     ArrayList<TestData> tmp = response.body();
                     textView.setText(tmp.toString());
                     Log.d("로그", "onResponse: suc" + tmp.toString());
                 } else {
                     Log.d("로그", "onResponse: fail");
                 }

             }

             @Override
             public void onFailure(Call<ArrayList<TestData>> call, Throwable t) {

             }
         });
    }


    DrawerMenu drawerMenu;
    @Override
    protected void onResume() {
        super.onResume();

        if (drawerMenu == null) {
            drawerMenu = DrawerMenu.addMenu(this, R.id.test_menu, R.id.test_drawer);
        } else {
            drawerMenu.restartMenu(this, R.id.test_menu, R.id.test_drawer);
        }
    }
}
