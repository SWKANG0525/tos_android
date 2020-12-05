package deu.cse.tos;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;


import java.util.Timer;
import java.util.TimerTask;


public class VideoActivity extends AppCompatActivity {
    int counter = 0;
    ProgressBar videobar;
<<<<<<< HEAD
    TextView textView;
=======
    int toggle = 1;

    @SuppressLint("ClickableViewAccessibility")
>>>>>>> 900bcd5f39017cfbabbd4a850bb88fbae92caaae
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
        setContentView(R.layout.activity_video);

        prog();

        Intent i = new Intent(this, SelfCheckActivity.class);
<<<<<<< HEAD

        Uri uri = Uri.parse("android.resource://deu.cse.tos/" + R.raw.tooth_video);
=======
//        TextView textView = (TextView) findViewById(R.id.video_mode_txt);
        Uri uri = Uri.parse("android.resource://deu.cse.tos/" + R.raw.video_test1);
>>>>>>> 900bcd5f39017cfbabbd4a850bb88fbae92caaae
        VideoView videoView = (VideoView) findViewById(R.id.videoview);
        videoView.setVideoURI(uri);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                //textView.setText("양치를 진행하세요 !!");
                videoView.start();           // start the video
                videoView.setVisibility(View.VISIBLE);

            }

        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                //textView.setText("양치가 끝났습니다 !!");
                startActivity(i);
            }
        });

        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (videoView.isPlaying()) {
                    videoView.pause();
                    return true;
                }else{
                    videoView.start();
                    return true;
                }
            }
        });

    }
        public void prog () {
            videobar = (ProgressBar) findViewById(R.id.videobar);
            final Timer t = new Timer();
            TimerTask tt = new TimerTask() {
                @Override
                public void run() {
                    counter++;
                    videobar.setProgress(counter);
                    if (counter == 100)
                        t.cancel();
                }

        } ;

        t.schedule(tt, 1, 1500);

    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}





