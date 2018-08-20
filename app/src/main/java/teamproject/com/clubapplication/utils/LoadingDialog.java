package teamproject.com.clubapplication.utils;

import android.app.Activity;
import android.app.Application;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import teamproject.com.clubapplication.R;



/*
*  사용법:
*
*  1)   로딩이 필요한 activity, fragment의 메소드에 아래의 코드를 추가합니다.
*       제일 위에 추가하거나, 계산이 시작되는 부분에 추가합니다. (오래 동작하고, 동시 동작을 막고 싶은 부분에 추가. 주로 서버와 통신하는 부분)
*
*       ....
        final LoadingDialog loadingDialog = LoadingDialog.getInstance();
        loadingDialog.progressON(getActivity(), "메세지");
*       ....
*
*  2)   계산이 종료되는 부분에 다음 코드를 추가합니다.
*       ....
        loadingDialog.progressOFF();
*       ....
*
*  3)   로딩중인 화면에서 메세지를 변경하고 싶을 경우 아래의 코드를 사용합니다.
*       ....
        progressSET("원하는 메세지");
*       ....
*
*
*   ex) 위의 코드에서 loadingDialog는 LoadingDialog.getInstance()로 바꿔서 사용이 가능합니다.
*
* */

public class LoadingDialog {
    private static LoadingDialog loadingDialog;
    AppCompatDialog progressDialog;

    public static LoadingDialog getInstance() {
        if(loadingDialog==null) {
            loadingDialog = new LoadingDialog();
        }
        return loadingDialog;
    }
    private LoadingDialog() {
    }

    public void progressON(Activity activity, String message)
    {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        if (progressDialog != null && progressDialog.isShowing()) {
            progressSET(message);
        } else {
            progressDialog = new AppCompatDialog(activity);
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            progressDialog.setContentView(R.layout.dialog_loading); progressDialog.show();
        }
        final ImageView img_loading_frame = (ImageView) progressDialog.findViewById(R.id.iv_frame_loading);
        final AnimationDrawable frameAnimation = (AnimationDrawable) img_loading_frame.getBackground();
//        img_loading_frame.post(new Runnable() {
//            @Override public void run() {
//                frameAnimation.start();
//            }
//        });
        TextView tv_progress_message = (TextView) progressDialog.findViewById(R.id.tv_progress_message);
        if (!TextUtils.isEmpty(message)) {
            tv_progress_message.setText(message);
        }
    }

    public void progressSET(String message) {
        if (progressDialog == null || !progressDialog.isShowing()) {
            return;
        }
        TextView tv_progress_message = (TextView) progressDialog.findViewById(R.id.tv_progress_message);
        if (!TextUtils.isEmpty(message)) {
            tv_progress_message.setText(message);
        }
    }

    public void progressOFF() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
