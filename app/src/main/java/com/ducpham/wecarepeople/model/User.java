package com.ducpham.wecarepeople.model;

import org.parceler.Parcel;

@Parcel
public class User {
    String userId;
    String name;
    String location;

    public User(){

    }
    public User(String userId,String name, String location){
        this.userId = userId;
        this.name = name;
        this.location = location;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
