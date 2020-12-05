package deu.cse.tos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class IntroActivity extends AppCompatActivity {

    Handler handler = new Handler();
    Runnable r = new Runnable() {
        @Override
        public void run() {// 4초뒤에 다음화면(MainActivity)으로 넘어가기 Handler 사용
            Intent intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
            //Intent intentNotLogin = new Intent(getApplicationContext(), NotLoginActivity.class);
            startActivity(intentLogin); // 다음화면으로 넘어가기
            //startActivity(intentNotLogin);
            finish(); // Activity 화면 제거
        }
    };

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
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
    }

    @Override
    protected void onResume() {
        super.onResume();// 다시 화면에 들어어왔을 때 예약 걸어주기
        handler.postDelayed(r, 2000); // 4초 뒤에 Runnable 객체 수행
    }

    @Override
    protected void onPause() {
        super.onPause();// 화면을 벗어나면, handler 에 예약해놓은 작업을 취소하자
        handler.removeCallbacks(r); // 예약 취소
    }

}