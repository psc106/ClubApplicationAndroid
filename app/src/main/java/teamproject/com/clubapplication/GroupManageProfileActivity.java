package teamproject.com.clubapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import teamproject.com.clubapplication.data.MemberView;
import teamproject.com.clubapplication.utils.CommonUtils;
import teamproject.com.clubapplication.utils.LoginService;
import teamproject.com.clubapplication.utils.glide.GlideApp;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;

public class GroupManageProfileActivity extends AppCompatActivity {

    @BindView(R.id.manageProfile_img)
    ImageView manageProfileImg;
    @BindView(R.id.manageProfile_edt)
    EditText manageProfileEdt;
    @BindView(R.id.manageProfile_btn_ok)
    Button manageProfileBtnOk;
    @BindView(R.id.manageProfile_btn_cancel)
    Button manageProfileBtnCancel;

    MemberView member;
    Long clubId;
    Long memeberId;

    @OnClick(R.id.manageProfile_img)
    void photoSelect() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(Intent.createChooser(intent,"다중 선택은 '포토'를 선택하세요."), 1);
    }

    @OnClick(R.id.manageProfile_btn_cancel)
    void photoCancel() {
        if(manageProfileBtnCancel.getVisibility()== View.VISIBLE) {
            manageProfileBtnCancel.setVisibility(View.GONE);
            GlideApp.with(this).load(new ColorDrawable(Color.parseColor("#55000000"))).into(manageProfileImg);
            externalImage = null;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_manage_profile);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        clubId = intent.getLongExtra("clubId", -1);
        memeberId = LoginService.getInstance().getMember().getId();
        getMemberView();

        manageProfileBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultipartBody.Part part = null;
                if(manageProfileBtnCancel.getVisibility()==View.VISIBLE && externalImage!=null)
                    part = prepareFilePart(externalImage.getRealPath(), externalImage.getFileUri());

                String nickname;
                if(TextUtils.isEmpty(manageProfileEdt.getText())){
                    nickname = member.getNickname();
                } else {
                    nickname = manageProfileEdt.getText().toString();
                }

                part = prepareFilePart(externalImage.getRealPath(), externalImage.getFileUri());
                RequestBody clubIdBody = createPartFromString(String.valueOf(clubId));
                RequestBody memeberIdBody = createPartFromString(String.valueOf(memeberId));
                RequestBody nicknameBody = createPartFromString(nickname);

                Call<Void> observer = RetrofitService.getInstance().getRetrofitRequest().updateProfile(clubIdBody, memeberIdBody, nicknameBody, part);
                observer.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(GroupManageProfileActivity.this, "완료", Toast.LENGTH_SHORT).show();
                            manageProfileBtnCancel.setVisibility(View.GONE);
                            getMemberView();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });

    }


    ExternalImage externalImage;
    final int REQUEST_TAKE_ALBUM = 1;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_TAKE_ALBUM:
                if (resultCode == Activity.RESULT_OK) {

                    externalImage = new ExternalImage(data.getData(), CommonUtils.getPath(this, data.getData()));
                    GlideApp.with(this).load(externalImage.getFileUri()).centerCrop().into(manageProfileImg);
                    if(manageProfileBtnCancel.getVisibility()==View.GONE)
                        manageProfileBtnCancel.setVisibility(View.VISIBLE);

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

    void getMemberView() {
        Call<MemberView> observer = RetrofitService.getInstance().getRetrofitRequest().getCurrentMember(clubId, memeberId);
        observer.enqueue(new Callback<MemberView>() {
            @Override
            public void onResponse(Call<MemberView> call, Response<MemberView> response) {
                if(response.isSuccessful()){
                    member = response.body();
                    manageProfileEdt.setText(member.getNickname());
                    GlideApp.with(GroupManageProfileActivity.this).load(CommonUtils.serverURL+CommonUtils.attachPath+member.getImgUrl()).into(manageProfileImg);
                    if(member.getImgUrl()!=null){
                        manageProfileBtnCancel.setVisibility(View.VISIBLE);
                        externalImage = null;
                    }
                }
            }

            @Override
            public void onFailure(Call<MemberView> call, Throwable t) {
            }
        });
    }
}
