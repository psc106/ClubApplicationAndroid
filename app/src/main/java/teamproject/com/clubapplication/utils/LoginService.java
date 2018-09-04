package teamproject.com.clubapplication.utils;

import android.app.Activity;
import android.util.Log;

import teamproject.com.clubapplication.FindIdPwActivity;
import teamproject.com.clubapplication.JoinActivity;
import teamproject.com.clubapplication.LoginActivity;
import teamproject.com.clubapplication.MainActivity;
import teamproject.com.clubapplication.MakeGroupActivity;
import teamproject.com.clubapplication.MyAlarmActivity;
import teamproject.com.clubapplication.MyCalendarActivity;
import teamproject.com.clubapplication.MyContentActivity;
import teamproject.com.clubapplication.MyGroupActivity;
import teamproject.com.clubapplication.MyInfoActivity;
import teamproject.com.clubapplication.MyOptionActivity;
import teamproject.com.clubapplication.data.Member;
import teamproject.com.clubapplication.utils.bus.BusProvider;
import teamproject.com.clubapplication.utils.bus.event.LoginEvent;



/*
*  사용법:
*
*  1) 로그인이 필요한 activity, fragment에 전역변수로 아래의 코드를 추가합니다.
    private LoginService loginService;
*
*  2) 오버라이딩된 onCreate(), onCreateView() 함수에 다음의 코드를 추가합니다. (onCreate:액티비티/ onCreateView:프래그먼트)
    loginService = LoginService.getInstance();
*
*  3) 현재 로그인된 정보가 필요할 경우 저장된 LoginService객체에서 getMember()를 이용합니다.
*  ex) loginService.getMember()
*
*  4) 로그인/로그아웃도 이 객체에서 처리합니다.
*  ex) 로그인
*  loginService.login(this, new Member()) : 액티비티에서 사용할 경우
*  loginService.login(getActivity(), new Member()) : 프래그먼트에서 사용할 경우
*
*  ex)로그아웃
*  loginService.logout(this) : 액티비티에서 사용할 경우
*  loginService.logout(getActivity()) : 프래그먼트에서 사용할 경우
* */

//싱글턴 객체로 getinstance로 호출합니다.
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

    //현재 로그인중인 member를 반환합니다.
    public Member getMember() {
        return member;
    }

    //refreshMember(Member member) : 현재 로그인중인 멤버를 매개변수 member로 바꾼다.
    //정보가 다를 경우 로그아웃이 된다.
    public void refreshMember(Member member) {
        Log.d("로그", "1 "+member.toString());
        if(this.member.getId()==member.getId()) {
            if (member != null) {
                Log.d("로그", "2 "+member.toString());
                BusProvider.getInstance().getBus().post(new LoginEvent(1));
                return;
            }
        }
        Log.d("로그", "3 "+member.toString());
        BusProvider.getInstance().getBus().post(new LoginEvent(0));
    }


    //login(Activity currActivity, Member member) : 로그인되면 로그아웃 상태에서만 보이던 activity를 모두 종료하고,
    // 만약 현재 액티비티가 logout에서만 보이는 activity라면 mainActivity로 이동합니다.
    // 현재 저장된 멤버를(아마도 null) 매개변수 member로 바꿉니다.
    // 이 때 bus가 발송되어서 실행중이던 activity에 어떤 기능들을 활성화시키거나 비활성화시킵니다.
    public void login(Activity currentActivity, Member member) {
        this.member = member;

        if(JoinActivity.activity!=null) {
            JoinActivity.activity.finish();
            JoinActivity.activity=null;
        }else if(LoginActivity.activity!=null) {
            LoginActivity.activity.finish();
            LoginActivity.activity=null;
        }else if(FindIdPwActivity.activity!=null) {
            FindIdPwActivity.activity.finish();
            FindIdPwActivity.activity=null;
        }

        BusProvider.getInstance().getBus().post(new LoginEvent(1));

        if(CommonUtils.isLogoutNeedActivity(currentActivity)) {
            ((MainActivity) MainActivity.activity).backHomeActivity(currentActivity);
        }
    }

    //logout(Activity currActivity) : 로그아웃되면 로그인 상태에서만 보이던 activity를 모두 종료하고,
    // 만약 현재 액티비티가 login에서만 보이는 activity라면 mainActivity로 이동합니다.
    // 현재 로그인된 멤버의 정보를 null로 합니다.
    // 이 때 bus가 발송되어서 실행중이던 activity에 어떤 기능들을 활성화시키거나 비활성화시킵니다.
    public void logout(Activity currentActivity) {
        this.member = null;

        if(MyAlarmActivity.activity!=null) {
            MyAlarmActivity.activity.finish();
            MyAlarmActivity.activity=null;
        }else if(MyCalendarActivity.activity!=null) {
            MyCalendarActivity.activity.finish();
            MyCalendarActivity.activity=null;
        }else if(MyContentActivity.activity!=null) {
            MyContentActivity.activity.finish();
            MyContentActivity.activity=null;
        }else if(MyGroupActivity.activity!=null) {
            MyGroupActivity.activity.finish();
            MyGroupActivity.activity=null;
        }else if(MyInfoActivity.activity!=null) {
            MyInfoActivity.activity.finish();
            MyInfoActivity.activity=null;
        }else if(MyOptionActivity.activity!=null) {
            MyOptionActivity.activity.finish();
            MyOptionActivity.activity=null;
        }else if(MakeGroupActivity.activity!=null) {
            MakeGroupActivity.activity.finish();
            MakeGroupActivity.activity=null;
        }

        BusProvider.getInstance().getBus().post(new LoginEvent(0));

        if(CommonUtils.isLoginNeedActivity(currentActivity)) {
            ((MainActivity) MainActivity.activity).backHomeActivity(currentActivity);
        }

    }


}