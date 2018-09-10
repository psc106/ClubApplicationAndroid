package teamproject.com.clubapplication.utils;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import okhttp3.ResponseBody;
import teamproject.com.clubapplication.FindIdPwActivity;
import teamproject.com.clubapplication.JoinActivity;
import teamproject.com.clubapplication.LoginActivity;
import teamproject.com.clubapplication.MyAlarmActivity;
import teamproject.com.clubapplication.MyCalendarActivity;
import teamproject.com.clubapplication.MyContentActivity;
import teamproject.com.clubapplication.MyGroupActivity;
import teamproject.com.clubapplication.MyInfoActivity;
import teamproject.com.clubapplication.MyOptionActivity;

public class CommonUtils {
    public static String TAG = "로그";
    public static String serverURL = "http://192.168.0.70:8090/club_application/";
//    public static String serverURL = "http://192.168.25.11:8090/club_application/";

    public static boolean isLoginNeedActivity(Activity activity) {
        if(activity==null)
            return true;
        return (activity.getClass()== MyAlarmActivity.class)||(activity.getClass()== MyOptionActivity.class)||(activity.getClass()== MyInfoActivity.class)
                ||(activity.getClass()== MyGroupActivity.class)||(activity.getClass()== MyContentActivity.class)||(activity.getClass()== MyCalendarActivity.class);
    }

    public static boolean isLogoutNeedActivity(Activity activity) {
        if(activity==null)
            return true;
        return (activity.getClass()== JoinActivity.class)||(activity.getClass()== FindIdPwActivity.class);
    }
    public static void setListviewHeightBasedOnChildren(ListView listView) {
        if(listView==null)
            return;

        ListAdapter adapter = listView.getAdapter();
        if (adapter == null) {
            // pre-condition
            return ;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static int getListviewHeight(ListView listView) {
        if(listView==null)
            return 0;

        ListAdapter adapter = listView.getAdapter();
        if (adapter == null) {
            // pre-condition
            return 0;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        return  totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
    }

    public static int getListviewWidth(ListView listView) {
        if(listView==null)
            return 0;

        ListAdapter adapter = listView.getAdapter();
        if (adapter == null) {
            // pre-condition
            return 0;
        }

        int totalWidth = 0;
        int desiredHeight = View.MeasureSpec.makeMeasureSpec(listView.getHeight(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(View.MeasureSpec.UNSPECIFIED, desiredHeight);
            totalWidth += listItem.getMeasuredWidth();
        }

        return  totalWidth + (listView.getDividerHeight() * (adapter.getCount() - 1));
    }

    public static String getStringFromServer(String pUrl) {
        if(pUrl==null)
            return null;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String json = "";

        try {
            URL url = new URL(pUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {  //통신에러
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while((line = reader.readLine()) != null) {
                buffer.append(line+"\n");
            }

            if (buffer.length() == 0) {     //에러처리
                return null;
            }

            json = buffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return json;
    }

    public static Bitmap getImageBitmap(String pUrl) {
        if(pUrl==null)
            return null;
        HttpURLConnection urlConnection = null;

        URL url = null;
        Bitmap bitmap = null;
        try {
            url = new URL(pUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return bitmap;
    }
    public static <T> T[] concatAll(T[] first, T[]... rest) {
        int totalLength = first.length;
        for (T[] array : rest) {
            totalLength += array.length;
        }
        T[] result;
        try {
            Method arraysCopyOf = Arrays.class.getMethod("copyOf", Object[].class, int.class);
            result = (T[]) arraysCopyOf.invoke(null, first, totalLength);
        } catch (Exception e){
            //Java 6 / Android >= 9 way didn't work, so use the "traditional" approach
            result = (T[]) java.lang.reflect.Array.newInstance(first.getClass().getComponentType(), totalLength);
            System.arraycopy(first, 0, result, 0, first.length);
        }
        int offset = first.length;
        for (T[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    private boolean writeResponseBodyToDisk(Context context, ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(context.getExternalFilesDir(null) + File.separator + "Future Studio Icon.png");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

//                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static void initSpinner(Context context, View view, String[] datas, String[] firstSelection){
        if(view==null || context==null || datas==null)
            return;

        if(view instanceof Spinner){
            String[] allData = datas;
            if(firstSelection!=null){
                allData = CommonUtils.concatAll(firstSelection, datas);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, allData);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ((Spinner) view).setAdapter(adapter);
        }
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
