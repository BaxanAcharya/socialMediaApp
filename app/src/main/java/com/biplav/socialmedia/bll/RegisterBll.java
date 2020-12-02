package com.biplav.socialmedia.bll;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.biplav.socialmedia.Api.UserApi;
import com.biplav.socialmedia.MainActivity;
import com.biplav.socialmedia.RegisterActivity;
import com.biplav.socialmedia.Url.Url;
import com.biplav.socialmedia.model.User;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterBll {
    boolean isSuccess = false;
    public boolean registerUser(User user){

        UserApi userApi = Url.getInstance().create(UserApi.class);
        Call<Void> voidCall = userApi.registerUser(user);

        try {
            Response<Void> loginResponse = voidCall.execute();
            if (loginResponse.isSuccessful()) {
                isSuccess = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return  isSuccess;
    }
}
