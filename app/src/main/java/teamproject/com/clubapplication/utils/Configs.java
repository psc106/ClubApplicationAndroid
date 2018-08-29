package teamproject.com.clubapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Configs {
    private Context context;
    private SharedPreferences alarmSetting;
    private static Configs appLocalSetting;

    private Configs(){}
    private Configs(Context context){
        setContext(context);
    }
    private static String[] keyArray = {"master", "club/agree", "club/new", "club/schedule", "comment"};
    public static final int MASTER_ALARM = 0;
    public static final int CLUB_AGREE_ALARM = 1;
    public static final int CLUB_NEW_ALARM = 2;
    public static final int CLUB_SCHEDULE_ALARM = 3;
    public static final int COMMENT_ALARM = 4;


    public static Configs getInstance(Context context) {
        if(appLocalSetting==null){
            appLocalSetting = new Configs(context);
        }
        return appLocalSetting;
    }
    public void setContext(Context context) {
        this.context = context;
        alarmSetting = context.getSharedPreferences("alarm_setting", Context.MODE_PRIVATE);
    }

    public boolean getAlarmSetting(int key) {
        return alarmSetting.getBoolean(keyArray[key], true);
    }

    public void setAlarmSetting(int key, Boolean value) {
        SharedPreferences.Editor editor = alarmSetting.edit();
            editor.putBoolean(keyArray[key], value);
            editor.commit();
        alarmSetting = context.getSharedPreferences("alarm_setting", Context.MODE_PRIVATE);
    }

    public void resetAlarmSetting() {
        SharedPreferences.Editor editor = alarmSetting.edit();
        editor.clear();
        editor.commit();
        alarmSetting = context.getSharedPreferences("alarm_setting", Context.MODE_PRIVATE);
    }

}
