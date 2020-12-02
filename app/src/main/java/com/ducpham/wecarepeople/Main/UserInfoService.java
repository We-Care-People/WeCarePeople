package com.ducpham.wecarepeople.Main;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.Nullable;

import com.ducpham.wecarepeople.databinding.ActivityMainBinding;
import com.ducpham.wecarepeople.model.User;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class UserInfoService {
    static final String TAG = "UserInfoService";
    DocumentReference documentReference;
    Activity activity;
    ActivityMainBinding binding;
    public UserInfoService(DocumentReference documentReference, Activity activity, ActivityMainBinding binding){
        this.documentReference = documentReference;
        this.activity = activity;
        this.binding = binding;
    }

    public User getUser(){
        final User user = new User();
        documentReference.addSnapshotListener(activity, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
               Log.d(TAG,"got info");
                user.setName(value.getString("name"));
                user.setLocation(value.getString("location"));
                //binding.name.setText(value.getString("name"));
                if(error != null){
                    Log.d(TAG,"error to get info");
                }
            }
        });
        //Log.d(TAG,user.getName());
        return user;
    }
}
