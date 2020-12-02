package com.biplav.socialmedia;

import android.net.Uri;

import com.biplav.socialmedia.Url.Url;
import com.biplav.socialmedia.fragment.HomeFragment;

import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.biplav.socialmedia.Url.Url.token;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UploadImage {

    String uri;


    @Before
    public void setUp() {
         uri="/storage/emulated/0/DCIM/Camera/IMG_20200121_002509.jpg";

    }

    @Test
    public void loginTest() {
        RegisterActivity registerActivity=new RegisterActivity();
        registerActivity.saveImageOnly(uri);
    }
}
