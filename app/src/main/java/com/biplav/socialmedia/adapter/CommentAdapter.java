package com.biplav.socialmedia.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.biplav.socialmedia.Api.UserApi;
import com.biplav.socialmedia.R;
import com.biplav.socialmedia.Url.Url;
import com.biplav.socialmedia.fragment.HomeFragment;
import com.biplav.socialmedia.model.Comment;
import com.biplav.socialmedia.model.Post;
import com.biplav.socialmedia.model.User;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{

    List<Comment> comments;
    Context context;

    public CommentAdapter(List<Comment> comments, Context context) {
        this.comments = comments;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        final Comment comment=comments.get(position);
       // holder
        commentdate.setText(comment.getCreated());
        commentnow.setText(comment.getText());
//        commentdelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println("logged user id "+HomeFragment.loggedUserId);
//                System.out.println("Commented by id"+comment.getPostedBy());
//                if (HomeFragment.loggedUserId==comment.getPostedBy()){
//                    Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(context, "You cannot delete this comment", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        });


        String postedId=comment.getPostedBy();
        UserApi userInterface = Url.getInstance().create(UserApi.class);
        Call<User> responseCall = userInterface.getUserById(postedId,Url.token);
        responseCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(context, ""+response.message(), Toast.LENGTH_SHORT).show();
                }else {
                    commentby.setText(""+response.body().getFirstName()+response.body().getLastName());
                    String image = Url.uploads + response.body().getImage();
                    if (response.body().getImage() == null) {
                        commentbyimage.setImageResource(R.drawable.no_image);
                    }else {
                        try {

                            URL url = new URL(image);

                            Bitmap imageBitmap = BitmapFactory.decodeStream((InputStream) url.getContent());

                           commentbyimage.setImageBitmap(imageBitmap);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    CircleImageView commentbyimage;
    TextView commentby,commentdate,commentnow;
    ImageButton commentdelete,commentedit;

    public class CommentViewHolder extends RecyclerView.ViewHolder{

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            commentbyimage=itemView.findViewById(R.id.commentByImage);
            commentby=itemView.findViewById(R.id.commentByName);
            commentdate=itemView.findViewById(R.id.commentDate);
            commentnow=itemView.findViewById(R.id.comment);
            //commentdelete=itemView.findViewById(R.id.commentDelete);
//
        }
    }
}
