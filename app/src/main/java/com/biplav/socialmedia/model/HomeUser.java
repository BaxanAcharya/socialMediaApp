package com.biplav.socialmedia.model;

public class HomeUser {
    private String username;
    private String _id;
   // private String user_id;



    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserImagePath() {
        return userImagePath;
    }

    public void setUserImagePath(String userImagePath) {
        this.userImagePath = userImagePath;
    }

    private String userImagePath;

    public HomeUser(String username, String userImagePath) {
        this.username = username;
        this.userImagePath = userImagePath;
    }
}
