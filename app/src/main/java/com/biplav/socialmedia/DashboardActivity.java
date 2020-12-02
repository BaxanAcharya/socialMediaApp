package com.biplav.socialmedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.biplav.socialmedia.fragment.HomeFragment;
import com.biplav.socialmedia.fragment.MoreFragment;
import com.biplav.socialmedia.fragment.ProfileFragment;
import com.biplav.socialmedia.fragment.RequestFragment;
import com.biplav.socialmedia.fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{


    private BottomNavigationView mBtmView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_dashboard);


        getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout,new HomeFragment()).commit();

        mBtmView=findViewById(R.id.bottom_nav);
        mBtmView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {



        Fragment selectedFragment=null;
        switch (menuItem.getItemId())
        {
            case R.id.nav_home:
                selectedFragment=new HomeFragment();
                break;
            case R.id.nav_more:
                selectedFragment=new MoreFragment();
                break;
//            case R.id.nav_notification:
//                selectedFragment=new ProfileFragment();
//                break;
            case R.id.nav_profile:
                selectedFragment=new RequestFragment();
                break;
            case R.id.nav_search:
                selectedFragment=new SearchFragment();
                break;
                default:
                    selectedFragment=new HomeFragment();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout,selectedFragment).commit();
        return true;
    }
}
