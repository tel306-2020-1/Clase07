package com.example.clase07.entity;

import androidx.annotation.NonNull;

public class Comment {

    private int id;
    private String body;
    private int postId;

    @NonNull
    @Override
    public String toString() {
        return id + " / " + body + " / postId: " + postId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
}
