package teamproject.com.clubapplication.data;

import android.net.Uri;

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
public class ExternalImage {
    private Uri fileUri ;
    private String realPath;
}
