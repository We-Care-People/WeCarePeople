package com.ducpham.wecarepeople.model;

import org.parceler.Parcel;

@Parcel
public class Post {
    String postId;
    String userId;
    String userName;
    String message;
    String postTitle;

    public Post(){

    }

    public Post(String postTitle,String userName,String userId, String message){
        this(null,postTitle,userName,userId,message);
    }

    public Post(String postId, String postTitle,String userName,String userId, String message){
        this.userName = userName;
        this.postTitle = postTitle;
        this.postId = postId;
        this.userId = userId;
        this.message = message;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
