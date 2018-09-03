package teamproject.com.clubapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamproject.com.clubapplication.data.ExternalImage;
import teamproject.com.clubapplication.db.DBManager;
import teamproject.com.clubapplication.utils.CommonUtils;
import teamproject.com.clubapplication.utils.DrawerMenu;
import teamproject.com.clubapplication.utils.LoginService;
import teamproject.com.clubapplication.utils.customView.KeyHideActivity;
import teamproject.com.clubapplication.utils.glide.GlideApp;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;

public class MakeGroupActivity extends KeyHideActivity {
    public static Activity activity;
    final int REQUEST_TAKE_ALBUM = 1;

    @BindView(R.id.img_group_photo)
    ImageView imgGroupPhoto;
    @BindView(R.id.btn_select_group_photo)
    Button btnSelectGroupPhoto;
    @BindView(R.id.text_group_name)
    EditText textGroupName;
    @BindView(R.id.make_edt_Count)
    EditText countEdt;
    @BindView(R.id.spinner_group_category)
    Spinner spinnerGroupCategory;
    @BindView(R.id.spinner_group_location)
    Spinner spinnerGroupLocation;
    @BindView(R.id.text_group_info)
    EditText textGroupInfo;
    @BindView(R.id.make_btn_Make)
    Button makeBtn;
    @BindView(R.id.make_btn_CancelImage)
    ImageButton cancelBtn;

    String[] noneSelect = {"선택"};
    String[] items_category;
    String[] items_location;

    DBManager dbManager;
    LoginService loginService;
    private DrawerMenu drawerMenu;
    ExternalImage externalImage;

    @OnClick(R.id.make_btn_CancelImage)
    void photoCancel() {
        if(cancelBtn.getVisibility()== View.VISIBLE) {
            cancelBtn.setVisibility(View.GONE);
            GlideApp.with(this).load(new ColorDrawable(Color.parseColor("#3789b0"))).into(imgGroupPhoto);
        }
    }
    @OnClick(R.id.btn_select_group_photo)
    void photoSelect() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(Intent.createChooser(intent,"다중 선택은 '포토'를 선택하세요."), 1);
    }

    @OnClick(R.id.make_btn_Make)
    void make() {
        if(loginService.getMember()!=null) {
            MultipartBody.Part part = null;
            if(cancelBtn.getVisibility()==View.VISIBLE)
                part = prepareFilePart(externalImage.getRealPath(), externalImage.getFileUri());
            RequestBody category = createPartFromString(String.valueOf(spinnerGroupCategory.getSelectedItemId()));
            RequestBody userId = createPartFromString(String.valueOf(loginService.getMember().getId()));
            RequestBody maxPeople = createPartFromString(String.valueOf(countEdt.getText().toString()));
            RequestBody name = createPartFromString(textGroupName.getText().toString());
            RequestBody intro = createPartFromString(textGroupInfo.getText().toString());
            RequestBody local = createPartFromString((String) spinnerGroupLocation.getSelectedItem());
            Call<Long> observer = RetrofitService.getInstance().getRetrofitRequest().insertClub(part, category, userId, name, local, maxPeople, intro);

            observer.enqueue(new Callback<Long>() {
                @Override
                public void onResponse(Call<Long> call, Response<Long> response) {
                    if(response.isSuccessful()){
                        if(response.body()>0){
                            Log.d("로그", "onResponse: 1");
                            Intent intent = new Intent(activity, GroupActivity.class);
                            intent.putExtra("clubId", response.body());
                            startActivity(intent);
                            activity.finish();
                        } else {
                            Log.d("로그", "onResponse: 2");
                            //실패
                        }
                    }
                }

                @Override
                public void onFailure(Call<Long> call, Throwable t) {
                    Log.d("로그", "onResponse: 3");

                }
            });
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_group);
        ButterKnife.bind(this);

        loginService = LoginService.getInstance();
        dbManager = new DBManager(this, DBManager.DB_NAME, null, DBManager.CURRENT_VERSION);

        items_location = dbManager.getDoSi();
        items_category = dbManager.getCategory();

        CommonUtils.initSpinner(this, spinnerGroupLocation, items_location, noneSelect);
        CommonUtils.initSpinner(this, spinnerGroupCategory, items_category, noneSelect);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_TAKE_ALBUM:
                if (resultCode == Activity.RESULT_OK) {

                    externalImage = new ExternalImage(data.getData(), CommonUtils.getPath(this, data.getData()));
                    GlideApp.with(this).load(externalImage.getFileUri()).centerCrop().into(imgGroupPhoto);
                    if(cancelBtn.getVisibility()==View.GONE)
                        cancelBtn.setVisibility(View.VISIBLE);

                } else {
                    Toast.makeText(this, "사진 선택을 취소하였습니다.", Toast.LENGTH_SHORT).show();
                }
                break;
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
