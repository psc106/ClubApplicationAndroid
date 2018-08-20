package teamproject.com.clubapplication.utils;

import android.app.Activity;

import teamproject.com.clubapplication.FindIdPwActivity;
import teamproject.com.clubapplication.JoinActivity;
import teamproject.com.clubapplication.LoginActivity;
import teamproject.com.clubapplication.MainActivity;
import teamproject.com.clubapplication.MyAlarmActivity;
import teamproject.com.clubapplication.MyCalendarActivity;
import teamproject.com.clubapplication.MyContentActivity;
import teamproject.com.clubapplication.MyGroupActivity;
import teamproject.com.clubapplication.MyInfoActivity;
import teamproject.com.clubapplication.MyOptionActivity;
import teamproject.com.clubapplication.data.Member;
import teamproject.com.clubapplication.utils.bus.BusProvider;
import teamproject.com.clubapplication.utils.bus.event.LoginEvent;

public class LoginService {
    private static LoginService loginProvider;
    private Member member;
    private LoginService(){/*empty*/}

    public static LoginService getInstance() {
        if(loginProvider==null) {
            loginProvider = new LoginService();
        }
        return loginProvider;
    }

    public void setMember(Member member) {
        this.member = member;
        if(member!=null) {
            BusProvider.getInstance().getBus().post(new LoginEvent(1));
        } else {
            BusProvider.getInstance().getBus().post(new LoginEvent(0));
        }
    }

    public void login(Activity currentActivity, Member member) {
        this.member = member;

        if(JoinActivity.activity!=null) {
            JoinActivity.activity.finish();
        }else if(LoginActivity.activity!=null) {
            LoginActivity.activity.finish();
        }else if(FindIdPwActivity.activity!=null) {
            FindIdPwActivity.activity.finish();
        }

        BusProvider.getInstance().getBus().post(new LoginEvent(1));

        if(CommonUtils.isLogoutNeedActivity(currentActivity)) {
            ((MainActivity) MainActivity.activity).backHomeActivity(currentActivity);
        }
    }

    public void logout(Activity currentActivity) {
        this.member = null;

        if(MyAlarmActivity.activity!=null) {
            MyAlarmActivity.activity.finish();
        }else if(MyCalendarActivity.activity!=null) {
            MyCalendarActivity.activity.finish();
        }else if(MyContentActivity.activity!=null) {
            MyContentActivity.activity.finish();
        }else if(MyGroupActivity.activity!=null) {
            MyGroupActivity.activity.finish();
        }else if(MyInfoActivity.activity!=null) {
            MyInfoActivity.activity.finish();
        }else if(MyOptionActivity.activity!=null) {
            MyOptionActivity.activity.finish();
        }

        BusProvider.getInstance().getBus().post(new LoginEvent(0));

        if(CommonUtils.isLoginNeedActivity(currentActivity)) {
            ((MainActivity) MainActivity.activity).backHomeActivity(currentActivity);
        }

    }

    public Member getMember() {
        return member;
    }

    public boolean isVerifyMember() {
        return member.getVerify().equals("Y");
    }
}