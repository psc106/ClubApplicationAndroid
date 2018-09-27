package teamproject.com.clubapplication;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

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
import teamproject.com.clubapplication.adapter.ModifyPostRecyclerviewAdapter;
import teamproject.com.clubapplication.adapter.WritePostRecyclerviewAdapter;
import teamproject.com.clubapplication.data.ExternalImage;
import teamproject.com.clubapplication.data.PostView;
import teamproject.com.clubapplication.utils.CommonUtils;
import teamproject.com.clubapplication.utils.LoginService;
import teamproject.com.clubapplication.utils.bus.BusProvider;
import teamproject.com.clubapplication.utils.customView.KeyHideActivity;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;

public class GroupPostModifyActivity extends KeyHideActivity {

    @BindView(R.id.writeBoard_edt_Main)
    EditText writeBoardEdtMain;
    @BindView(R.id.writeBoard_recycleV)
    RecyclerView writeBoardRecycleV;
    @BindView(R.id.writeBoard_btn_Add)
    Button writeBoardBtnAdd;
    @BindView(R.id.writeBoard_btn_Ok)
    Button writeBoardBtnOk;

    PostView currPost;
    ArrayList<ExternalImage> oldImgs;
    ArrayList<ExternalImage> newImgs;
    ArrayList<ExternalImage> allImgs;
    ModifyPostRecyclerviewAdapter imgAdapter;

    public void removeNew(int position){
        newImgs.remove(position-oldImgs.size());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_write_board);
        ButterKnife.bind(this);

        oldImgs = new ArrayList<>();
        newImgs = new ArrayList<>();
        allImgs = new ArrayList<>();

        Intent intent = getIntent();
        currPost = intent.getParcelableExtra("postData");

        writeBoardEdtMain.setText(currPost.getContent());

        imgAdapter = new ModifyPostRecyclerviewAdapter(this, allImgs);

        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        writeBoardRecycleV.setLayoutManager(horizontalLayoutManagaer);
        writeBoardRecycleV.setAdapter(imgAdapter);

        writeBoardBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(Intent.createChooser(intent, "다중 선택은 '포토'를 선택하세요."), 1);
            }
        });

        Call<ArrayList<String>> observer = RetrofitService.getInstance().getRetrofitRequest().selectPostImg(currPost.getId());
        observer.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                if(response.isSuccessful()){
                    Log.d("로그", response.body().size()+"");
                    for(int i = 0; i < response.body().size(); ++i) {
                        Log.d("로그", i+"/"+response.body().get(i));
                        oldImgs.add(new ExternalImage(null, response.body().get(i)));
                    }
                    allImgs.addAll(oldImgs);
                    imgAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {

            }
        });

        writeBoardBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String content = "";
                if (!TextUtils.isEmpty(writeBoardEdtMain.getText())) {
                    content = writeBoardEdtMain.getText().toString();
                }
//                String tag = "";
//                if (!TextUtils.isEmpty(writeBoardEdtTag.getText())) {
//                    tag = writeBoardEdtTag.getText().toString();
//                }

                ArrayList<MultipartBody.Part> parts = new ArrayList<>();

                for (int i = 0; i < newImgs.size(); ++i) {
                    String name = newImgs.get(i).getRealPath();
                    Uri uri = newImgs.get(i).getFileUri();
                    parts.add(prepareFilePart(name, uri));
                }

                String deleteListStr = "";
                ArrayList<String> deleteList = imgAdapter.getDeleteList();
                for(int i  = 0 ; i <deleteList.size(); ++i) {
                    deleteListStr = deleteListStr+deleteList.get(i);
                    if(i+1!=deleteList.size()){
                        deleteListStr+=",";
                    }
                }

                Long memberId = LoginService.getInstance().getMember().getId();

                RequestBody contentBody = createPartFromString(content);
//                RequestBody tagBody = createPartFromString(tag);
//                RequestBody clubIdBody = createPartFromString(String.valueOf(clubId));
                RequestBody memberIdBody = createPartFromString(String.valueOf(memberId));
                RequestBody postIdBody = createPartFromString(String.valueOf(currPost.getId()));
                RequestBody deleteListStrBody = createPartFromString(deleteListStr);

                Call<ResponseBody> call = RetrofitService.getInstance().getRetrofitRequest().updatePost(postIdBody, memberIdBody, contentBody, deleteListStrBody, parts);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });

    }


    final int REQUEST_TAKE_ALBUM = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_TAKE_ALBUM:
                ArrayList<ExternalImage> resultEvent = new ArrayList<>();
                if (resultCode == Activity.RESULT_OK) {

                    // 멀티 선택을 지원하지 않는 기기에서는 getClipdata()가 없음 > getData()로 접근해야 함
                    if (data.getClipData() == null) {
                        ExternalImage externalImage = new ExternalImage(data.getData(), CommonUtils.getPath(this, data.getData()));
                        newImgs.add(externalImage);
                    } else {
                        ClipData clipData = data.getClipData();
                        if (clipData.getItemCount() > 0) {
                            for (int i = 0; i < clipData.getItemCount(); i++) {
                                if (i > 10) {
                                    break;
                                }

                                Uri dataStr = clipData.getItemAt(i).getUri();
                                ExternalImage externalImage = new ExternalImage(dataStr, CommonUtils.getPath(this, dataStr));
                                newImgs.add(externalImage);
                            }
                        }
                    }
                } else {
                    Toast.makeText(this, "사진 선택을 취소하였습니다.", Toast.LENGTH_SHORT).show();
                }

                allImgs.clear();
                allImgs.addAll(oldImgs);
                allImgs.addAll(newImgs);
                imgAdapter.notifyDataSetChanged();

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
