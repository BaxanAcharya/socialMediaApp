package com.biplav.socialmedia.Url;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Url {

    //token generated during login is set here
    public static String token = "Bearer ";

    //backend hosted path
  // public static String backend_URL = "http://10.0.2.2:8082/";
    public static  String backend_URL = "http://192.168.100.5:8082/";

    //profile Image path
    public static String uploads = backend_URL + "proImage/";
    //post Image path
    public static String post_upload = backend_URL + "postImage/";


    public static Retrofit getInstance() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.backend_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
