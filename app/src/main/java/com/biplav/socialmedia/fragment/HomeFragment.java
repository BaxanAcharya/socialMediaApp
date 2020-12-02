package com.biplav.socialmedia.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.biplav.socialmedia.Api.UserApi;
import com.biplav.socialmedia.MainActivity;
import com.biplav.socialmedia.MapsActivity;
import com.biplav.socialmedia.R;
import com.biplav.socialmedia.Url.Url;
import com.biplav.socialmedia.adapter.PostAdapter;
import com.biplav.socialmedia.model.Post;
import com.biplav.socialmedia.model.User;
import com.biplav.socialmedia.responses.ImageResponse;
import com.biplav.socialmedia.strictMode.StrictModeClass;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.SENSOR_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public static String loggedUsername;
    public static String userImagePath;
    public static String loggedUserId;
    ConstraintLayout cons;
    String imagePath = "";
    CircleImageView circleImageView;
    ImageButton imageButton;
    EditText etpost;
    private RecyclerView recyclerViewHome;
    Button btnpost;
    public static List<Post> posts;
    String imageName;

    private SensorManager sensorManager;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        imageButton = view.findViewById(R.id.imageButton);
        recyclerViewHome = view.findViewById(R.id.recycleHome);
        circleImageView = view.findViewById(R.id.imageCircle);
        btnpost = view.findViewById(R.id.btnPost);
        cons = view.findViewById(R.id.cons);
        etpost = view.findViewById(R.id.etPost);


        sensorManager = (SensorManager) getContext().getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        SensorEventListener sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float[] values = event.values;
                String xAxis = "x: " + values[0];
                String yAxis = "y: " + values[1];
                String zAxis = "z: " + values[2];
                float x = values[0];

                if (x > 3) {
                    Url.token = "Bearer ";
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
                //System.out.println(xAxis);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        if (sensor != null) {
            sensorManager.registerListener(sensorEventListener, sensor, sensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(getContext(), "Sensor not found", Toast.LENGTH_SHORT).show();
        }


        showUserById(Url.token);


        UserApi userInterface = Url.getInstance().create(UserApi.class);
        Call<List<Post>> responseCall = userInterface.getPost(Url.token);
        StrictModeClass.StrictMode();
        responseCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "Code " + response.code(), Toast.LENGTH_SHORT).show();
                    // System.out.println(response.toString());
                    return;
                }
                posts = response.body();
                // System.out.println("Twwwts"+tweetMS.toString());

                PostAdapter postAdapter = new PostAdapter(getContext(), posts);
                recyclerViewHome.setAdapter(postAdapter);

                recyclerViewHome.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseImage();
            }
        });


        btnpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });


        return view;
    }


    //accelorometer sensor


    //open gallery
    private void BrowseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*"); //browse only image
        startActivityForResult(intent, 1);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(getContext(), "Please select an image ", Toast.LENGTH_SHORT).show();
            }
        }
        try {
            Uri uri = data.getData();
            imageButton.setImageURI(uri);
            imagePath = getRealPathFromUri(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getContext(),
                uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();
        return result;
    }


    private void saveImageOnly() {
        StrictModeClass.StrictMode();
        File file = new File(imagePath);
        Toast.makeText(getContext(), "" + imagePath, Toast.LENGTH_SHORT).show();
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("postImage",
                file.getName(), requestBody);

        UserApi userInterface = Url.getInstance().create(UserApi.class);

        Call<ImageResponse> responseCall = userInterface.uploadPost(Url.token, body);

        //Synchronous method
        try {
            Response<ImageResponse> imageResponseResponse = responseCall.execute();

            imageName = imageResponseResponse.body().getFilename();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void save() {
        saveImageOnly();

        String post = etpost.getText().toString();
        Post post1 = new Post(post, imageName);

        UserApi userApi = Url.getInstance().create(UserApi.class);
        Call<Void> voidCall = userApi.createPost(Url.token, post1);

        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Log.d("error", response.message());
                    Toast.makeText(getContext(), "" + response.message(), Toast.LENGTH_SHORT).show();
                }
                etpost.setText("");
                showUserById(Url.token);
                imageButton.setImageResource(R.drawable.ic_action_add);
                Toast.makeText(getContext(), "Posted", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("error", t.getLocalizedMessage());
            }
        });

    }


    private void showUserById(String token) {
        UserApi userInterface = Url.getInstance().create(UserApi.class);
        Call<User> userCall = userInterface.getUser(token);
        StrictModeClass.StrictMode();
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                etpost.setHint("What's up " + response.body().getFirstName() + " ?");
                loggedUsername = response.body().getFirstName() + " " + response.body().getLastName();
                loggedUserId=response.body().get_id();

                String imagepath = Url.uploads + response.body().getImage();
                userImagePath = Url.uploads + response.body().getImage();
                try {

                    URL url = new URL(imagepath);

                    Bitmap imageBitmap = BitmapFactory.decodeStream((InputStream) url.getContent());

                    circleImageView.setImageBitmap(imageBitmap);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });

    }


}
