package teamproject.com.clubapplication.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    private long id;
    private  long club_id;
    private  long member_id;
    private  String place;
    private  int cost;
    private  String intro;
    private  String start_date;
    private  String create_date;
}
