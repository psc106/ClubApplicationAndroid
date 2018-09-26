package teamproject.com.clubapplication.data;


import java.util.ArrayList;

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
public class PostFrame {
    private int commentPage;
    private PostView postView;
    private ArrayList<CommentView> commentView;
}
