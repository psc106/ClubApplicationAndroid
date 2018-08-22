package teamproject.com.clubapplication.utils.retrofit;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import teamproject.com.clubapplication.data.Alarm;
import teamproject.com.clubapplication.data.CalendarSchedule;
import teamproject.com.clubapplication.data.Club;
import teamproject.com.clubapplication.data.Comment;
import teamproject.com.clubapplication.data.Member;
import teamproject.com.clubapplication.data.Post;
import teamproject.com.clubapplication.data.Schedule;
import teamproject.com.clubapplication.data.TestData;

public interface RetrofitRequest {
    @GET("androidTest.do")
    Call<ArrayList<TestData>> getTest();

    @Multipart
    @POST("testImage.do")
    Call<ResponseBody> uploadFile(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file);

    @GET("mobile/selectLoginUser.do")
    Call<Member> selectLoginUser(@Query("id") String id, @Query("pw") String pw);

    @GET("mobile/checkId.do")
    Call<Integer> checkId(@Query("id") String id);

    @GET("mobile/findId.do")
    Call<String> findId(@Query("email") String email);

    @GET("mobile/findPw.do")
    Call<Void> findPw(@Query("email") String email, @Query("id") String id);

    @GET("mobile/selectMyAlarm.do")
    Call<ArrayList<Alarm>> selectMyAlarm(@Query("userId") Long userId);

    @GET("mobile/selectMyPost.do")
    Call<ArrayList<Post>> selectMyPost(@Query("userId") Long userId);

    @GET("mobile/selectMyComment.do")
    Call<ArrayList<Comment>> selectMyComment(@Query("userId") Long userId);

    @GET("mobile/selectMySchedule.do")
    Call<ArrayList<Schedule>> selectMySchedule(@Query("userId") Long userId);

    @GET("mobile/selectMyCalendar.do")
    Call<ArrayList<CalendarSchedule>> selectMyCalendar(@Query("userId") Long userId, @Query("year") int year, @Query("month") int month);

    @GET("mobile/selectMyGroup.do")
    Call<ArrayList<Club>> selectMyClub(@Query("userId") Long userId);

    @FormUrlEncoded
    @POST("mobile/insertMember.do")
    Call<Void> insertMember(@Field("id") String id, @Field("pw") String pw, @Field("name") String name, @Field("birthday") String birthday,
                            @Field("gender") Integer gender, @Field("local") String local, @Field("email") String email, @Field("phone") String phone);


}
