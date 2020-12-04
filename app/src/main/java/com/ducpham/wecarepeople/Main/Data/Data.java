package com.ducpham.wecarepeople.Main.Data;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ducpham.wecarepeople.model.CartItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// need interface
public class Data {

    public interface Listener {
        void getCartItemSuccess(List<CartItem> list);
    }
    static final String TAG = "Data";
    private final Listener listener;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    StorageReference mStorageRef;

    public Data(Listener listerner){
        this.db = FirebaseFirestore.getInstance();
        this.mAuth = FirebaseAuth.getInstance();
        this.listener = listerner;
    }
//asyn
    public void getCartItems(){
        final List<CartItem> list = new ArrayList<>();
        db.collection("CartItems").document(mAuth.getUid()).collection("items").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                   // Log.d(TAG,"Call Data Success");
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        // the method below did not work and I dont understand, so I replace it in traditional way
                     //   CartItem cartItem = document.toObject(CartItem.class);
                            String itemId = document.getId();
                            String name = document.get("name").toString();
                            String category = document.get("category").toString();
                            String des = document.get("description").toString();
                            String sit = document.get("situation").toString();
                            int count = Integer.parseInt(document.get("count").toString());
                            String imageUrl = document.get("url").toString();
                            String userId = document.get("userId").toString();
                            CartItem cartItem= new CartItem(userId,itemId,name,category,des,sit,count,imageUrl);
                            Log.d(TAG,"Call Data Success");
                            list.add(cartItem);
                            //Log.d(TAG,String.valueOf(list.size()));
                    }
                    listener.getCartItemSuccess(list);
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
        Log.d(TAG,String.valueOf(list.size()));
    }

    public void deleteItem(String itemId){
        db.collection("CartItems").document(mAuth.getUid())
                .collection("items").document(itemId)
                .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG,"Success");
            }
        });
        getCartItems();
    }

    public void addItem(Map<String, Object> item){
        db.collection("CartItems").document(mAuth.getUid()).collection("items")
                .add(item)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
        getCartItems();
    }


}
