package com.biplav.socialmedia.Api;

import com.biplav.socialmedia.model.Comment;
import com.biplav.socialmedia.model.Post;
import com.biplav.socialmedia.model.User;
import com.biplav.socialmedia.responses.DeleteResponse;
import com.biplav.socialmedia.responses.ImageResponse;
import com.biplav.socialmedia.responses.LikeUnlikeResponse;
import com.biplav.socialmedia.responses.LoginResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserApi {
    //create user
    @POST("users/register")
    Call<Void> registerUser(@Body User user);

    //create post
    @POST("posts/add")
    Call<Void> createPost(@Header("Authorization") String token,@Body Post post);

    //imageupload
    @Multipart
    @POST("uploadProfile")
    Call<ImageResponse> uploadImage(@Part MultipartBody.Part img);

    //imageupload
    @Multipart
    @POST("uploadPost")
    Call<ImageResponse> uploadPost(@Header("Authorization") String token, @Part MultipartBody.Part postImage);

    @GET("posts/postList/")
    Call<List<Post>> getPost(@Header("Authorization") String token);

    //login
    @Headers("Content-Type: application/json")
    @POST("users/login")
    Call<LoginResponse> checkUser(@Body User user);

    //userPosts
    @GET("posts/currentUserPosts")
    Call<List<Post>> getCurrentUserPosts(@Header("Authorization") String token);

    //add comment
    @PUT("/posts/comment/{id}")
    Call<LikeUnlikeResponse> addComment(@Path("id") String id, @Header("Authorization") String token, @Body Comment c);

    //userByID
    @GET("/users/userById/{id}")
    Call<User> getUserById(@Path("id") String id,@Header("Authorization") String token);

    //like and unlike
    @PUT("posts/likedislike/{id}")
    Call<LikeUnlikeResponse> likeUnlike(@Path("id") String id, @Header("Authorization") String token);

    //delete post
    @DELETE("posts/{id}")
    Call<DeleteResponse> deletePost(@Path("id") String id, @Header("Authorization") String token);

    //UserList
    @GET("users/userList/")
    Call<List<User>> getUserList(@Header("Authorization") String token);

    //userProfile
    @GET("users/me")
    Call<User> getUser(@Header("Authorization") String token);

}
