package com.biplav.socialmedia.adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.biplav.socialmedia.Api.UserApi;
import com.biplav.socialmedia.CommentActivity;
import com.biplav.socialmedia.R;
import com.biplav.socialmedia.Url.Url;
import com.biplav.socialmedia.UserPostImageActivity;
import com.biplav.socialmedia.model.Comment;
import com.biplav.socialmedia.model.HomeUser;
import com.biplav.socialmedia.model.Post;
import com.biplav.socialmedia.model.User;
import com.biplav.socialmedia.responses.DeleteResponse;
import com.biplav.socialmedia.responses.LikeUnlikeResponse;
import com.biplav.socialmedia.strictMode.StrictModeClass;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
   public static Context context;
    String user_id;
    String post_username;
     List<Post> dataSetList;
     public static List<Comment> comments;
    AlertDialog.Builder builder;

    String username, imgPath, date, post, postUser_image, post_id, post_user_id;

    HomeUser homeUser;

    public PostAdapter(Context context, List<Post> dataSetList) {
        this.context = context;
        this.dataSetList = dataSetList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.posts_layout, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PostViewHolder holder, final int position) {
        final Post tm = dataSetList.get(position);
        post_id=tm.get_id();

        user_id = tm.getUser_id();
        System.out.println("Post user"+user_id);

        UserApi userInterface = Url.getInstance().create(UserApi.class);
        Call<User> userCall = userInterface.getUser(Url.token);
        StrictModeClass.StrictMode();
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    //Toast.makeText(getActivity(), "Code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    getUserById();
                    username = homeUser.getUsername();
                    holder.name.setText("" + homeUser.getUsername());

                    postUser_image = Url.uploads + homeUser.getUserImagePath();
                    if (homeUser.getUserImagePath() == null) {
                        holder.imageView.setImageResource(R.drawable.no_image);
                    } else {
                        try {

                            URL url = new URL(postUser_image);

                            Bitmap imageBitmap = BitmapFactory.decodeStream((InputStream) url.getContent());

                            holder.imageView.setImageBitmap(imageBitmap);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        date = String.valueOf(tm.getPost_date());
        holder.date.setText("" + tm.getPost_date());
        post = tm.getCaption();
        holder.post.setText(tm.getCaption());




        final String[] like = tm.getLikes();



        holder.like.setText("" + like.length);

        holder.btnlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LikeUnlike(position);
            }
        });


        holder.deletepost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    deleteAlert();

            }
        });

      comments = tm.getComments();
        holder.dislike.setText("" + comments.size());

        holder.btncomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, CommentActivity.class);
                intent.putExtra("post_id",tm.get_id());
                context.startActivity(intent);
            }
        });

        imgPath = Url.post_upload + tm.getImages();
        if (tm.getImages() == null) {
            holder.imageViewImage.setImageResource(R.drawable.no_image);
        } else {
            try {
                URL url = new URL(imgPath);
                holder.imageViewImage.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
                holder.imageViewImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context, UserPostImageActivity.class);
                        intent.putExtra("imageName", postUser_image);
                        context.startActivity(intent);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    public void LikeUnlike(final int position){
        UserApi userInterface = Url.getInstance().create(UserApi.class);
        Call<LikeUnlikeResponse> userCall = userInterface.likeUnlike(post_id,Url.token);
        userCall.enqueue(new Callback<LikeUnlikeResponse>() {
            @Override
            public void onResponse(Call<LikeUnlikeResponse> call, Response<LikeUnlikeResponse> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(context, ""+response.body(), Toast.LENGTH_SHORT).show();
                }else {
                    if (response.body().getStatus()=="Deleted"){
                        Toast.makeText(context, ""+response.body().getStatus(), Toast.LENGTH_SHORT).show();
                        dataSetList.remove(position);
                    }
                    else {
                        Toast.makeText(context, ""+response.body().getStatus(), Toast.LENGTH_SHORT).show();
                    }
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<LikeUnlikeResponse> call, Throwable t) {

                Toast.makeText(context, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void deleteAlert(){
        //showing alert if delete clicked
        builder = new AlertDialog.Builder(context);


        builder.setMessage("Do you want to delete this post ").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StrictModeClass.StrictMode();
                        UserApi userInterface = Url.getInstance().create(UserApi.class);
                        Call<DeleteResponse> deletepost=userInterface.deletePost(post_id,Url.token);
                        deletepost.enqueue(new Callback<DeleteResponse>() {
                            @Override
                            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                                if (!response.isSuccessful()){
                                    Toast.makeText(context, ""+response.message(), Toast.LENGTH_SHORT).show();
                                }else {

                                    Toast.makeText(context, ""+response.body().getStatus(), Toast.LENGTH_SHORT).show();
                                    PostAdapter.this.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                                Toast.makeText(context, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });

        AlertDialog alert = builder.create();
        alert.show();

    }







    public HomeUser getUserById() {
        StrictModeClass.StrictMode();
        UserApi userInterface = Url.getInstance().create(UserApi.class);
        Call<User> userCall = userInterface.getUserById(user_id, Url.token);
        try {
            Response<User> userResponse = userCall.execute();
            if (userResponse.isSuccessful()) {
                post_username = userResponse.body().getFirstName() + " " + userResponse.body().getLastName();
              //  System.out.println(post_username);
                post_user_id=userResponse.body().get_id();

                System.out.println("Home user"+post_user_id);
                String userImagePath = userResponse.body().getImage();
               // System.out.println(userImagePath);
                homeUser = new HomeUser(post_username, userImagePath);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return homeUser;
    }


    @Override
    public int getItemCount() {
        return dataSetList.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imageView;
        ImageView imageViewImage;
        TextView name, post, like, dislike, date;
        ImageView btnlike,btncomment;
        ImageButton deletepost,editpost;


        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.userimage);
            name = itemView.findViewById(R.id.post_name);
            post = itemView.findViewById(R.id.post);
            imageViewImage = itemView.findViewById(R.id.post_image);
            like = itemView.findViewById(R.id.like_no);
            dislike = itemView.findViewById(R.id.dislike_no);
            date = itemView.findViewById(R.id.date);
            btnlike=itemView.findViewById(R.id.imageViewLike);
            btncomment=itemView.findViewById(R.id.imageViewComment);
            deletepost=itemView.findViewById(R.id.btndeletepost);
//            editpost=itemView.findViewById(R.id.btneditpost);
        }
    }
}
