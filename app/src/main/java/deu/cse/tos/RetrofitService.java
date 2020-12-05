package deu.cse.tos;

import java.time.Year;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitService {


    @FormUrlEncoded
    @POST("auth/info")
    Call<UserDTO> postUserResult(@FieldMap HashMap<String, Object> param);

    @FormUrlEncoded
    @POST("tooth/info")
    Call<ToothInfoDTO> postToothResult(@FieldMap HashMap<String, Object> param);

    @FormUrlEncoded
    @POST("tooth/yearscore")
    Call<YearGraphDTO> postYearGraphResult(@FieldMap HashMap<String,Object> param);

    @FormUrlEncoded
    @POST("tooth/insight")
    Call<InsightDTO> postInsightResult(@FieldMap HashMap<String,Object> param);

    @FormUrlEncoded
    @POST("tooth/todayscore")
    Call<TodayGraphDTO> postTodayGraphResult(@FieldMap HashMap<String,Object> param);

    @FormUrlEncoded
    @POST("qna/select_qnalist")
    Call<QnaDTO> postQnAResult(@FieldMap HashMap<String,Object> param);



}
