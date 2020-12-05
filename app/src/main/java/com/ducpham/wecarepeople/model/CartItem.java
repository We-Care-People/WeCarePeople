package com.ducpham.wecarepeople.model;

import org.parceler.Parcel;

@Parcel
public class CartItem {
    String userId;
    String itemId;
    String name;
    String category;
    String description;
    String situation;
    int count;
    String imageUrl;

    public CartItem(){

    }
    public CartItem(String userId, String itemId,String name, String category, String des, String situation, int count, String imageUrl){
        this.userId = userId;
        this.itemId = itemId;
        this.name = name;
        this.category = category;
        this.description = des;
        this.situation = situation;
        this.count = count;
        this.imageUrl = imageUrl;
    }
    public CartItem(String userId,String name, String category, String des, String situation, int count, String imageUrl){
        this.userId = userId;
        this.name = name;
        this.category = category;
        this.description = des;
        this.situation = situation;
        this.count = count;
        this.imageUrl = imageUrl;

    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getSituation() {
        return situation;
    }

    public int getCount() {
        return count;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getItemId(){
        return itemId;
    }
}
