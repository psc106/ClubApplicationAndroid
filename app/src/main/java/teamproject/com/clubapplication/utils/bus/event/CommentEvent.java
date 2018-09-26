package teamproject.com.clubapplication.utils.bus.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentEvent {
    //0 생성 1 수정 2 삭제
    int type;
    int position;
    long postId;
    long commentId;
}
