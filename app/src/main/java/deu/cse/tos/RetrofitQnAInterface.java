package deu.cse.tos;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitQnAInterface {
    @FormUrlEncoded
    @POST("qna/select_qnalist")
    Call<JsonObject> postQnAResult(@FieldMap HashMap<String, Object> param);
}
