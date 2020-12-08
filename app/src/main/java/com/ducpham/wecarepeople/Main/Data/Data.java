package com.ducpham.wecarepeople.Main.Data;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ducpham.wecarepeople.model.CartItem;
import com.ducpham.wecarepeople.model.Message;
import com.ducpham.wecarepeople.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

// need interface
public class Data {

    public interface Listener {
        void getCartItemSuccess(List<CartItem> list);

        void getUserSuccess(List<User> list);

        void getMessageSuccess(List<Message> list);
    }
    static final String TAG = "Data";
    private final Listener listener;
    FirebaseFirestore db;
    FirebaseAuth mAuth;

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
                            String imageUrl = document.get("imageUrl").toString();
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
    }

    public void deleteItem(String itemId){
        db.collection("CartItems").document(mAuth.getUid())
                .collection("items").document(itemId)
                .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG,"Success");
                getCartItems();
            }
        });

    }

    public void addItem(CartItem item){

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

    public void getUsers(){
        final List<User> list = new ArrayList<>();
        db.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        list.add(new User(document.getId(),document.get("name").toString(),document.get("location").toString()));
                    }
                    listener.getUserSuccess(list);
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public void sendMessage(final Message message){
        db.collection("Chat")
                .document(message.getSender())
                .collection("chat")
                .document(message.getReceiver())
                .collection("detail")
                .add(message).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Log.d(TAG,"Success");
                getMessage(message.getSender(),message.getReceiver());
            }
        });
        db.collection("Chat")
                .document(message.getReceiver())
                .collection("chat")
                .document(message.getSender())
                .collection("detail")
                .add(message).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Log.d(TAG,"Success");
            }
        });
    }

    public void getMessage(final String curUserId, final String conUserId){
        final List<Message> list = new ArrayList<>();
        db.collection("Chat").document(curUserId)
                .collection("chat")
                .document(conUserId)
                .collection("detail").orderBy("date")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        list.add(new Message(document.getId()
                                ,document.get("sender").toString()
                                ,document.get("receiver").toString()
                                ,document.get("message").toString()
                                ,document.getDate("date")));
                    }
                    Log.d(TAG,curUserId);
                    Log.d(TAG,conUserId);
                    Log.d(TAG,String.valueOf(list.size()));
                    listener.getMessageSuccess(list);
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }


}
