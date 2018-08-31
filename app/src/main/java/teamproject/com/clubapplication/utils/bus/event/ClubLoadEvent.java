package teamproject.com.clubapplication.utils.bus.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import teamproject.com.clubapplication.data.Club;
import teamproject.com.clubapplication.data.ClubMemberClass;
import teamproject.com.clubapplication.data.Member;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClubLoadEvent {
    ClubMemberClass clubMemberClass;
}
