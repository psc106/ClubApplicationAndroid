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
public class Club {

    private long id;
    private long category_id;
    private long member_id;
    private long image_id;
    private String name;
    private String local;
    private int max_people;
    private String intro;
    private  String create_date;
}
