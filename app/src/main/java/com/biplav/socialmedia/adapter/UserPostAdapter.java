package com.biplav.socialmedia.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.biplav.socialmedia.R;
import com.biplav.socialmedia.Url.Url;
import com.biplav.socialmedia.UserPostImageActivity;
import com.biplav.socialmedia.fragment.HomeFragment;
import com.biplav.socialmedia.model.Comment;
import com.biplav.socialmedia.model.Post;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;



public class UserPostAdapter extends RecyclerView.Adapter<UserPostAdapter.UserPostViewHolder >{

    Context context;
   private List<Post> posts;
    String imgPath;

    public UserPostAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public UserPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_post_layout, parent, false);
        return new UserPostAdapter.UserPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserPostViewHolder holder, int position) {
        Post postList = posts.get(position);
        holder.userPostName.setText("Posted by: "+ HomeFragment.loggedUsername);

        String userImagepath=HomeFragment.userImagePath;

        try {
            URL url = new URL(userImagepath);
            holder.currentUserImage.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.userPost.setText(""+postList.getCaption());
        holder.userPostDate.setText("Posted at: "+postList.getPost_date());
        String []like=postList.getLikes();

        holder.userLikeNo.setText(""+like.length);

        List<Comment> comments=postList.getComments();
        holder.userCommentNo.setText(""+comments.size());
         imgPath = Url.post_upload + postList.getImages();
        try {
            URL url = new URL(imgPath);
            holder.userPostImage.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.userPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, UserPostImageActivity.class);
                intent.putExtra("imageName",imgPath);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class UserPostViewHolder extends RecyclerView.ViewHolder{



            CircleImageView currentUserImage;
            TextView userPostName,userPostDate,userPost,userLikeNo,userCommentNo;
            ImageView userPostImage;

        public UserPostViewHolder(@NonNull View itemView) {
            super(itemView);
            currentUserImage=itemView.findViewById(R.id.current_user_image);
            userPostName=itemView.findViewById(R.id.user_post_name);
            userPostDate=itemView.findViewById(R.id.user_post_date);
            userPost=itemView.findViewById(R.id.user_post);
            userLikeNo=itemView.findViewById(R.id.user_like_no);
            userCommentNo=itemView.findViewById(R.id.user_comment_no);
            userPostImage=itemView.findViewById(R.id.user_post_image);
        }
    }
}
