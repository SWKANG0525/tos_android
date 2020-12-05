package deu.cse.tos;

import android.app.DatePickerDialog;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddBrushListActivity extends AppCompatActivity {
    private RadioButton rb1, rb2, rb3, rb4;
    private RadioGroup rg;
    private EditText et;
    private DatePickerDialog datePickerDialog;
    private TextView userName, itemName, usingDate, buyDate;
    private Retrofit retrofit;
    private Intent previousIntent;
    private Button img_btn;

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
        setContentView(R.layout.activity_addbrushlist);


        Calendar c = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(AddBrushListActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                try {
                    Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    //et.setText(new StringBuilder().append(year).append("-").append(monthOfYear + 1).append("-").append(dayOfMonth).append(" "));
                    et.setText(formatter.format(d));
                    long diff = new Date().getTime() - d.getTime();
                    usingDate.setText(Long.toString(diff/(24*60*60*1000)));
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));


        datePickerDialog.getDatePicker().setCalendarViewShown(false);
        datePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        img_btn = findViewById(R.id.btn_register);
        userName = findViewById(R.id.tv_name);
        itemName = findViewById(R.id.tv_list_item_name);
        usingDate = findViewById(R.id.tv_date);
        buyDate = findViewById(R.id.editTextDate);
        et = findViewById(R.id.editTextDate);
        et.setTextIsSelectable(true);
        et.setShowSoftInputOnFocus(false);
        rg = (RadioGroup) findViewById(R.id.radioGroup);
        rb1 = (RadioButton) findViewById(R.id.radioButton1);
        rb2 = (RadioButton) findViewById(R.id.radioButton2);
        rb3 = (RadioButton) findViewById(R.id.radioButton3);
        rb4 = (RadioButton) findViewById(R.id.radioButton4);

        updateTextView();

        et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        img_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                retrofit = new Retrofit.Builder()
                        .baseUrl("http://113.198.235.232:3000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RetrofitToothService retrofitToothService = retrofit.create(RetrofitToothService.class);
                HashMap<String, Object> input = new HashMap<>();

                input.put("hash_key",UserAccount.getInstance().getHash_key());
                input.put("item_name", itemName.getText());
                input.put("buy_date", buyDate.getText());

                retrofitToothService.postBrushListResult(input).enqueue(new Callback<CheckResult>() {
                    @Override
                    public void onResponse(Call<CheckResult> call, Response<CheckResult> response) {
                        if(response.isSuccessful()) {
                            CheckResult data = response.body();
                            if (data.getResult().equals("true")) {
                                Intent nextIntent = new Intent(AddBrushListActivity.this, BrushListActivity.class);
                                startActivity(nextIntent);
                            }
                            else if (data.getResult().equals("false")) {
                                Toast.makeText(AddBrushListActivity.this, "올바르지 않은 입력입니다.", Toast.LENGTH_SHORT).show();
                            }
                            Log.d("CheckResult",data.toString());
                        }
                    }
                    @Override
                    public void onFailure(Call<CheckResult> call, Throwable t) {
                    }
                });

            }
        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                rb1.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                rb2.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                rb3.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                rb4.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                switch (i) {
                    case R.id.radioButton1:
                        itemName.setText(rb1.getTag().toString());
                        rb1.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case R.id.radioButton2:
                        itemName.setText(rb2.getTag().toString());
                        rb2.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case R.id.radioButton3:
                        itemName.setText(rb3.getTag().toString());
                        rb3.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case R.id.radioButton4:
                        itemName.setText(rb4.getTag().toString());
                        rb4.setTextColor(getResources().getColor(R.color.white));
                        break;
                }
            }
        });
    }
    public void updateTextView() {
        Intent previousIntent = getIntent();
        userName.setText(UserAccount.getInstance().getNickName());
        itemName.setText(previousIntent.getStringExtra("itemName"));
        usingDate.setText(previousIntent.getStringExtra("usingDate"));
        buyDate.setText(previousIntent.getStringExtra("buyDate"));
        rb1.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        rb2.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        rb3.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        rb4.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        if(previousIntent.getStringExtra("itemName").equals(rb1.getTag().toString())) {
            rg.check(R.id.radioButton1);
            rb1.setTextColor(getResources().getColor(R.color.white));
        }
        else if(previousIntent.getStringExtra("itemName").equals(rb2.getTag().toString())) {
            rg.check(R.id.radioButton2);
            rb2.setTextColor(getResources().getColor(R.color.white));
        }else if(previousIntent.getStringExtra("itemName").equals(rb3.getTag().toString())) {
            rg.check(R.id.radioButton3);
            rb3.setTextColor(getResources().getColor(R.color.white));
        }
        else if(previousIntent.getStringExtra("itemName").equals(rb4.getTag().toString())) {
            rg.check(R.id.radioButton4);
            rb4.setTextColor(getResources().getColor(R.color.white));
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
