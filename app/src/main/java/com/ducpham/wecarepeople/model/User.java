package com.ducpham.wecarepeople.model;

public class User {
    String name;
    String location;

    public User(){

    }
    public User(String name, String location){
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
}
