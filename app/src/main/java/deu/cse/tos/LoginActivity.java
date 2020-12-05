package deu.cse.tos;

import androidx.appcompat.app.AppCompatActivity;

import com.kakao.sdk.auth.LoginClient;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LoginActivity extends AppCompatActivity {

    Animation topAnim, bottomAnim;
    ImageView image,image2;
    boolean issignin = true;

    private void init_activity() {
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
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getHashKey();
        KakaoSdk.init(this, "1c11ae1b9e8f2cfbeb1676908dfcd2da");
        init_activity();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ImageButton button = (ImageButton)findViewById(R.id.imageButton2);
        Intent i = new Intent(this,MainActivity.class);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://113.198.235.232:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitCheckAccountInterface retrofitCheckAccountInterface = retrofit.create(RetrofitCheckAccountInterface.class);


        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        image2 = findViewById(R.id.imageView);

        image2.setAnimation(topAnim);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                if (LoginClient.getInstance().isKakaoTalkLoginAvailable(LoginActivity.this)) {
                    LoginClient.getInstance().loginWithKakaoTalk(LoginActivity.this, new Function2<OAuthToken, Throwable, Unit>() {
                        @Override
                        public Unit invoke(OAuthToken token, Throwable error) {
                            if (error != null) {
                                Log.e("TAG", "로그인 실패", error);
                            } else {
                                Log.d("TAG", "로그인 성공");

                                // 사용자 정보 요청
                                UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
                                    @Override
                                    public Unit invoke(User user, Throwable error) {
                                        if (error != error) {
                                            Log.e("TAG", "사용자 정보 요청 실패", error);
                                        } else {
                                            UserAccount.getInstance().setHash_key(String.valueOf(user.getId()));

                                            HashMap<String, Object> input = new HashMap<>();
                                            input.put("hash_key",UserAccount.getInstance().getHash_key());
                                            retrofitCheckAccountInterface.postCheckUserResult(input).enqueue(new Callback<CheckResult>() {

                                                @Override
                                                public void onResponse(Call<CheckResult> call, Response<CheckResult> response) {
                                                    if(response.isSuccessful()) {
                                                        CheckResult checkResult = response.body();
                                                        if(checkResult.getResult().equals("false")) {
                                                            HashMap<String, Object> input = new HashMap<>();
                                                            input.put("hash_key",UserAccount.getInstance().getHash_key().toString());
                                                            input.put("nickname",user.getKakaoAccount().getProfile().getNickname().toString());
                                                            input.put("profile_image",user.getKakaoAccount().getProfile().getProfileImageUrl().toString());
                                                            input.put("thumbnail_image",user.getKakaoAccount().getProfile().getThumbnailImageUrl().toString());
                                                            if(user.getKakaoAccount().getGender().toString() == "FEMALE")
                                                                input.put("gender",0);
                                                            else
                                                                input.put("gender",1);

                                                            retrofitCheckAccountInterface.postInsertUserResult(input).enqueue(new Callback<CheckResult>() {

                                                                @Override
                                                                public void onResponse(Call<CheckResult> call, Response<CheckResult> response) {
                                                                    Log.d("Signup","회원가입 성공");

                                                                    startActivity(i);
                                                                    finish();

                                                                }

                                                                @Override
                                                                public void onFailure(Call<CheckResult> call, Throwable t) {
                                                                    Log.d("Singup","회원가입 실패");
                                                                    t.printStackTrace();

                                                                }
                                                            });

                                                        }
                                                        else {
                                                            Log.d("Else","else");
                                                            startActivity(i);
                                                            finish();

                                                        }

                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<CheckResult> call, Throwable t) {
                                                    Log.d("issigninfail",t.getMessage());
                                                    t.getStackTrace();
                                                }
                                            });

                                        }




                                        return null;

                                    }
                                });
                            }
                            return null;
                        }
                    });
                } else {
                    LoginClient.getInstance().loginWithKakaoAccount(LoginActivity.this,  new Function2<OAuthToken, Throwable, Unit>() {
                        @Override
                        public Unit invoke(OAuthToken token, Throwable error) {
                            if (error != null) {
                                Log.e("TAG", "로그인 실패", error);
                            } else {
                                Log.d("TAG", "로그인 성공");
                                startActivity(i);
                                finish();

                                // 사용자 정보 요청
                                UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
                                    @Override
                                    public Unit invoke(User user, Throwable error) {
                                        if (error != error) {
                                            Log.e("TAG", "사용자 정보 요청 실패", error);
                                        } else {
                                            Log.i("TAG", user.toString());
                                        }
                                        return null;
                                    }
                                });
                            }
                            return null;
                        }
                    });
                }

            }
        });

    }

    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }





}