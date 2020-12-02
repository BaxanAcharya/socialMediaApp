package com.biplav.socialmediawear.Api;

import com.biplav.socialmediawear.model.LoginResponse;
import com.biplav.socialmediawear.model.Post;
import com.biplav.socialmediawear.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserApi {
    //login
    @Headers("Content-Type: application/json")
    @POST("users/login")
    Call<LoginResponse> checkUser(@Body User user);

    @GET("posts/postList/")
    Call<List<Post>> getPost(@Header("Authorization") String token);
}
