package com.biplav.socialmedia.model;


public class Comment {
    private String text;
    private String created;
    private String postedBy;

    public Comment(String text, String postedBy) {
        this.text = text;
        this.postedBy = postedBy;
    }

    public Comment(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }
}
