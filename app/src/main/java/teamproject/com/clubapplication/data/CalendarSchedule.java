package teamproject.com.clubapplication.data;

import java.util.ArrayList;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CalendarSchedule {
    private String day;
    private ArrayList<Schedule> todaySchedule;
}