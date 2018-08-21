package teamproject.com.clubapplication;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import teamproject.com.clubapplication.utils.AppSetting;
import teamproject.com.clubapplication.utils.DrawerMenu;

public class MyOptionActivity extends AppCompatActivity {
    public static Activity activity;

    @BindView(R.id.myOption_MasterAlarm)
    SwitchCompat masterSwitch;

    @BindView(R.id.myOption_ClubAgree)
    SwitchCompat clubAgreeSwitch;

    @BindView(R.id.myOption_ClubNew)
    SwitchCompat clubNewSwitch;

    @BindView(R.id.myOption_ClubSchedule)
    SwitchCompat clubScheduleSwitch;

    @BindView(R.id.myOption_Comment)
    SwitchCompat commentSwitch;

    AppSetting appSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_option);
        ButterKnife.bind(this);
        appSetting = AppSetting.getInstance(getApplicationContext());

        masterSwitch.setChecked(appSetting.getAlarmSetting(AppSetting.MASTER_ALARM));
        setSubAlarmCheck(masterSwitch.isChecked());
        setSubAlarmClickable(masterSwitch.isChecked());

        masterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                appSetting.setAlarmSetting(AppSetting.MASTER_ALARM ,isChecked);
                setSubAlarmCheck(isChecked);
                setSubAlarmClickable(isChecked);
            }
        });
        clubAgreeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(masterSwitch.isChecked())
                    appSetting.setAlarmSetting(AppSetting.CLUB_AGREE_ALARM ,isChecked);
            }
        });
        clubNewSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(masterSwitch.isChecked())
                    appSetting.setAlarmSetting(AppSetting.CLUB_NEW_ALARM ,isChecked);
            }
        });
        clubScheduleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(masterSwitch.isChecked())
                    appSetting.setAlarmSetting(AppSetting.CLUB_SCHEDULE_ALARM ,isChecked);
            }
        });
        commentSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(masterSwitch.isChecked())
                    appSetting.setAlarmSetting(AppSetting.COMMENT_ALARM ,isChecked);
            }
        });
    }

    DrawerMenu drawerMenu;
    @Override
    protected void onResume() {
        super.onResume();

        if (drawerMenu == null) {
            drawerMenu = DrawerMenu.addMenu(this, R.id.myOption_menu, R.id.myOption_drawer);
        } else {
            drawerMenu.restartMenu(this, R.id.myOption_menu, R.id.myOption_drawer);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        activity = null;
    }

    void setSubAlarmCheck(boolean check) {
        if (!check) {
            clubAgreeSwitch.setChecked(check);
            clubNewSwitch.setChecked(check);
            clubScheduleSwitch.setChecked(check);
            commentSwitch.setChecked(check);
        } else {
            clubAgreeSwitch.setChecked(appSetting.getAlarmSetting(AppSetting.CLUB_AGREE_ALARM));
            clubNewSwitch.setChecked(appSetting.getAlarmSetting(AppSetting.CLUB_NEW_ALARM));
            clubScheduleSwitch.setChecked(appSetting.getAlarmSetting(AppSetting.CLUB_SCHEDULE_ALARM));
            commentSwitch.setChecked(appSetting.getAlarmSetting(AppSetting.COMMENT_ALARM));
        }
    }
    void setSubAlarmClickable(boolean check) {

        clubAgreeSwitch.setClickable(check);
        clubNewSwitch.setClickable(check);
        clubScheduleSwitch.setClickable(check);
        commentSwitch.setClickable(check);
    }
}
