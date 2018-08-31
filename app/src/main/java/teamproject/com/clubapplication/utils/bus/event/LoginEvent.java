package teamproject.com.clubapplication.utils.bus.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginEvent {
    //0 로그아웃
    //1 로그인
    private int state;
}
