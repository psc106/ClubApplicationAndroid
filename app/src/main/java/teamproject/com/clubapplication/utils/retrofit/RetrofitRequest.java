package teamproject.com.clubapplication.utils.retrofit;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import teamproject.com.clubapplication.data.TestData;

public interface RetrofitRequest {
    @GET("androidTest.do")
    Call<ArrayList<TestData>> getTest();
}
