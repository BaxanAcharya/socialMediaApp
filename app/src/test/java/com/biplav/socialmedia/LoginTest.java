package com.biplav.socialmedia;

import com.biplav.socialmedia.Url.Url;

import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;

import static com.biplav.socialmedia.Url.Url.token;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
//import static com.biplav.socialmedia.Url.Url.backend_URL;
//import static com.biplav.socialmedia.Url.Url.token;

public class LoginTest {
    @Before
    public void setUp() {
       Url.backend_URL = "http://192.168.1.5:8081/users/login/";
        Url.token="Bearer ";
    }

    @Test
    public void loginTest() {
        MainActivity mainActivity = new MainActivity();
        mainActivity.Login("baxanacharya@gmail.com", "baxan1234");
        assertThat(token, is(IsNull.notNullValue()));
    }
}