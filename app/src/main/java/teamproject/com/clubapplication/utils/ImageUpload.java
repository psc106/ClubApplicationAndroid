package teamproject.com.clubapplication.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import teamproject.com.clubapplication.utils.retrofit.RetrofitService;

public class ImageUpload {

    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    @NonNull
    public static RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                MediaType.parse(MULTIPART_FORM_DATA), descriptionString);
    }

    @NonNull
    public static MultipartBody.Part prepareFilePart(Context context, String partName, Uri fileUri) {
        File file = new File(getRealPathFromURI(context, fileUri));
        RequestBody requestFile = RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    private static String getRealPathFromURI(Context context, Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(context, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
//    액티비티에서 사용할경우 다음과 같이 작성한다.

//    File file = new File(this, getRealPathFromURI(uri));
//    MultipartBody.Part body1 = prepareFilePart("image", uri);
//    RequestBody description = createPartFromString("hello, this is description speaking");
//    Call<ResponseBody> call = RetrofitService.getInstance().getRetrofitRequest().통신메소드(description, body1);

}
