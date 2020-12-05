package deu.cse.tos;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class TimerActivity extends AppCompatActivity {

    private LottieAnimationView animationView;

    ProgressBar progressBar;
    EditText time_out_min, time_out_sec;
    Button btn_start, btn_reset;
    InputMethodManager imm;
    Handler handler;
    final static int INIT = 0;
    final static int RUN = 1;
    final static int PAUSE = 2;

    int cur_status = INIT;
    long baseTime;
    long pauseTime;
    long setTime;

    public void init() {
        animationView = findViewById(R.id.tooth_top);
        animationView.setVisibility(View.INVISIBLE);
        animationView = findViewById(R.id.tooth_front_bottom);
        animationView.setVisibility(View.INVISIBLE);
        animationView = findViewById(R.id.tooth_top);
        animationView.setVisibility(View.VISIBLE);
    }



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
        setContentView(R.layout.activity_timer);
        init();
        Intent i = new Intent(this, SelfCheckActivity.class);
        TextView textView = (TextView) findViewById(R.id.timer_mode_txt) ;
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        progressBar = findViewById(R.id.progressBar);
        time_out_min = findViewById(R.id.time_out_min);
        go_start();


        handler = new Handler() {
            public void handleMessage(Message msg) {
                String time = getTimeOut();
                if (time.equals("00:00")) {
                    textView.setText("양치를 완료했어요 !!") ;
                    reset();
                    startActivity(i);
                }else if (time.equals("00:10")) {
                    textView.setText("혀를 10초 동안 닦으세요 !!") ;
                    handler.sendEmptyMessage(0);
                    animationView.pauseAnimation();
                    animationView.setVisibility(View.INVISIBLE);
                    animationView = findViewById(R.id.tooth_front_bottom);
                    animationView.setVisibility(View.VISIBLE);
                    animationView.playAnimation();
                }else if (time.equals("00:30")) {
                    textView.setText("앞니의 안쪽을 20초 동안 닦으세요 !!") ;
                    handler.sendEmptyMessage(0);
                    animationView.pauseAnimation();
                    animationView.setVisibility(View.INVISIBLE);
                    animationView = findViewById(R.id.tooth_top);
                    animationView.setVisibility(View.VISIBLE);
                    animationView.playAnimation();
                }else if (time.equals("00:50")) {
                    textView.setText("윗니를 20초 동안 닦으세요 !!") ;
                    handler.sendEmptyMessage(0);
                    animationView.pauseAnimation();
                    animationView.setVisibility(View.INVISIBLE);
                    animationView = findViewById(R.id.tooth_front_bottom);
                    animationView.setVisibility(View.VISIBLE);
                    animationView.playAnimation();
                }else if (time.equals("01:10")) {
                    textView.setText("아랫니 치아 안쪽 부분 20초 동안 닦으세요 !!") ;
                    handler.sendEmptyMessage(0);
                    animationView.pauseAnimation();
                    animationView.setVisibility(View.INVISIBLE);
                    animationView = findViewById(R.id.tooth_top);
                    animationView.setVisibility(View.VISIBLE);
                    animationView.playAnimation();
                }else if (time.equals("01:30")) {
                    textView.setText("아랫니 앞니를 20초 동안 닦으세요 !!") ;
                    handler.sendEmptyMessage(0);
                    animationView.pauseAnimation();
                    animationView.setVisibility(View.INVISIBLE);
                    animationView = findViewById(R.id.tooth_front_bottom);
                    animationView.setVisibility(View.VISIBLE);
                    animationView.playAnimation();
                }else if (time.equals("01:50")) {
                    textView.setText("아랫니 어금니를 20초 동안 닦으세요 !!") ;
                    handler.sendEmptyMessage(0);
                    animationView.pauseAnimation();
                    animationView.setVisibility(View.INVISIBLE);
                    animationView = findViewById(R.id.tooth_top);
                    animationView.setVisibility(View.VISIBLE);
                    animationView.playAnimation();
                }else if (time.equals("02:10")) {
                    textView.setText("씹는쪽을 20초 동안 닦으세요 !!") ;
                    handler.sendEmptyMessage(0);
                    animationView.pauseAnimation();
                    animationView.setVisibility(View.INVISIBLE);
                    animationView = findViewById(R.id.tooth_front_bottom);
                    animationView.setVisibility(View.VISIBLE);
                    animationView.playAnimation();
                }else if (time.equals("02:30")) {
                    textView.setText("앞니 안쪽을 20초 동안 닦으세요 !!") ;
                    handler.sendEmptyMessage(0);
                    animationView.pauseAnimation();
                    animationView.setVisibility(View.INVISIBLE);
                    animationView = findViewById(R.id.tooth_top);
                    animationView.setVisibility(View.VISIBLE);
                    animationView.playAnimation();
                }else if (time.equals("02:50")) {
                    textView.setText("윗니 안쪽을 20초 동안 닦으세요 !!") ;
                    handler.sendEmptyMessage(0);
                    animationView.pauseAnimation();
                    animationView.setVisibility(View.INVISIBLE);
                    animationView = findViewById(R.id.tooth_front_bottom);
                    animationView.setVisibility(View.VISIBLE);
                    animationView.playAnimation();
                }else if (time.equals("03:10")) {
                    textView.setText("윗니 앞니를 20초 동안 닦으세요 !!") ;
                    handler.sendEmptyMessage(0);
                    animationView.pauseAnimation();
                    animationView.setVisibility(View.INVISIBLE);
                    animationView = findViewById(R.id.tooth_top);
                    animationView.setVisibility(View.VISIBLE);
                    animationView.playAnimation();
                }else if (time.equals("03:30")) {
                    textView.setText("윗니 어금니를 20초 동안 닦으세요 !!") ;
                    handler.sendEmptyMessage(0);
                    animationView.pauseAnimation();
                    animationView.setVisibility(View.INVISIBLE);
                    animationView = findViewById(R.id.tooth_front_bottom);
                    animationView.setVisibility(View.VISIBLE);
                    animationView.playAnimation();
                }else {
                    handler.sendEmptyMessage(0);
                }

            }

        };


        time_out_min.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (time_out_min.hasFocus() && getEditTime() != 0) {
                    setTime();
                    Log.d("ProgressTest", "setTime = " + setTime);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        time_out_sec = findViewById(R.id.time_out_sec);
        time_out_sec.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (time_out_sec.hasFocus() && getEditTime() != 0) {
                    setTime();
                    Log.d("ProgressTest", "setTime = " + setTime);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        btn_start = findViewById(R.id.btn_start);
        btn_reset = findViewById(R.id.btn_reset);
        btn_start.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                if (getEditTime()!= 0) {
                    hideKeyboard();
                    start(cur_status);

                } else {
                    Toast.makeText(TimerActivity.this, "시간을 입력하세요", Toast.LENGTH_SHORT).show();
                    time_out_min.requestFocus();
                }

            }
        }); btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

        //animation

    }

    public void reset() {
        handler.removeCallbacksAndMessages(null);
        cur_status = INIT;
        time_out_min.setText("03");
        time_out_min.setEnabled(true);
        time_out_sec.setText("30");
        time_out_sec.setEnabled(true);
        btn_start.setText("시작");
        btn_reset.setEnabled(false);
        progressBar.setProgress(0);
        init();
    }

    public void hideKeyboard() {
        imm.hideSoftInputFromWindow(time_out_min.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(time_out_sec.getWindowToken(), 0);
    }

    public long getEditTime() {
        long min = Long.parseLong(time_out_min.getText().toString()) * 60 * 1000;
        long sec = Long.parseLong(time_out_sec.getText().toString()) * 1000;
        return min + sec;
    }

    public void start(int status) {
        switch (status) {
            case INIT:
                baseTime = SystemClock.elapsedRealtime();
                btn_start.setText("멈춤");
                btn_reset.setEnabled(false);
                time_out_min.setEnabled(false);
                time_out_sec.setEnabled(false);
                handler.sendEmptyMessage(0);
                cur_status = RUN;
                animationView.playAnimation();
                break;
            case RUN:
                handler.removeMessages(0);
                pauseTime = SystemClock.elapsedRealtime();
                btn_start.setText("재시작");
                btn_reset.setEnabled(true);
                cur_status = PAUSE;
                animationView.pauseAnimation();
                break;

            case PAUSE:
                long now = SystemClock.elapsedRealtime();
                baseTime += (now - pauseTime);
                btn_start.setText("멈춤");
                btn_reset.setEnabled(false);
                handler.sendEmptyMessage(0);
                cur_status = RUN;
                animationView.playAnimation();
                break;
        }
    }

    public String getTimeOut() {
        long now = SystemClock.elapsedRealtime();
        long outTime = baseTime - now + setTime;
        long sec = outTime / 1000 % 60;
        long min = outTime / 1000 / 60;
        if (outTime % 1000 / 10 != 0 && sec < 60) {
            sec += 1;
            if (sec == 60) {
                sec = 0;
                min += 1;
            }
        }
        String easy_outTime = String.format("%02d:%02d", min, sec);
        String[] times = easy_outTime.split(":");
        time_out_min.setText(times[0]);
        time_out_sec.setText(times[1]);
        progressBar.setProgress((int) ((now - baseTime) + (setTime / 1000)));
        return easy_outTime;
    }

    public void setTime() {
        setTime = Long.parseLong("03") * 1000 * 60 + Long.parseLong("30") * 1000;
        //setTime = Long.parseLong(time_out_min.getText().toString()) * 1000 * 60 + Long.parseLong(time_out_sec.getText().toString()) * 1000;
        progressBar.setMax((int) setTime);
    }

    public void go_start(){
        setTime();
        Log.d("ProgressTest", "setTime = " + setTime);
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}


