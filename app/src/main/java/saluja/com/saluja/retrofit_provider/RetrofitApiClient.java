package saluja.com.saluja.retrofit_provider;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import saluja.com.saluja.constant.Constant;

public interface RetrofitApiClient {

    @GET(Constant.LOGIN_URL)
    Call<ResponseBody> signIn(@Query("email") String email,
                              @Query("password") String password);

    @FormUrlEncoded
    @POST(Constant.LOGIN_URL)
    Call<ResponseBody> signUp(@Field("email") String email,
                              @Field("password") String password,
                               @Field("first_name") String first_name,
                              @Field("last_name") String last_name);
}