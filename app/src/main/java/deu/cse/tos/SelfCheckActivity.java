package deu.cse.tos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.time.Duration;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SelfCheckActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView selftext1;
    private TextView selftext3;
    private TextView selftext4;
    private CheckBox check1;
    private CheckBox check3;
    private CheckBox check4;
    private  TextView tv_name;


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
        setContentView(R.layout.activity_selfcheck);

        check1 = (CheckBox) findViewById(R.id.checkbox1);
        check3 = (CheckBox) findViewById(R.id.checkbox3);
        check4 = (CheckBox) findViewById(R.id.checkbox4);
        selftext1 = (TextView) findViewById(R.id.selfcheck1);
        selftext3 = (TextView) findViewById(R.id.selfcheck3);
        selftext4 = (TextView) findViewById(R.id.selfcheck4);
        tv_name = findViewById(R.id.tv_name);
        tv_name.setText(UserAccount.getInstance().getNickName());
        Intent i = new Intent(this, CheckResultActivity.class);
        selftext1.setOnClickListener(this);
        selftext3.setOnClickListener(this);
        selftext4.setOnClickListener(this);

        Button button=findViewById(R.id.btn_register2);
        button.setOnClickListener(new View.OnClickListener() {//버튼 이벤트 처리
            @Override
            public void onClick(View view) {
                if(!check1.isChecked() &&  !check3.isChecked() && !check4.isChecked() )
                {
                    Toast.makeText(SelfCheckActivity.this, "오늘 치아가 건강하군요", Toast.LENGTH_SHORT).show();
                }
// hash_key, tooth_ache, tooth_wobble, tooth_cold
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://113.198.235.232:3000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RetrofitSelfCheckService retrofitSelfCheckService = retrofit.create(RetrofitSelfCheckService.class);
                HashMap<String, Object> input = new HashMap<>();

                input.put("hash_key",UserAccount.getInstance().getHash_key());
                if(check1.isChecked())
                    input.put("tooth_cold",1);
                else if(!check1.isChecked())
                    input.put("tooth_cold",0);

                if(check3.isChecked())
                    input.put("tooth_ache",1);
                else if(!check3.isChecked())
                    input.put("tooth_ache",0);

                if(check4.isChecked())
                    input.put("tooth_wobble",1);
                else if(!check4.isChecked())
                    input.put("tooth_wobble",0);

                retrofitSelfCheckService.postSelfUserResult(input).enqueue(new Callback<CheckResult>() {
                    @Override
                    public void onResponse(Call<CheckResult> call, Response<CheckResult> response) {
                        if(response.isSuccessful()) {
                            CheckResult data = response.body();

                            Log.d("CheckResult",data.toString());
                            startActivity(i);
                        }
                    }
                    @Override
                    public void onFailure(Call<CheckResult> call, Throwable t) {
                        Toast.makeText(SelfCheckActivity.this, "서버값에 접근할 수 없습니다.", Toast.LENGTH_SHORT).show();
                        startActivity(i);
                    }
                });

            }
        });
    }



    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.selfcheck1:
                if(check1.isChecked()){
                    check1.setChecked(false);
                }else {
                    check1.setChecked(true);
                }
                break;
            case R.id.selfcheck3:
                if(check3.isChecked()){
                    check3.setChecked(false);
                }else {
                    check3.setChecked(true);
                }
                break;
            case R.id.selfcheck4:
                if(check4.isChecked()){
                    check4.setChecked(false);
                }else {
                    check4.setChecked(true);
                }
                break;

        }
    }
}