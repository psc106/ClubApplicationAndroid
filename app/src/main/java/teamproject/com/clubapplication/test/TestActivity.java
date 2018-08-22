package teamproject.com.clubapplication.test;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import retrofit2.Retrofit;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.utils.CommonUtils;
import teamproject.com.clubapplication.utils.DrawerMenu;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;

public class TestActivity extends AppCompatActivity {
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
    @BindView(R.id.test_horizentalListV)
    RecyclerView recyclerView;

    ArrayList<Uri> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        imageList = new ArrayList<>();


        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        .check();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(Intent.createChooser(intent,"다중 선택은 '포토'를 선택하세요."), 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_TAKE_ALBUM:
                Log.i("result", String.valueOf(resultCode));
                if (resultCode == Activity.RESULT_OK) {

                    // 멀티 선택을 지원하지 않는 기기에서는 getClipdata()가 없음 > getData()로 접근해야 함
                    if (data.getClipData() == null) {
                        imageList.add(data.getData());
                    } else {

                        ClipData clipData = data.getClipData();
                        Log.i("clipdata", String.valueOf(clipData.getItemCount()));
                        if (clipData.getItemCount() > 10){
                            Toast.makeText(this, "사진은 10개까지 선택가능 합니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        // 멀티 선택에서 하나만 선택했을 경우
                        else if (clipData.getItemCount() == 1) {
                            Uri dataStr = clipData.getItemAt(0).getUri();
                            imageList.add(dataStr);

                        } else if (clipData.getItemCount() > 1 && clipData.getItemCount() < 10) {
                            for (int i = 0; i < clipData.getItemCount(); i++) {
                                Log.i("3. single choice", String.valueOf(clipData.getItemAt(i).getUri()));
                                imageList.add(clipData.getItemAt(i).getUri());
                                Log.d("로그", "1 "+clipData.getItemAt(i).getUri());
                            }
                        }
                    }

                    ArrayList<MultipartBody.Part> parts = new ArrayList<>();

                    for (int i = 0 ; i < imageList.size(); ++i) {
                        Log.d("로그", "2 "+imageList.get(i));
                        String name = CommonUtils.getPath(this, imageList.get(i));
                        parts.add(prepareFilePart(name, imageList.get(i)));
                    }

                    RequestBody description = createPartFromString("hello, this is description speaking");

                    Call<ResponseBody> call = RetrofitService.getInstance().getRetrofitRequest().test(description, parts);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.isSuccessful()) {
                                Log.d("테스트", "onResponse: "+"success");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });

                } else {
                    Toast.makeText(this, "사진 선택을 취소하였습니다.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
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
