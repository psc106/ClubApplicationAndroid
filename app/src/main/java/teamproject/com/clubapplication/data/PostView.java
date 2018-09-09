package teamproject.com.clubapplication.data;

import android.os.Parcel;
import android.os.Parcelable;

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
public class PostView implements Parcelable {
    private Long id;
    private Long member_id;
    private String nickname;
    private String content;
    private String create_date;

    protected PostView(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        if (in.readByte() == 0) {
            member_id = null;
        } else {
            member_id = in.readLong();
        }
        nickname = in.readString();
        content = in.readString();
        create_date = in.readString();
    }

    public static final Creator<PostView> CREATOR = new Creator<PostView>() {
        @Override
        public PostView createFromParcel(Parcel in) {
            return new PostView(in);
        }

        @Override
        public PostView[] newArray(int size) {
            return new PostView[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        if (member_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(member_id);
        }
        dest.writeString(nickname);
        dest.writeString(content);
        dest.writeString(create_date);
    }
}
