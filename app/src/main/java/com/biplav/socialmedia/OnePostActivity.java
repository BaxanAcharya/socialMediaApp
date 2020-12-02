package com.biplav.socialmedia;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class OnePostActivity extends AppCompatActivity {

    CircleImageView postUserImage;
    TextView postUsername, post,date, like ,comment;
    ImageView postImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_one_post);

        postUserImage=findViewById(R.id.OnePost_userimage);
        postUsername=findViewById(R.id.OnePost_post);
        post=findViewById(R.id.OnePost_post_name);
        date=findViewById(R.id.OnePost_date);
        like=findViewById(R.id.OnePost_like_no);
        comment=findViewById(R.id.OnePost_comment_no);
        postImage=findViewById(R.id.OnePost_post_image);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            postUsername.setText("Posted By: "+bundle.getString("username"));

            try {
                URL url = new URL(bundle.getString("OnepostImage"));
                postUserImage.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));

            } catch (Exception e) {
                e.printStackTrace();
            }
            post.setText(bundle.getString("post"));
            date.setText("Posted at: "+bundle.getString("date"));
            like.setText("Likes: "+bundle.getString("like"));
            comment.setText("Comments: "+bundle.getString("comment"));
            try {
                URL url = new URL(bundle.getString("userImage"));
                postImage.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
