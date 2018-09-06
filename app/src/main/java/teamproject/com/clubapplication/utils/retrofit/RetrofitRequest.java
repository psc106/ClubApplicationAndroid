package teamproject.com.clubapplication.utils.retrofit;

import java.net.URL;
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
import retrofit2.http.Url;
import teamproject.com.clubapplication.data.*;

public interface RetrofitRequest {
    @GET("androidTest.do")
    Call<ArrayList<TestData>> getTest();

    @Multipart
    @POST("test.do")
    Call<ResponseBody> test(
            @Part("description") RequestBody description,
            @Part ArrayList<MultipartBody.Part> images);

    @GET("test2.do")
    Call<ArrayList<String>> testImage();


    //클럽생성
    @Multipart
    @POST("mobile/insertClub.do")
    Call<Long> insertClub(@Part MultipartBody.Part image, @Part("category") RequestBody category, @Part("userId") RequestBody userId, @Part("name") RequestBody name, @Part("local") RequestBody local, @Part("maxPeople") RequestBody maxPeople, @Part("intro") RequestBody intro);

    //클럽
    @GET("mobile/selectClub.do")
    Call<ClubMemberClass> selectClub(@Query("clubId") Long clubId, @Query("userId") Long userId);
    @GET("mobile/joinClub.do")
    Call<Void> joinClub(@Query("clubId") Long clubId, @Query("userId") Long userId);
    @GET("mobile/selectClubProfileImg.do")
    Call<String> selectClubProfileImg(@Query("clubId") Long clubId);

    @GET("mobile/selectClubNotice.do")
    Call<ArrayList<Notice>> selectClubNotice(@Query("clubId") Long clubId, @Query("page") Integer page);
    @GET("mobile/getNoticeCount.do")
    Call<Integer> getNoticeCount(@Query("clubId") Long clubId);

    @GET("mobile/selectClubPost.do")
    Call<ArrayList<PostView>> selectClubPost(@Query("clubId") Long clubId, @Query("page") Integer page);
    @GET("mobile/getPostCout.do")
    Call<Integer> getPostCout(@Query("clubId") Long clubId);

    @GET("mobile/selectClubAlbum.do")
    Call<ArrayList<AlbumView>> selectClubAlbum(@Query("clubId") Long clubId, @Query("page") Integer page);
    @GET("mobile/getAlbumCount.do")
    Call<Integer> getAlbumCount(@Query("clubId") Long clubId);

    @GET("mobile/selectJoinMember.do")
    Call<ArrayList<MemberView>> selectJoinMember(@Query("clubId") Long clubId);
    @GET("mobile/selectWaitingMember.do")
    Call<ArrayList<MemberView>> selectWaitingMember(@Query("clubId") Long clubId);


    //로그인
    @GET("mobile/selectLoginUser.do")
    Call<Member> selectLoginUser(@Query("id") String id, @Query("pw") String pw);
    @GET("mobile/refreshMemberInfo.do")
    Call<Member> refreshLoginUser(@Query("id") Long id);

    //가입
    @GET("mobile/checkId.do")
    Call<Integer> checkId(@Query("id") String id);
    @FormUrlEncoded
    @POST("mobile/insertMember.do")
    Call<Void> insertMember(@Field("id") String id, @Field("pw") String pw, @Field("name") String name, @Field("birthday") String birthday,
                            @Field("gender") Integer gender, @Field("local") String local, @Field("phone") String phone);

    //정보찾기
    @GET("mobile/findId.do")
    Call<String> findId(@Query("id") String id);
    @GET("mobile/findPw.do")
    Call<Void> findPw(@Query("id") String id);

    //메뉴
    @GET("mobile/selectMyAlarm.do")
    Call<ArrayList<Alarm>> selectMyAlarm(@Query("userId") Long userId);
    @GET("mobile/selectMyPost.do")
    Call<ArrayList<PostView>> selectMyPost(@Query("userId") Long userId);
    @GET("mobile/selectMyComment.do")
    Call<ArrayList<Comment>> selectMyComment(@Query("userId") Long userId);
    @GET("mobile/selectMySchedule.do")
    Call<ArrayList<Schedule>> selectMySchedule(@Query("userId") Long userId);
    @GET("mobile/selectMyCalendar.do")
    Call<ArrayList<CalendarSchedule>> selectMyCalendar(@Query("userId") Long userId, @Query("year") int year, @Query("month") int month);
    @GET("mobile/selectMyGroup.do")
    Call<ArrayList<Club>> selectMyClub(@Query("userId") Long userId);

    //클럽 검색
    @GET("mobile/selectClubInPage.do")
    Call<ArrayList<Club>> selectClubInPage(@Query("main") String main, @Query("local") String local, @Query("category") Long category, @Query("page") Integer page);
    @GET("mobile/getResultCount.do")
    Call<Integer> getResultCount(@Query("main") String main, @Query("local") String local, @Query("category") Long category);
//
//    @GET
//    Call<ArrayList<URL>> selectImage(@Query("userId") Long userId);
//    @GET
//    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);
}
