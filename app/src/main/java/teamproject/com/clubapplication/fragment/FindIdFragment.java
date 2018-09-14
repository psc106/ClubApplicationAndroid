package teamproject.com.clubapplication.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.utils.LoadingDialog;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;

public class FindIdFragment extends Fragment {
    private static FindIdFragment curr = null;
    public static FindIdFragment getInstance() {
        if (curr == null) {
            curr = new FindIdFragment();
        }

        return curr;
    }

    @BindView(R.id.findid_btn_Find)
    Button findBtn;
    @BindView(R.id.findid_edt_Email)
    EditText emailEdt;
    @BindView(R.id.findid_txt_FindId)
    TextView idTxt;

    @OnClick(R.id.findid_btn_Find)
    public void findId() {
        String email;
        if(emailEdt.getText()!=null && !emailEdt.getText().toString().equals("")){
            email = emailEdt.getText().toString();
        } else {
            return;
        }

        final LoadingDialog loadingDialog = LoadingDialog.getInstance();
        loadingDialog.progressON(getActivity(), "로딩중");

        Call<String> observer = RetrofitService.getInstance().getRetrofitRequest().findId(email);
        observer.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    idTxt.setText(response.body());
                    loadingDialog.progressOFF();
                }else  {
                    loadingDialog.progressOFF();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                loadingDialog.progressOFF();
            }
        });
    }

    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_id, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
