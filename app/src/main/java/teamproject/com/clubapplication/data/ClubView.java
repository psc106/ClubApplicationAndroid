package teamproject.com.clubapplication.data;

import android.os.Parcel;
import android.os.Parcelable;

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
public class ClubView implements Parcelable {

    private long id;
    private long category_id;
    private long member_id;
    private String nickname;
    private String imgUrl;
    private String name;
    private String local;
    private int max_people;
    private int cur_people;
    private String intro;
    private  String create_date;

    protected ClubView(Parcel in) {
        id = in.readLong();
        category_id = in.readLong();
        member_id = in.readLong();
        nickname = in.readString();
        imgUrl = in.readString();
        name = in.readString();
        local = in.readString();
        max_people = in.readInt();
        cur_people = in.readInt();
        intro = in.readString();
        create_date = in.readString();
    }

    public static final Creator<ClubView> CREATOR = new Creator<ClubView>() {
        @Override
        public ClubView createFromParcel(Parcel in) {
            return new ClubView(in);
        }

        @Override
        public ClubView[] newArray(int size) {
            return new ClubView[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(category_id);
        dest.writeLong(member_id);
        dest.writeString(nickname);
        dest.writeString(imgUrl);
        dest.writeString(name);
        dest.writeString(local);
        dest.writeInt(max_people);
        dest.writeInt(cur_people);
        dest.writeString(intro);
        dest.writeString(create_date);
    }
}
