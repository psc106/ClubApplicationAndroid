package teamproject.com.clubapplication.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private long id;
    private String login_id;
    private String login_pw;
    private String name;
    private String birthday;
    private int gender;
    private String local;
    private String email;
    private String phone;

    public Member(Member member) {
        this.id = member.id;
        this.login_id = member.login_id;
        this.login_pw = member.login_pw;
        this.name = member.name;
        this.birthday = member.birthday;
        this.gender = member.gender;
        this.local = member.local;
        this.email = member.email;
        this.phone = member.phone;
    }

    public static Member testData() {
        return new Member(0, "abc1234", "1234", "박성철", "20000923", 1, "경기도 의정부시", "psc106@naver.com",  "01055804310");
    }
}