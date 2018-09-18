package teamproject.com.clubapplication.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamproject.com.clubapplication.GroupWriteActivity;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.adapter.WritePostRecyclerviewAdapter;
import teamproject.com.clubapplication.data.ExternalImage;
import teamproject.com.clubapplication.utils.CommonUtils;
import teamproject.com.clubapplication.utils.LoginService;
import teamproject.com.clubapplication.utils.bus.BusProvider;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;


public class GroupWriteBoardFragment extends Fragment {
    private static GroupWriteBoardFragment curr = null;

    @BindView(R.id.writeBoard_edt_Main)
    EditText writeBoardEdtMain;
    @BindView(R.id.writeBoard_btn_Add)
    Button writeBoardBtnAdd;
    @BindView(R.id.writeBoard_recycleV)
    RecyclerView writeBoardRecycleV;
    @BindView(R.id.writeBoard_checkBtn)
    CheckBox writeBoardCheckBtn;
    @BindView(R.id.writeBoard_edt_Tag)
    EditText writeBoardEdtTag;
    @BindView(R.id.writeBoard_btn_Ok)
    Button writeBoardBtnOk;

    public static GroupWriteBoardFragment getInstance() {
        if (curr == null) {
            curr = new GroupWriteBoardFragment();
        }

        return curr;
    }

    Unbinder unbinder;
    Bus bus;

    ArrayList<ExternalImage> imageList;
    WritePostRecyclerviewAdapter adapter;
    LoginService loginService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_write_board, container, false);
        unbinder = ButterKnife.bind(this, view);
        bus = BusProvider.getInstance().getBus();
        bus.register(this);
        loginService = LoginService.getInstance();
        imageList = new ArrayList<>();

        adapter = new WritePostRecyclerviewAdapter(getContext(), imageList);

        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        writeBoardRecycleV.setAdapter(adapter);
        writeBoardRecycleV.setLayoutManager(horizontalLayoutManagaer);

        writeBoardBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                getActivity().startActivityForResult(Intent.createChooser(intent,"다중 선택은 '포토'를 선택하세요."), 1);
            }
        });


        writeBoardBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String content = "";
                if(!TextUtils.isEmpty(writeBoardEdtMain.getText())){
                    content = writeBoardEdtMain.getText().toString();
                }
                String tag = "";
                if(!TextUtils.isEmpty(writeBoardEdtTag.getText())){
                    tag = writeBoardEdtTag.getText().toString();
                }

                ArrayList<MultipartBody.Part> parts = new ArrayList<>();

                for (int i = 0; i < imageList.size(); ++i) {
                    String name = imageList.get(i).getRealPath();
                    Uri uri = imageList.get(i).getFileUri();
                    parts.add(prepareFilePart(name, uri));
                }
                Long clubId = ((GroupWriteActivity)getActivity()).getClubId();
                Long memberId = loginService.getMember().getId();

                RequestBody contentBody = createPartFromString(content);
                RequestBody tagBody = createPartFromString(tag);
                RequestBody clubIdBody = createPartFromString(String.valueOf(clubId));
                RequestBody memberIdBody = createPartFromString(String.valueOf(memberId));
                RequestBody checkBody = createPartFromString(String.valueOf(writeBoardCheckBtn.isChecked()));

                Call<ResponseBody> call = RetrofitService.getInstance().getRetrofitRequest().insertPost(contentBody, tagBody, clubIdBody, memberIdBody, parts, checkBody);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            getActivity().finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        bus.unregister(this);
    }

    @Subscribe
    public void onActivityResult(ArrayList<ExternalImage> resultEvent){
        Log.d("로그", "1: ");
        if(resultEvent.size()+imageList.size()<=10) {
            Log.d("로그", "2: ");
            imageList.addAll(resultEvent);
        } else if(resultEvent.size()+imageList.size()>10 && imageList.size()<10) {
            Log.d("로그", "3: ");
            imageList.addAll(resultEvent.subList(0, 10-imageList.size()));
        } else {
            Log.d("로그", "4: ");
            Toast.makeText(getContext(), "이미지 최대 10개", Toast.LENGTH_SHORT).show();
        }

        adapter.notifyDataSetChanged();
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
        File file = new File(CommonUtils.getPath(getContext(), fileUri));

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(getContext().getContentResolver().getType(fileUri)),
                        file
                );

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }
}
