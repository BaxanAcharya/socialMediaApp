package com.biplav.socialmedia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class PopupActivity extends Activity {

    ImageView imgGallery,imgCamera;
    public static Uri imgPath, imageName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        imgCamera=findViewById(R.id.imgCamera);
        imgGallery=findViewById(R.id.imgGallery);

        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //height and width for window
        int width=displayMetrics.widthPixels;
        int height=displayMetrics.heightPixels;
        getWindow().setLayout((int)(width*.7),(int)(height*.3));

        WindowManager.LayoutParams params=getWindow().getAttributes();
        //position of window
        params.gravity= Gravity.CENTER;

//        params.x=-20;
//        params.y=-780;
        getWindow().setAttributes(params);


        //open gallery
        imgGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseImage();
            }
        });

            //open camera
        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCamera();
//                finish();
            }
        });

    }


    //open gallery
    private void BrowseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*"); //browse only image
        startActivityForResult(intent, 1);
    }




    //get path of the image uploaded
    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), uri, projection, null, null, null
        );
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();
        return result;

    }



    //to load camera

    private void loadCamera()
    {
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(intent,0);
        }
    }

    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode==RESULT_OK)
        {
            Bundle extras=data.getExtras();
            Bitmap imageBitmap= (Bitmap) extras.get("data");
            Intent intent=new Intent(PopupActivity.this,RegisterActivity.class);
            intent.putExtra("image", imageBitmap);
            startActivity(intent);
            finish();
        }else if(requestCode==1 && resultCode==RESULT_OK){
            if (data == null&& data.getData() != null) {
                Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            }
            Uri uri = data.getData();
            imgPath= Uri.parse(getRealPathFromUri(uri));
            finish();
        }
    }

}
