package teamproject.com.clubapplication.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClubView {

    private long id;
    private long category_id;
    private String nickname;
    private String imgUrl;
    private String name;
    private String local;
    private int max_people;
    private int cur_people;
    private String intro;
    private  String create_date;
}
