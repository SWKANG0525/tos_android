package deu.cse.tos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitToothService {
    @FormUrlEncoded
    @POST("tooth/insert_brushlist")
    Call<CheckResult>postBrushListResult(@FieldMap HashMap<String, Object> param);

    @FormUrlEncoded
    @POST("tooth/select_brushlist")
    Call<BrushListDTO>postBrushListSelectResult(@FieldMap HashMap<String, Object> param);
}
