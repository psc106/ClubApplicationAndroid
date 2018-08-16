package teamproject.com.clubapplication.utils.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    //자신의 환경에 맞춰서 serverURL 바꿀것
    private String serverURL = "http://192.168.0.70:8090/club_application/";

    public static RetrofitService curr = null;
    private RetrofitRequest retrofitRequest;

    public static RetrofitService getInstance() {
        if (curr == null) {
            curr = new RetrofitService();
        }

        return curr;
    }

    private RetrofitService() {
        retrofitRequest = init();
    }

    public RetrofitRequest init() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(serverURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(RetrofitRequest.class);
    }

    public RetrofitRequest getRetrofitRequest() {
        return retrofitRequest;
    }
}
