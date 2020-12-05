package deu.cse.tos;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.util.HashMap;

public class CheckResultActivity extends AppCompatActivity {
    private Context mContext = CheckResultActivity.this;
    private static final int ACTIVITY_NUM = 4;
    private static final String TAG = "CheckResultActivity";
    private TextView tv_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(0x00000000);  // transparent
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            window.addFlags(flags);
        }
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkresult);
        tv_name = findViewById(R.id.tv_name);
        tv_name.setText(UserAccount.getInstance().getNickName());
        BarChart mBarChart2 = (BarChart) findViewById(R.id.barchart2);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://113.198.235.232:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        HashMap<String, Object> input = new HashMap<>();

        input.put("hash_key",UserAccount.getInstance().getHash_key());
        retrofitService.postTodayGraphResult(input).enqueue(new Callback<TodayGraphDTO>() {
            @Override
            public void onResponse(Call<TodayGraphDTO> call, Response<TodayGraphDTO> response) {
                if(response.isSuccessful()) {
                    TodayGraphDTO data = response.body();


                    mBarChart2.addBar(new BarModel("아침",data.getMorning_count(), 0xFF98BFBD));
                    mBarChart2.addBar(new BarModel("점심",data.getAfternoon_count(),  0xFF98BFBD));
                    mBarChart2.addBar(new BarModel("저녁",data.getDinner_count(), 0xFF98BFBD));
                    mBarChart2.addBar(new BarModel("자기 전",data.getNight_count(), 0xFFDADADA));
                    mBarChart2.bringToFront();
                    mBarChart2.startAnimation();


                    Log.d("UserDTO",data.toString());
                }
            }
            @Override
            public void onFailure(Call<TodayGraphDTO> call, Throwable t) {

            }
        });

        Intent i = new Intent(this, MainActivity.class);
        Button button=findViewById(R.id.btn_register2);
        button.setOnClickListener(new View.OnClickListener() {//버튼 이벤트 처리
            @Override
            public void onClick(View view) {
                startActivity(i);
                System.out.println("화면 전환 성공");
            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}