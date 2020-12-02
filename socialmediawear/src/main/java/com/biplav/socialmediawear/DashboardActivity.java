package com.biplav.socialmediawear;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.biplav.socialmediawear.Api.UserApi;
import com.biplav.socialmediawear.Url.Url;
import com.biplav.socialmediawear.model.LoginResponse;
import com.biplav.socialmediawear.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.POST;

public class DashboardActivity extends WearableActivity {

    TextView no;
    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        no=findViewById(R.id.tvno);
        logout=findViewById(R.id.btnlogout);


        UserApi userApi = Url.getInstance().create(UserApi.class);
        Call<List<Post>> voidCall = userApi.getPost(Url.token);
        voidCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(DashboardActivity.this, ""+response.message(), Toast.LENGTH_SHORT).show();
                }else {
                    List<Post> postList=response.body();
                    System.out.println("No of posts"+postList.size());
                    no.setText("No of posts : "+postList.size());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                System.out.println(t.getLocalizedMessage());
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Url.token="Bearer ";
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
