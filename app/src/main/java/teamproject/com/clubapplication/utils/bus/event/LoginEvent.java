package teamproject.com.clubapplication.utils.bus.event;

public class LoginEvent {
    //0 로그아웃
    //1 로그인
    private int state;
    public LoginEvent(){/*empty*/}
    public LoginEvent(int state) {
        this.state = state;
    }
    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }
}
