package teamproject.com.clubapplication;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teamproject.com.clubapplication.db.DBManager;
import teamproject.com.clubapplication.utils.CommonUtils;
import teamproject.com.clubapplication.utils.DrawerMenu;
import teamproject.com.clubapplication.utils.LoadingDialog;
import teamproject.com.clubapplication.utils.customView.KeyHideActivity;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;

public class JoinActivity extends KeyHideActivity {
    public static Activity activity;
    Context context = this;

    @BindView(R.id.join_Edt_id)
    TextInputEditText edtId;
    @BindView(R.id.join_btn_IdCheck)
    Button btnIdCheck;
    @BindView(R.id.join_edt_Pw)
    TextInputEditText edtPw;
    @BindView(R.id.join_edt_PwCheck)
    TextInputEditText edtPwCheck;
    @BindView(R.id.join_edt_Name)
    TextInputEditText edtName;
    @BindView(R.id.join_edt_Birth)
    TextInputEditText edtBirth;
    @BindView(R.id.join_edt_Phone)
    TextInputEditText edtPhone;
    @BindView(R.id.join_spinner_Gender)
    Spinner spinnerGender;
    @BindView(R.id.join_spinner_Local)
    Spinner spinnerLocal;
    @BindView(R.id.join_btn_Ok)
    Button btnOk;

    @BindView(R.id.join_TILayout_id)
    TextInputLayout tILayoutId;
    @BindView(R.id.join_TILayout_Pw)
    TextInputLayout tILayoutPw;
    @BindView(R.id.join_TILayout_PwCheck)
    TextInputLayout tILayoutPwCheck;
    @BindView(R.id.join_TILayout_Name)
    TextInputLayout tILayoutName;
    @BindView(R.id.join_TILayout_Birth)
    TextInputLayout tILayoutBirth;
    @BindView(R.id.join_TILayout_Phone)
    TextInputLayout tILayoutPhone;

    @OnClick(R.id.join_btn_IdCheck)
    public void btnJoinCheckId() {
        Pattern pattern = Pattern.compile(regexId);

        if (!TextUtils.isEmpty(edtId.getText())) {
            matcher = pattern.matcher(edtId.getText().toString());
            if (matcher.matches()) {
                Call<Integer> observer = RetrofitService.getInstance().getRetrofitRequest().checkId(edtId.getText().toString());
                observer.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (response.isSuccessful()) {
                            if (response.body() == 0) {
                                isIdCheck = true;
                                isIdSuccess = true;
                                saveId = edtId.getText().toString();
                                tILayoutId.setError(null);
                                btnIdCheck.setText("확인 완료");
                                return;
                            } else {
                                isIdCheck = true;
                                isIdSuccess = false;
                                tILayoutId.setError("이미 존재하는 아이디입니다.");
                                return;
                            }
                        }
                        Toast.makeText(activity, "성공", Toast.LENGTH_SHORT).show();
                        isIdCheck = false;
                        tILayoutId.setError("네트워크가 불안정합니다. \n다시 시도해주세요.");
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        isIdCheck = false;
                        tILayoutId.setError("네트워크가 불안정합니다. \n다시 시도해주세요.");
                    }
                });
            }

        }
    }

    @OnClick(R.id.join_btn_Ok)
    public void btnJoinOk() {
        if (TextUtils.isEmpty(edtId.getText()) || TextUtils.isEmpty(edtBirth.getText()) || TextUtils.isEmpty(edtName.getText()) || TextUtils.isEmpty(edtPw.getText()) ||
                spinnerGender.getSelectedItemPosition() == 0 || spinnerLocal.getSelectedItemPosition() == 0 || !isIdCheck || !isIdSuccess || !isPwSuccess) {
            Log.d("로그", "1: "+TextUtils.isEmpty(edtId.getText()));
            Log.d("로그", "2: "+TextUtils.isEmpty(edtBirth.getText()));
            Log.d("로그", "3: "+TextUtils.isEmpty(edtName.getText()));
            Log.d("로그", "4: "+TextUtils.isEmpty(edtPw.getText()));
            Log.d("로그", "5: "+spinnerGender.getSelectedItemPosition());
            Log.d("로그", "6: "+spinnerLocal.getSelectedItemPosition());
            Log.d("로그", "7: "+isIdCheck );
            Log.d("로그", "8: "+isPwSuccess);
            Log.d("로그", "9: "+isIdSuccess);
            return;
        }

        if (tILayoutBirth.getError() != null || tILayoutId.getError() != null || tILayoutName.getError() != null || tILayoutPhone.getError() != null || tILayoutPw.getError() != null || tILayoutPwCheck.getError() != null) {
            Log.d("로그", "1: "+tILayoutBirth.getError());
            Log.d("로그", "2: "+tILayoutId.getError());
            Log.d("로그", "3: "+tILayoutName.getError());
            Log.d("로그", "4: "+tILayoutPhone.getError());
            Log.d("로그", "5: "+tILayoutPw.getError());
            Log.d("로그", "6: "+tILayoutPwCheck.getError());

            return;
        }
        String loginId = edtId.getText().toString();
        String loginPw = edtPw.getText().toString();
        String name = edtName.getText().toString();
        String birthday = new SimpleDateFormat("yyyyMMdd").format(birthCalendar.getTime());
        int gender = spinnerGender.getSelectedItemPosition();
        String local = (String) spinnerLocal.getSelectedItem();
        String phone = "";
        if (!TextUtils.isEmpty(edtPhone.getText())) {
            phone = edtPhone.getText().toString();
        }
        final LoadingDialog loadingDialog = LoadingDialog.getInstance();
        loadingDialog.progressON(this, "메일 발송중");

        Call<Void> observer = RetrofitService.getInstance().getRetrofitRequest().insertMember(loginId, loginPw, name, birthday, gender, local, phone);
        observer.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "가입?", Toast.LENGTH_SHORT).show();
                    loadingDialog.progressOFF();
                    finish();
                } else {
                    Log.d("로그", "onResponse: fail");
                    loadingDialog.progressOFF();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
                loadingDialog.progressOFF();
            }
        });
    }

    DBManager dbManager;
    DrawerMenu drawerMenu;

    boolean isIdCheck = false;
    boolean isIdSuccess = false;
    boolean isPwSuccess = false;
    String saveId = "";

    Matcher matcher;
    String regexId = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    String regexPw = "^.{8,20}$";
    String regexTel = "^\\d{2,3}-?\\d{3,4}-?\\d{4}$";
    Calendar birthCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        ButterKnife.bind(this);

        birthCalendar = Calendar.getInstance();
        dbManager = new DBManager(this, DBManager.DB_NAME, null, DBManager.CURRENT_VERSION);

        CommonUtils.initSpinner(this, spinnerGender, new String[]{"남자", "여자"}, new String[]{"성별*"});
        CommonUtils.initSpinner(this, spinnerLocal, dbManager.getDoSi(), new String[]{"지역*"});

        edtBirth.setTag(edtBirth.getKeyListener());
        edtBirth.setKeyListener(null);
        edtBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        birthCalendar.set(year, monthOfYear, dayOfMonth, birthCalendar.get(Calendar.HOUR_OF_DAY), birthCalendar.get(Calendar.MINUTE));
                        edtBirth.setText(new SimpleDateFormat("yyyy년 MM월 dd일").format(birthCalendar.getTime()));
                    }
                }, birthCalendar.get(Calendar.YEAR), birthCalendar.get(Calendar.MONTH), birthCalendar.get(Calendar.DATE)).show();
            }
        });

        edtId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            Pattern pattern = Pattern.compile(regexId);

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText editText = (EditText) v;
                if (!v.hasFocus()) {
                    String str = "";
                    if (!TextUtils.isEmpty(editText.getText()))
                        str = editText.getText().toString();
                    if (!saveId.equals(str)) {
                        isIdCheck = false;
                        isIdSuccess = false;
                        btnIdCheck.setText("중복확인");
                        saveId = "";
                    }
                    matcher = pattern.matcher(str);
                    if (str.length() == 0) {
                        tILayoutId.setError("필수항목입니다.");
                    } else if (!matcher.matches()) {
                        tILayoutId.setError("Email을 입력 바랍니다.");
                    } else if (!isIdCheck && !btnIdCheck.isFocused()) {
                        tILayoutId.setError("중복 여부를 체크바랍니다.");
                    } else {
                        tILayoutId.setError(null);
                    }
                } else {
                    tILayoutId.setError(null);
                }
            }
        });

        edtPw.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            Pattern pattern = Pattern.compile(regexPw);

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText editText = (EditText) v;
                if (!v.hasFocus()) {
                    String str = "";
                    if (!TextUtils.isEmpty(editText.getText()))
                        str = editText.getText().toString();
                    matcher = pattern.matcher(str);

                    if (!matcher.matches()) {
                        tILayoutPw.setError("8~20글자 입력");
                    } else if ((edtPwCheck.getText() == null || edtPwCheck.getText().toString().equals("")) && !edtPwCheck.isFocused()) {
                        tILayoutPwCheck.setError("비밀번호 확인 입력 바랍니다.");
                    } else if (!TextUtils.equals(edtPwCheck.getText(), edtPw.getText())) {
                        tILayoutPwCheck.setError("입력한 비밀번호가 다릅니다.");
                        isPwSuccess = false;

                    } else {
                        tILayoutPw.setError(null);
                        if (TextUtils.equals(edtPwCheck.getText(), edtPw.getText())) {
                            isPwSuccess = true;
                            tILayoutPwCheck.setError(null);
                        }
                    }
                } else {
                    tILayoutPw.setError(null);
                }
            }
        });

        edtPwCheck.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            Pattern pattern = Pattern.compile(regexPw);

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText editText = (EditText) v;
                if (!v.hasFocus()) {
                    String str = "";
                    if (!TextUtils.isEmpty(editText.getText()))
                        str = editText.getText().toString();
                    matcher = pattern.matcher(str);

                    if (!matcher.matches()) {
                        tILayoutPwCheck.setError("8~20글자 입력 바랍니다.");
                    } else if ((edtPw.getText() == null && edtPw.getText().toString().equals("")) && !edtPw.isFocused()) {
                        tILayoutPw.setError("비밀번호 입력 바랍니다.");
                    } else if (!TextUtils.equals(edtPwCheck.getText(), edtPw.getText())) {
                        tILayoutPwCheck.setError("입력한 비밀번호가 다릅니다.");
                        isPwSuccess = false;

                    } else {
                        tILayoutPw.setError(null);
                        if (TextUtils.equals(edtPwCheck.getText(), edtPw.getText())) {
                            isPwSuccess = true;
                            tILayoutPwCheck.setError(null);
                        }
                    }
                } else {
                    tILayoutPwCheck.setError(null);
                }
            }
        });

        edtBirth.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText editText = (EditText) v;
                if (!v.hasFocus()) {
                    String str = "";
                    if (!TextUtils.isEmpty(editText.getText()))
                        str = editText.getText().toString();

                    if (str.length() == 0) {
                        tILayoutBirth.setError("필수 입력 사항입니다");
                    } else {
                        tILayoutBirth.setError(null);
                    }
                } else {
                    tILayoutBirth.setError(null);
                }
            }
        });

        edtName.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText editText = (EditText) v;
                if (!v.hasFocus()) {
                    String str = "";
                    if (!TextUtils.isEmpty(editText.getText()))
                        str = editText.getText().toString();

                    if (str.length() == 0) {
                        tILayoutName.setError("필수 입력 사항입니다");
                    } else {
                        tILayoutName.setError(null);
                    }
                } else {
                    tILayoutName.setError(null);
                }
            }
        });

        edtPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            Pattern pattern = Pattern.compile(regexTel);

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText editText = (EditText) v;
                if (!v.hasFocus()) {
                    String str = "";
                    if (!TextUtils.isEmpty(editText.getText()))
                        str = editText.getText().toString();

                    matcher = pattern.matcher(str);
                    if (str.length() > 0 && !matcher.matches()) {
                        tILayoutPhone.setError("전화번호 입력바랍니다.");
                    } else {
                        tILayoutPhone.setError(null);
                    }
                } else {
                    tILayoutPhone.setError(null);
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (drawerMenu == null) {
            drawerMenu = DrawerMenu.addMenu(this, R.id.join_menu, R.id.join_drawer);
        } else {
            drawerMenu.restartMenu(this, R.id.join_menu, R.id.join_drawer);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activity = null;
    }
}
