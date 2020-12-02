package com.biplav.socialmedia.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.biplav.socialmedia.R;
import com.biplav.socialmedia.model.Request;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {

    static List<Request> requestList = new ArrayList<>();

    public RequestAdapter(List<Request> requestList) {
        this.requestList = requestList;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_layout, parent, false);
        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {

        Request request=new Request();
        request=requestList.get(position);

        holder.circleImageView.setImageResource(request.getImageId());
        holder.username.setText(request.getUsername());

    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public class RequestViewHolder extends RecyclerView.ViewHolder {

        CircleImageView circleImageView;
        TextView username, follow;
        ImageButton followbtn;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.imageViewRequest);
            username = itemView.findViewById(R.id.usernameRequest);
            follow = itemView.findViewById(R.id.followRequest);
            followbtn = itemView.findViewById(R.id.btnfollowRequest);
        }
    }
}
