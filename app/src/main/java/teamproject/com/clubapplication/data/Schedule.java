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
    long id;
    long club_id;
    long member_id;
    String place;
    int cost;
    String intro;
    String start_date;
    String create_date;
}
