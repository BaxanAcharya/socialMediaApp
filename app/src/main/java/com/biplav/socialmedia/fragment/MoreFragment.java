package com.biplav.socialmedia.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.biplav.socialmedia.MainActivity;
import com.biplav.socialmedia.MapsActivity;
import com.biplav.socialmedia.R;
import com.biplav.socialmedia.Url.Url;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment {

    TextView texViewLogout, tvAboutUs;

    public MoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_more, container, false);
        tvAboutUs=view.findViewById(R.id.tvAboutUs);
        texViewLogout = view.findViewById(R.id.tvSettings);


        tvAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
        texViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Url.token="Bearer ";
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });




        return view;
    }

}
