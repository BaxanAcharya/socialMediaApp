package com.biplav.socialmedia.fragment;


import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.biplav.socialmedia.Api.UserApi;
import com.biplav.socialmedia.R;
import com.biplav.socialmedia.Url.Url;
import com.biplav.socialmedia.model.Post;
import com.biplav.socialmedia.model.User;
import com.biplav.socialmedia.strictMode.StrictModeClass;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.SENSOR_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    AutoCompleteTextView autoCompleteTextView;
    private SensorManager sensorManager;
    Context context;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_search, container, false);
        sensorManager= (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        Sensor sensor=sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        SensorEventListener sensorEventListener=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.values[0]<=2)
                {
                   getActivity().getWindow().getDecorView().setBackgroundColor(Color.BLACK);
                }else {

                    getActivity().getWindow().getDecorView().setBackgroundColor(Color.WHITE);
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


        fillArrayOfUsers();
        autoCompleteTextView=view.findViewById(R.id.etSearchName);

        return view;
    }


    private void fillArrayOfUsers(){
        StrictModeClass.StrictMode();
        UserApi userInterface = Url.getInstance().create(UserApi.class);
        Call<List<User>> responseCall = userInterface.getUserList(Url.token);
        // progress dialogbox start
            responseCall.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    // progress dialogbox end/
                    if (!response.isSuccessful()){
                        Toast.makeText(getActivity(), ""+response.message(), Toast.LENGTH_SHORT).show();
                    }else {

                        List<User> userList=response.body();
                            //loading dialog box

                        String [] data=new String[userList.size()];

                        for (int i=0; i<data.length; i++){
                            User user = userList.get(i);
                            data[i]=user.getEmailPhone();
                            System.out.println(data[i]);
                        }

                        ArrayAdapter<String> adapter=new ArrayAdapter<>(
                                getActivity(),
                                android.R.layout.simple_list_item_1,
                                data
                        );
                        autoCompleteTextView.setAdapter(adapter); //marker values in autocompletetextview
                        autoCompleteTextView.setThreshold(1);

                    }
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    System.out.println("error"+t.getMessage());
                    Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }

}
