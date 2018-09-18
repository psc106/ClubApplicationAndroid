package teamproject.com.clubapplication.test;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.data.ExternalImage;
import teamproject.com.clubapplication.utils.CommonUtils;
import teamproject.com.clubapplication.utils.DrawerMenu;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;

public class TestActivity extends AppCompatActivity {
    static String TAG = "로그";
    final int REQUEST_TAKE_ALBUM = 1;
    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText(TestActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(TestActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }


    };

    @BindView(R.id.test_txt_Test)
    TextView textView;
    @BindView(R.id.test_btn)
    TextView btn;
    @BindView(R.id.test_btn2)
    TextView btn2;
    @BindView(R.id.test_horizentalListV)
    RecyclerView recyclerView;
    @BindView(R.id.test_horizentalListV2)
    RecyclerView recyclerView2;
    TestAdapter adapter;
    TestAdapter2 adapter2;

    ArrayList<ExternalImage> imageList;
    ArrayList<String> urlList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();

        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        imageList = new ArrayList<>();
        adapter = new TestAdapter(this, imageList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);

        LinearLayoutManager horizontalLayoutManagaer2
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        urlList = new ArrayList<>();
        adapter2 = new TestAdapter2(this, urlList);
        recyclerView2.setAdapter(adapter2);
        recyclerView2.setLayoutManager(horizontalLayoutManagaer2);

        Call<ArrayList<String>> call = RetrofitService.getInstance().getRetrofitRequest().testImage();
        call.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                if (response.isSuccessful()) {
                    urlList.clear();
                    urlList.addAll(response.body());
                    adapter2.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {

            }
        });

        final Context context = this;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(Intent.createChooser(intent, "다중 선택은 '포토'를 선택하세요."), 1);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<MultipartBody.Part> parts = new ArrayList<>();

                for (int i = 0; i < imageList.size(); ++i) {
                    String name = imageList.get(i).getRealPath();
                    Uri uri = imageList.get(i).getFileUri();
                    parts.add(prepareFilePart(name, uri));
                }

                RequestBody description = createPartFromString("hello, this is description speaking");

                Call<ResponseBody> call = RetrofitService.getInstance().getRetrofitRequest().test(description, parts);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Log.d("테스트", "onResponse: " + "success");
                            imageList.clear();
                            adapter.notifyDataSetChanged();
                            refresh();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

            }
        });
    }

    void refresh() {
        Call<ArrayList<String>> call = RetrofitService.getInstance().getRetrofitRequest().testImage();
        call.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                if (response.isSuccessful()) {
                    urlList.clear();
                    urlList.addAll(response.body());
                    adapter2.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_TAKE_ALBUM:
                if (resultCode == Activity.RESULT_OK) {

                    // 멀티 선택을 지원하지 않는 기기에서는 getClipdata()가 없음 > getData()로 접근해야 함
                    if (data.getClipData() == null) {
                        Log.d(TAG, 1 + "" + CommonUtils.getPath(this, data.getData()));
                        ExternalImage externalImage = new ExternalImage(data.getData(), CommonUtils.getPath(this, data.getData()));
                        imageList.add(externalImage);
                    } else {
                        ClipData clipData = data.getClipData();
                        if (clipData.getItemCount() > 10) {
                            Toast.makeText(this, "사진은 10개까지 선택가능 합니다.", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (clipData.getItemCount() >= 1 && clipData.getItemCount() < 10) {
                            for (int i = 0; i < clipData.getItemCount(); i++) {
                                Uri dataStr = clipData.getItemAt(i).getUri();
                                Log.d(TAG, i + "" + CommonUtils.getPath(this, dataStr));
                                ExternalImage externalImage = new ExternalImage(dataStr, CommonUtils.getPath(this, dataStr));
                                imageList.add(externalImage);
                                if (imageList.size() > 0)
                                    Log.d(TAG, "" + imageList.get(0).getRealPath().equals(externalImage.getRealPath()));
                            }
                        }
                    }
                } else {
                    Toast.makeText(this, "사진 선택을 취소하였습니다.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        adapter.notifyDataSetChanged();
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

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString);
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        File file = new File(CommonUtils.getPath(this, fileUri));

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(getContentResolver().getType(fileUri)),
                        file
                );

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }
}
