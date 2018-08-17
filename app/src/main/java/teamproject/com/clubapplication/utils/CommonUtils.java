package teamproject.com.clubapplication.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import teamproject.com.clubapplication.FindIdPwActivity;
import teamproject.com.clubapplication.JoinActivity;
import teamproject.com.clubapplication.LoginActivity;
import teamproject.com.clubapplication.MainActivity;
import teamproject.com.clubapplication.MyAlarmActivity;
import teamproject.com.clubapplication.MyCalendarActivity;
import teamproject.com.clubapplication.MyContentActivity;
import teamproject.com.clubapplication.MyGroupActivity;
import teamproject.com.clubapplication.MyInfoActivity;
import teamproject.com.clubapplication.MyOptionActivity;

public class CommonUtils {
    public static String getStringFromServer(String pUrl) {
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

    public static boolean isLoginNeedActivity(Activity activity) {
        return (activity.getClass()== MyAlarmActivity.class)||(activity.getClass()!= MyOptionActivity.class)||(activity.getClass()!= MyInfoActivity.class)
                ||(activity.getClass()!= MyGroupActivity.class)||(activity.getClass()!= MyContentActivity.class)||(activity.getClass()!= MyCalendarActivity.class);
    }

    public static boolean isLogoutNeedActivity(Activity activity) {
        return (activity.getClass()== LoginActivity.class)||(activity.getClass()== JoinActivity.class)||(activity.getClass()== FindIdPwActivity.class);
    }
}
