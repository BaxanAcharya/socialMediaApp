package com.biplav.socialmedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class SplashActivity extends AppCompatActivity  {

    //    private TextView textView;
    private ImageView imageView;
    int initialPoint;
    ProgressBar progressBar;

    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_splash);
        imageView = findViewById(R.id.imageViewSplash);
        progressBar=findViewById(R.id.progressBar);

        Animation myanimation = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        imageView.startAnimation(myanimation);

            thread();

    }




private void thread(){
    //Using thread and halt screen for two second
    RunProBar();

    handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {


                            Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();

                            }
                        }, 2000
    );


}

    public void RunProBar() {
        initialPoint = 40;
        new Thread( new Runnable() {
            @Override
            public void run() {
                while (initialPoint < 100) {
                    initialPoint += 30;
                    handler.post( new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress( initialPoint );
                        }
                    } );
                    try {
                        Thread.sleep( 2000 );

                    } catch (InterruptedException i) {
                        i.printStackTrace();
                    }
                    if (initialPoint >= 100) {


                    }

                }

            }
        } ).start();
    }

}






