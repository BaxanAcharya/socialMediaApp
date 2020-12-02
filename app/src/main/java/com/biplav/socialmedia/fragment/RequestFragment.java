package com.biplav.socialmedia.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.biplav.socialmedia.Api.UserApi;
import com.biplav.socialmedia.MapsActivity;
import com.biplav.socialmedia.R;
import com.biplav.socialmedia.Url.Url;
import com.biplav.socialmedia.UserPostImageActivity;
import com.biplav.socialmedia.adapter.UserPostAdapter;
import com.biplav.socialmedia.model.Post;
import com.biplav.socialmedia.model.User;
import com.biplav.socialmedia.strictMode.StrictModeClass;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.SENSOR_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestFragment extends Fragment {

    CircleImageView circleImageViewProfile;
    RecyclerView recyclerView;
    TextView profileName, profileEmail, profileDOB;

    private SensorManager sensorManager;


    public RequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_request, container, false);

        circleImageViewProfile = view.findViewById(R.id.imageViewProfile);
        profileName = view.findViewById(R.id.profileName);
        profileEmail = view.findViewById(R.id.profileEmail);
        recyclerView=view.findViewById(R.id.recycleViewProfile);
        profileDOB = view.findViewById(R.id.profileDOb);
        sensorManager= (SensorManager) getContext().getSystemService(SENSOR_SERVICE);
        Sensor sensor=sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);




        SensorEventListener sensorEventListener=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if(event.values[2] > 0.5f) {
                    Intent intent=new Intent(getActivity(), MapsActivity.class);
                    startActivity(intent);
                }
                }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        if (sensor!=null){
            sensorManager.registerListener(sensorEventListener,sensor,sensorManager.SENSOR_DELAY_NORMAL);
        }else {
            Toast.makeText(getContext(), "Sensor not found", Toast.LENGTH_SHORT).show();
        }




        getUserProfile();
        getUserPosts();

        return view;
    }

    private void getUserProfile() {
        StrictModeClass.StrictMode();
        final UserApi userApi = Url.getInstance().create(UserApi.class);
        Call<User> responseCall = userApi.getUser(Url.token);



        responseCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getContext(), ""+response.message(), Toast.LENGTH_SHORT).show();
                }else {

                    profileName.setText(""+response.body().getFirstName()+"    "+response.body().getLastName());
                    profileEmail.setText(""+response.body().getEmailPhone());
                    profileDOB.setText("Born on: "+ response.body().getDob());
                    final String imagePath=Url.uploads+response.body().getImage();
                    try {

                        URL url = new URL(imagePath);

                        Bitmap imageBitmap = BitmapFactory.decodeStream((InputStream) url.getContent());

                        circleImageViewProfile.setImageBitmap(imageBitmap);
                        circleImageViewProfile.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(getContext(), UserPostImageActivity.class);
                                intent.putExtra("imageName",imagePath);
                                startActivity(intent);
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void getUserPosts(){
        StrictModeClass.StrictMode();
        UserApi userInterface = Url.getInstance().create(UserApi.class);
        Call<List<Post>> responseCall = userInterface.getCurrentUserPosts(Url.token);

        responseCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText( getContext(), "Code " + response.code(), Toast.LENGTH_SHORT ).show();
                    // System.out.println(response.toString());
                    return;
                }
                List<Post> posts = response.body();
                // System.out.println("Twwwts"+tweetMS.toString());

                UserPostAdapter postAdapter = new UserPostAdapter(getContext(), posts );
                recyclerView.setAdapter( postAdapter );
                recyclerView.setLayoutManager( new LinearLayoutManager(getContext()) );
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(getContext(), ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
