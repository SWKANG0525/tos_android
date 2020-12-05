package deu.cse.tos;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BrushListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OralSuppliesAdapter oralSuppliesAdapter;
    private ArrayList<OralSupplies> oralItems;
    private Intent nextIntent;
    private FloatingActionButton btn;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private Retrofit retrofit;
    private List<BrushListDTO.BrushDTO> items;

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
        setContentView(R.layout.activity_brushlist);

        btn = findViewById(R.id.floatingActionButton);
        oralItems = new ArrayList<>();
        oralSuppliesAdapter = new OralSuppliesAdapter(this, oralItems, onClickItem);
        createRecyclerView();
        // OralSupplies 객체 생성
        oralSuppliesAdapter.notifyDataSetChanged();
        nextIntent = new Intent(this, AddBrushListActivity.class);
        getAPI();
        btn.setOnClickListener((view) -> {
            nextIntent.putExtra("itemName", "--");
            nextIntent.putExtra("buyDate", " ");
            nextIntent.putExtra("usingDate", "--");
            startActivity(nextIntent);
        });

        initActionBar();
    }

    private View.OnClickListener onClickItem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            OralSupplies item = oralSuppliesAdapter.getItem((int) v.getTag());
            nextIntent.putExtra("itemName", item.getItemName());
            nextIntent.putExtra("buyDate", items.get((int) v.getTag()).buy_date);
            nextIntent.putExtra("usingDate", Integer.toString(items.get((int) v.getTag()).using_date));
            startActivity(nextIntent);
        }
    };

    private void createRecyclerView() {
        recyclerView = findViewById(R.id.rv_oral_supplies);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(oralSuppliesAdapter);
        recyclerView.setHasFixedSize(true);
    }

    public void initActionBar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_customactionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                break;
            case R.id.account:
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getAPI() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://113.198.235.232:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitToothService retrofitToothService = retrofit.create(RetrofitToothService.class);
        HashMap<String, Object> input = new HashMap<>();
        input.put("hash_key", UserAccount.getInstance().getHash_key());
        retrofitToothService.postBrushListSelectResult(input).enqueue(new Callback<BrushListDTO>() {
            @Override
            public void onResponse(Call<BrushListDTO> call, Response<BrushListDTO> response) {
                if (response.isSuccessful()) {
                    BrushListDTO data = response.body();
                    items = data.getData();
                    if (items != null) {
                        for (BrushListDTO.BrushDTO item : items) {
                            oralItems.add(new OralSupplies(item.remain_recommend_date, item.item_name, item.recommend_date));
                            oralSuppliesAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BrushListDTO> call, Throwable t) {
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
