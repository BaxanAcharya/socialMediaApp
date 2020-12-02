package com.biplav.socialmediawear;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.biplav.socialmediawear.Api.UserApi;
import com.biplav.socialmediawear.Url.Url;
import com.biplav.socialmediawear.model.LoginResponse;
import com.biplav.socialmediawear.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends WearableActivity {

    private EditText etuser, etpass;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etuser = findViewById(R.id.etemail);
        etpass = findViewById(R.id.etpass);
        btnLogin = findViewById(R.id.btnlogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=etuser.getText().toString();
                String password=etpass.getText().toString();
                User user=new User(username,password);

                UserApi userApi = Url.getInstance().create(UserApi.class);
                Call<LoginResponse> voidCall = userApi.checkUser(user);
                voidCall.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Username or password dont match", Toast.LENGTH_SHORT).show();
                        } else {
                            Url.token+=response.body().getToken();
                            Intent intent=new Intent(getApplicationContext(),DashboardActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(MainActivity.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // Enables Always-on
        setAmbientEnabled();
    }
}
