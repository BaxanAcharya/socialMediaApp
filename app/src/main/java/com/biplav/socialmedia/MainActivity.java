package com.biplav.socialmedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.biplav.socialmedia.Api.UserApi;
import com.biplav.socialmedia.Url.Url;
import com.biplav.socialmedia.model.User;
import com.biplav.socialmedia.responses.LoginResponse;
import com.biplav.socialmedia.strictMode.StrictModeClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView register, login;
    EditText user, pass;
    RadioButton rbremember;
    String emailPhone, password;

//    public static String token = "Bearer ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_main);

        register = findViewById(R.id.textViewRegisterLogin);
        login = findViewById(R.id.textViewLogin);
        user = findViewById(R.id.editTextUser);
        pass = findViewById(R.id.editTextPass);
        rbremember = findViewById(R.id.rbRememberMe);
        chekckSharedPreferences();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                emailPhone = user.getText().toString();
                password = pass.getText().toString();
                User userArg = new User("", "", emailPhone, password, "", "", "");

                if (TextUtils.isEmpty(user.getText().toString())) {
                    user.setError("Please enter Username");
                    return;
                } else if (TextUtils.isEmpty(pass.getText().toString())) {
                    pass.setError("Please enter Password");
                    return;
                } else if (rbremember.isChecked()) {

                    saveSharedPreferences(userArg);
                                Login(emailPhone,password);

                               } else if (rbremember.isChecked() == false) {

                    UserApi userApi = Url.getInstance().create(UserApi.class);
                    Call<LoginResponse> voidCall = userApi.checkUser(userArg);
                    StrictModeClass.StrictMode();
                    voidCall.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                            if (!response.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Username or password error", Toast.LENGTH_SHORT).show();
                                return;
                            }


                            if (response.body().getCode() == 200) {
                                Url.token += response.body().getToken();
                                saveToken();

                                //System.out.println(response.body().getToken());
                                Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(MainActivity.this, response.body().getStatus(), Toast.LENGTH_SHORT).show();
                            }


                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Toast.makeText(MainActivity.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }

        });
    }

    public void Login(String emailPhone,String password){
        User userArg1 = new User("", "", emailPhone, password, "", "", "");
        UserApi userApi = Url.getInstance().create(UserApi.class);
        Call<LoginResponse> voidCall = userApi.checkUser(userArg1);
//        StrictModeClass.StrictMode();
        voidCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Username or password error", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (response.body().getCode() == 200) {
                    Url.token += response.body().getToken();
                    saveToken();

                    //System.out.println(response.body().getToken());
                    Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(MainActivity.this, response.body().getStatus(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });

    }

    private void saveToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("token", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("mytoken", Url.token);
        editor.commit();
    }

    private void chekckSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("username")) {
            user.setText("" + sharedPreferences.getString("username", ""));

        }
        if (sharedPreferences.contains("password")) {
            pass.setText("" + sharedPreferences.getString("password", ""));
        }
    }


    public void saveSharedPreferences(User u) {
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", u.getEmailPhone());
        editor.putString("password", u.getPassword());
        editor.commit();
    }

}
