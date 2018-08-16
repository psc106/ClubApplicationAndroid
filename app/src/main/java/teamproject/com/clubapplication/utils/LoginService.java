package teamproject.com.clubapplication.utils;

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

    public void login(Member member) {
        this.member = member;
        BusProvider.getInstance().getBus().post(new LoginEvent(1));
    }

    public void logout() {
        this.member = null;
        BusProvider.getInstance().getBus().post(new LoginEvent(0));
    }

    public Member getMember() {
        return member;
    }
}