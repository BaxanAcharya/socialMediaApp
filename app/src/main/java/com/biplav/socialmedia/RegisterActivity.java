package com.biplav.socialmedia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.biplav.socialmedia.Api.UserApi;
import com.biplav.socialmedia.Url.Url;
import com.biplav.socialmedia.bll.RegisterBll;
import com.biplav.socialmedia.broadcast.BroadcastReciverWifi;
import com.biplav.socialmedia.model.User;
import com.biplav.socialmedia.responses.ImageResponse;
import com.biplav.socialmedia.notification.CreateChannel;

import java.io.File;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    private TextView register, login, date;
    private EditText fname, lname, password, email;
    private RadioButton male, female, others;
    private ImageView imageUpload;
    private String dob;
    private int counter = 0;
    String imageName;
    private NotificationManagerCompat notificationManagerCompat;
    //wifi connection
    BroadcastReciverWifi broadcastReciverWifi = new BroadcastReciverWifi();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        removeTitleBarAndFullScreen();
        setContentView(R.layout.activity_register);

        notificationManagerCompat = NotificationManagerCompat.from(this);
        CreateChannel createChannel = new CreateChannel(this);
        createChannel.createChannel();
        register = findViewById(R.id.textViewRegister);
        imageUpload = findViewById(R.id.ImageUpload);


        // String image_gallery=getIntent().getString("imageGallery");
        // String path=getIntent().getStringExtra("path");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImageOnly(String.valueOf(PopupActivity.imgPath));

                String firstname = fname.getText().toString();
                final String lastname = lname.getText().toString();
                final String emailPhone = email.getText().toString();
                final String passwordHere = password.getText().toString();
                String dateHere = date.getText().toString();
                String genderHere = null;
                if (male.isChecked()) {
                    genderHere = "Male";
                } else if (female.isChecked()) {
                    genderHere = "Female";
                } else if (others.isChecked()) {
                    genderHere = "Others";
                }
                User user = new User(firstname, lastname, emailPhone, passwordHere, imageName, dateHere, genderHere);

                save(user);


            }
        });


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Bitmap image_camera = bundle.getParcelable("image");



            if (image_camera != null) {
                imageUpload.setImageBitmap(image_camera);
            }

        }else if (PopupActivity.imgPath!=null){
            imageUpload.setImageURI(PopupActivity.imgPath);
        }


        //if (extras.getBi);


        checkPermission();
        imageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, PopupActivity.class);
                startActivity(intent);
            }
        });

        login = findViewById(R.id.textViewLoginRegister);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });


        date = findViewById(R.id.textViewDateRegister);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDatePicker();
            }
        });

        fname = findViewById(R.id.etfname);
        lname = findViewById(R.id.etlname);
        password = findViewById(R.id.etpasswordRegister);
        email = findViewById(R.id.etemailRegister);

        male = findViewById(R.id.radioButtonMale);
        female = findViewById(R.id.radioButtonFemale);
        others = findViewById(R.id.radioButtonOthers);

    }


    //save image only


    public void saveImageOnly(String uri) {
        File file = new File(uri);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("proImage",
                file.getName(), requestBody
        );

        UserApi userInterface = Url.getInstance().create(UserApi.class);

        Call<ImageResponse> responseCall = userInterface.uploadImage(body);
       // StrictModeClass.StrictMode();

        try {
            Response<ImageResponse> imageResponseResponse = responseCall.execute();

             imageName = imageResponseResponse.body().getFilename();
            System.out.println(imageName);
            Toast.makeText(this, "Image uploaded " + imageName, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //users/register api used
    public void save(User user) {
        RegisterBll registerBll=new RegisterBll();
        registerBll.registerUser(user);
        if (registerBll.registerUser(user)){
            DisplayPopUpNotification("Registration", "Account registered. You cannot login until account is verified ");
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            //Toast.makeText(RegisterActivity.this, response.message() , Toast.LENGTH_LONG).show();
        }


//        UserApi userApi = Url.getInstance().create(UserApi.class);
//        Call<Void> voidCall = userApi.registerUser(user);
//
//        voidCall.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                if (!response.isSuccessful()) {
//                    Log.d("error", response.message());
//                    Toast.makeText(RegisterActivity.this, ""+response.message(), Toast.LENGTH_SHORT).show();
//                }
//
//

//
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                Log.d("error", t.getLocalizedMessage());
//            }
//        });


    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0
            );
        }
    }


    public void removeTitleBarAndFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
    }


    //this will show popup notification
    public void DisplayPopUpNotification(String message, String content) {
        Notification notification = new NotificationCompat.Builder(this, CreateChannel.CHANNEL_1)
                .setSmallIcon(R.drawable.ic_notification_black)
                .setContentTitle(message)
                .setContentText(content)
                .setCategory(NotificationCompat.CATEGORY_PROGRESS)
                .build();
        counter = counter + 1;

        notificationManagerCompat.notify(counter, notification);
    }


    //open calender
    private void loadDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, year, month, day);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        dob = month + "/" + dayOfMonth + "/" + year;
        date.setText(dob);
    }


    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(broadcastReciverWifi, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReciverWifi);
    }
}
