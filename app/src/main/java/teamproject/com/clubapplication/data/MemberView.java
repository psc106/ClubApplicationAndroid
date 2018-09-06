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
public class MemberView {

	private Long memberId;
	private String loginId;
	private String name;
	private String birthday;
	private Integer gender;
	private String local;
	private String phone;
	private String nickname;
	private String imgUrl;
}
