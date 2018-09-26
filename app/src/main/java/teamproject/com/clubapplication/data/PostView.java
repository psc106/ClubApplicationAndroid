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
    private String imgUrl;
    private Long nextId;
    private Long previousId;


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
        imgUrl = in.readString();
        if (in.readByte() == 0) {
            nextId = null;
        } else {
            nextId = in.readLong();
        }
        if (in.readByte() == 0) {
            previousId = null;
        } else {
            previousId = in.readLong();
        }
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
        dest.writeString(imgUrl);
        if (nextId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(nextId);
        }
        if (previousId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(previousId);
        }
    }

    public Integer canMovePosition(){
        if(nextId!=previousId){
            if(previousId==-1){
                return 1;
            }else if(nextId==-1){
                return -1;
            } else {
                return 0;
            }
        } else {
            if(nextId!=-1){
                return 0;
            }else if(nextId==-1){
                return Integer.MIN_VALUE;
            }
        }
        return null;
    }

}
