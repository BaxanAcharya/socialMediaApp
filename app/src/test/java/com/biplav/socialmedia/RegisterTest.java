package com.biplav.socialmedia;

//import com.biplav.socialmedia.Url.Url;

import com.biplav.socialmedia.Url.Url;
import com.biplav.socialmedia.bll.RegisterBll;
import com.biplav.socialmedia.model.User;

import org.junit.Before;
import org.junit.Test;


public class RegisterTest {
    @Before
    public void setUp() {
        //Url.backend_URL = "http://192.168.1.5:8081/users/login/";
    }

    @Test
    public void RegisterTest() {
        RegisterActivity registerActivity = new RegisterActivity();
        User user=new User("Loke", "Niranjan", "loken@gmail.com", "loken", "", "2017/02/17", "Male");
        RegisterBll registerBll=new RegisterBll();

    }
}
