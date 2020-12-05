package deu.cse.tos;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class ModeActivity extends AppCompatActivity {
    private Context mContext = ModeActivity.this;
    private static final int ACTIVITY_NUM = 4;
    private static final String TAG = "ModeActivity";

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
        setContentView(R.layout.fragment_mode);

        ImageButton button1 = (ImageButton) findViewById(R.id.imageButtonmode1);
        ImageButton button2 = (ImageButton) findViewById(R.id.imageButtonmode2);
        Intent i = new Intent(this, VideoActivity.class);
        Intent j = new Intent(this, TimerActivity.class);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(i);
            }

        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(j);
            }

        });

    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}