package deu.cse.tos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InformationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button button1;
    private Intent intent1;

    Spinner spinner;
    BarChart chart2;
    ImageView image;
    TextView textView12,textView13,textView14,tv_date,tv_name;

    public InformationFragment() {
        // Required empty public constructor
    }
    private void initActivity() {
        // MAIN SET UP Navigation Bar & Status Bar
        Window window = getActivity().getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(0x00000000);  // transparent
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            window.addFlags(flags);
        }
        View decorView = getActivity().getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.intent1 = new Intent(getActivity(), BrushListActivity.class);
        this.button1 = (Button) view.findViewById(R.id.btn_oral_list);

        //chartbar
        BarChart mBarChart = (BarChart) getActivity().findViewById(R.id.barchart);


        Animation bottomAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_animation);

        //Hooks
        image = getActivity().findViewById(R.id.image_view);
        image.setAnimation(bottomAnim);
        textView12 = getActivity().findViewById(R.id.textView12);
        textView12.setAnimation(bottomAnim);
        textView13 = getActivity().findViewById(R.id.textView13);
        textView13.setAnimation(bottomAnim);
        textView14 = getActivity().findViewById(R.id.textView14);
        textView14.setAnimation(bottomAnim);
        tv_date = getActivity().findViewById(R.id.tv_date);
        tv_name = getActivity().findViewById(R.id.tv_name);



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://113.198.235.232:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        HashMap<String, Object> input = new HashMap<>();

        input.put("hash_key",UserAccount.getInstance().getHash_key());
        retrofitService.postYearGraphResult(input).enqueue(new Callback<YearGraphDTO>() {
            @Override
            public void onResponse(Call<YearGraphDTO> call, Response<YearGraphDTO> response) {
                if(response.isSuccessful()) {
                    YearGraphDTO data = response.body();

                    mBarChart.addBar(new BarModel("JAN",data.getJan(), 0xFF98BFBD));
                    mBarChart.addBar(new BarModel("FEB",data.getFeb(),  0xFF98BFBD));
                    mBarChart.addBar(new BarModel("MAR",data.getMar(), 0xFF98BFBD));
                    mBarChart.addBar(new BarModel("APR",data.getApr(), 0xFF98BFBD));
                    mBarChart.addBar(new BarModel("MAY",data.getMay(), 0xFF98BFBD));
                    mBarChart.addBar(new BarModel("JUNE",data.getJune(),  0xFF98BFBD));
                    mBarChart.addBar(new BarModel("JULY",data.getJuly(), 0xFF98BFBD));
                    mBarChart.addBar(new BarModel("AUG",data.getAug(),  0xFF98BFBD));
                    mBarChart.addBar(new BarModel("SEP",data.getSep(),  0xFF98BFBD));
                    mBarChart.addBar(new BarModel("OCT",data.getOct(),  0xFF98BFBD));
                    mBarChart.addBar(new BarModel("NOV",data.getNov(),  0xFF98BFBD));
                    mBarChart.addBar(new BarModel("DEC",data.getDec(),  0xFF98BFBD));
                    mBarChart.bringToFront();
                    mBarChart.startAnimation();


                    Log.d("UserDTO",data.toString());
                }
            }
            @Override
            public void onFailure(Call<YearGraphDTO> call, Throwable t) {

            }
        });


        retrofitService.postInsightResult(input).enqueue(new Callback<InsightDTO>() {
            @Override
            public void onResponse(Call<InsightDTO> call, Response<InsightDTO> response) {
                if(response.isSuccessful()) {
                    InsightDTO data = response.body();
                    textView12.setText(UserAccount.getInstance().getNickName()+"님의 구강점수");
                    textView13.setText(String.valueOf(data.getTooth_score()));
                    tv_date.setText(data.getMonth_tooth_number()+" 회");
                    tv_name.setText(UserAccount.getInstance().getNickName());


                }
            }
            @Override
            public void onFailure(Call<InsightDTO> call, Throwable t) {

            }
        });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent1);
            }

        });


        spinner = getActivity().findViewById(R.id.spinner);
        ArrayAdapter monthAdapter = ArrayAdapter.createFromResource(getContext(), R.array.nal, android.R.layout.simple_spinner_dropdown_item);
        //R.array.test는 저희가 정의해놓은 1월~12월 / android.R.layout.simple_spinner_dropdown_item은 기본으로 제공해주는 형식입니다.
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(monthAdapter); //어댑터에 연결해줍니다.


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            } //이 오버라이드 메소드에서 position은 몇번째 값이 클릭됬는지 알 수 있습니다.
            //getItemAtPosition(position)를 통해서 해당 값을 받아올수있습니다.

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        spinner.bringToFront();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initActivity();
        return inflater.inflate(R.layout.fragment_information, container, false);
    }
}