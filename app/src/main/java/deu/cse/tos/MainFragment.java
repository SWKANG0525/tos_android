package deu.cse.tos;


import android.content.Intent;
import android.graphics.Color;
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
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainFragment() {
        // Required empty public constructor

    }
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    // TODO: Rename and change types and number of parameters


    @Override
    public void onResume() {

        super.onResume();
        getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);

    }

    private int returnToothRes(int count) {

        switch(count) {
            case 0:
                return R.drawable.group_240;
            case 1:
                return R.drawable.group_242;
            default:
                return R.drawable.group_239;
        }

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
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

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
        ImageButton button = (ImageButton) getView().findViewById(R.id.brushimageButton);
        Intent testIntent = new Intent(getActivity(), BrushListActivity.class);
        button.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                //startActivity(i);
                ((MainActivity)getActivity()).replaceFragment(ModeFragment.newInstance());

            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://113.198.235.232:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        HashMap<String, Object> input = new HashMap<>();

        input.put("hash_key",UserAccount.getInstance().getHash_key());
        retrofitService.postUserResult(input).enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                if(response.isSuccessful()) {
                    UserDTO data = response.body();
                    TextView textview3 = getActivity().findViewById(R.id.textView3);
                    TextView textview4 = getActivity().findViewById(R.id.textView4);

                    textview3.setText(data.getNickname());
                    textview4.setText("Level "+data.getLevel());

                    UserAccount.getInstance().setNickName(data.getNickname());
                    Log.d("UserDTO",data.toString());
                }
            }
            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {

            }
        });


        retrofitService.postToothResult(input).enqueue(new Callback<ToothInfoDTO>() {
            @Override
            public void onResponse(Call<ToothInfoDTO> call, Response<ToothInfoDTO> response) {
                if(response.isSuccessful()) {
                    ToothInfoDTO data = response.body();


                    TextView textview5 = getActivity().findViewById(R.id.textView5);
                    ImageView imageView4 = getActivity().findViewById(R.id.imageView4);
                    ImageView imageView5 = getActivity().findViewById(R.id.imageView5);
                    ImageView imageView6 = getActivity().findViewById(R.id.imageView6);
                    ImageView imageView7 = getActivity().findViewById(R.id.imageView7);

                    if(!data.getDifftime().equals("0")) {
                        textview5.setText(data.getDifftime()+" 시간 전에 양치했어요");
                    }
                    else {
                        textview5.setText("방금 전 양치했어요");
                    }

                    if(!data.getMorning_time().equals("0")) {

                        imageView4.setImageResource(returnToothRes(data.getMorning_count()));
                    }
                    else if(data.getMorning_time().equals("0"))
                        imageView4.setImageResource(R.drawable.group_241);

                    if(!data.getAfternoon_time().equals("0")) {
                        imageView5.setImageResource(returnToothRes(data.getAfternoon_count()));
                    }
                    else if(data.getAfternoon_time().equals("0"))
                        imageView5.setImageResource(R.drawable.group_241);

                    if(!data.getDinner_time().equals("0")) {
                        imageView6.setImageResource(returnToothRes(data.getDinner_count()));
                    }
                    else if(data.getDinner_time().equals("0"))
                        imageView6.setImageResource(R.drawable.group_241);

                    Log.d("Night",data.getNight_time());
                    if(!data.getNight_time().equals("0")) {
                        imageView7.setImageResource(returnToothRes(data.getNight_count()));
                    }
                    else if(data.getNight_time().equals("0"))
                        imageView7.setImageResource(R.drawable.group_241);

                    Log.d("UserDTO",data.toString());
                }
            }
            @Override
            public void onFailure(Call<ToothInfoDTO> call, Throwable t) {

            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initActivity();
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
    
}