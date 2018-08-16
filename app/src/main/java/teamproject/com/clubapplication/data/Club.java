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

    long id;
    long category_id;
    long member_id;
    long image_id;
    String name;
    String local;
    int max_people;
    String intro;
    String create_date;
}
