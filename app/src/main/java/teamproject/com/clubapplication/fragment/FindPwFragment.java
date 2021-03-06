package teamproject.com.clubapplication.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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

public class FindPwFragment extends Fragment {
    private static FindPwFragment curr = null;
    public static FindPwFragment getInstance() {
        if (curr == null) {
            curr = new FindPwFragment();
        }

        return curr;
    }


    @BindView(R.id.findpw_btn_Find)
    Button findBtn;
    @BindView(R.id.findpw_edt_Email)
    EditText emailEdt;
//    @BindView(R.id.findpw_edt_Id)
//    EditText idEdt;
    @BindView(R.id.findpw_txt_Alarm)
    TextView alarmTxt;

    @OnClick(R.id.findpw_btn_Find)
    void findId() {
        final String email, id;
        if(emailEdt.getText()!=null && !emailEdt.getText().toString().equals("")){
            email = emailEdt.getText().toString();
            id =  emailEdt.getText().toString();
        } else {
            return;
        }
//        if(idEdt.getText()!=null && !idEdt.getText().toString().equals("")){
//            id = idEdt.getText().toString();
//        } else {
//            return;
//        }
        Call<Integer> obs = RetrofitService.getInstance().getRetrofitRequest().checkId(id);
        obs.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){

                    if(response.body()!=0) {
                        final LoadingDialog loadingDialog = LoadingDialog.getInstance();
                        loadingDialog.progressON(getActivity(), "메일 발송중");

                        Call<Void> observer = RetrofitService.getInstance().getRetrofitRequest().findPw(id);
                        observer.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    alarmTxt.setText("임시 비밀번호 메일 발송 완료");
                                    loadingDialog.progressOFF();
                                } else {
                                    alarmTxt.setText("임시 비밀번호 메일 발송 실패.\n 서버 오류");
                                    loadingDialog.progressOFF();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                alarmTxt.setText("임시 비밀번호 메일 발송 실패.\n 서버 오류");
                                loadingDialog.progressOFF();
                                t.printStackTrace();
                            }
                        });
                    } else {
                        alarmTxt.setText("없는 아이디입니다.");
                    }
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });

    }

    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_pw, container, false);
        unbinder = ButterKnife.bind(this, view);
        emailEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                alarmTxt.setText("");
            }
        });

        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}