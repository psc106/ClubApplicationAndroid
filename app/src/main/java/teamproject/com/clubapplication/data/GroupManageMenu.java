package teamproject.com.clubapplication.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class GroupManageMenu {
    int id;
    String title;
    String detail;

    //대기멤버
    public static final GroupManageMenu profile = new GroupManageMenu(0, "동호회 프로필", "닉네임, 프로필 이미지 수정");

    //일반멤버
    public static final GroupManageMenu out = new GroupManageMenu(1, "동호회 탈퇴", "");

    //관리자
    public static final GroupManageMenu member = new GroupManageMenu(2, "동호회 멤버", "가입 승인, 멤버 강퇴, 멤버 정보 보기");
    public static final GroupManageMenu home = new GroupManageMenu(3, "동호회 대문", "동호회 소개, 동호회 대문 이미지 수정");
    public static final GroupManageMenu delete = new GroupManageMenu(4, "동호회 삭제", "");
}
