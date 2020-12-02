package com.biplav.socialmedia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.biplav.socialmedia.Api.UserApi;
import com.biplav.socialmedia.Url.Url;
import com.biplav.socialmedia.adapter.CommentAdapter;
import com.biplav.socialmedia.adapter.PostAdapter;
import com.biplav.socialmedia.model.Comment;
import com.biplav.socialmedia.model.Post;
import com.biplav.socialmedia.model.User;
import com.biplav.socialmedia.responses.LikeUnlikeResponse;
import com.biplav.socialmedia.strictMode.StrictModeClass;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.POST;

public class CommentActivity extends AppCompatActivity {

    EditText etComment;
    ImageButton btncomment;
    String post_id;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_comment);
        recyclerView=findViewById(R.id.recycleComment);
        etComment=findViewById(R.id.etComment);
        btncomment=findViewById(R.id.btncomment);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
           post_id= bundle.getString("post_id");
        }
        btncomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               saveComment();
               etComment.setText("");

            }
        });





        CommentAdapter commentAdapter=new CommentAdapter(PostAdapter.comments,CommentActivity.this);
        recyclerView.setAdapter(commentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }







    void saveComment(){
        UserApi userInterface = Url.getInstance().create(UserApi.class);
        Comment comment=new Comment(etComment.getText().toString());
        Call<LikeUnlikeResponse> commentcall=userInterface.addComment(post_id,Url.token,comment);
        commentcall.enqueue(new Callback<LikeUnlikeResponse>() {
            @Override
            public void onResponse(Call<LikeUnlikeResponse> call, Response<LikeUnlikeResponse> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(CommentActivity.this, ""+response.message(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(CommentActivity.this, ""+response.body().getStatus(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LikeUnlikeResponse> call, Throwable t) {

            }
        });
    }

}
