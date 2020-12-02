package com.biplav.socialmediawear.Url;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Url {
    public static String token = "Bearer ";

    //backend hosted path
    public static String backend_URL = "http://10.0.2.2:8081/";

    public static Retrofit getInstance() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.backend_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
