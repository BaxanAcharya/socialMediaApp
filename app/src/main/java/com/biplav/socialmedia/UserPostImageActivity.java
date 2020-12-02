package com.biplav.socialmedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class UserPostImageActivity extends AppCompatActivity {

    ImageView imageViewSingleImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_post_image);
        imageViewSingleImage = findViewById(R.id.singlePostImage);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String image = bundle.getString("imageName");
            try {
                URL url = new URL(image);
                imageViewSingleImage.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }
}
