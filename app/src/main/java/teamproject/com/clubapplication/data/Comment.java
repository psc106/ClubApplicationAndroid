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
public class Comment {
    private  Long id;
    private  Long club_id;
    private  Long member_id;
    private   String content;
    private  String create_date;
}
