package com.ducpham.wecarepeople.Signup;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ducpham.wecarepeople.Main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static android.widget.Toast.LENGTH_SHORT;

public class UserSignupService {

    public static final String TAG = "UserSignupService";
    FirebaseAuth mAuth;
    Activity activity;
    FirebaseFirestore db;
    String userId;
    public  UserSignupService(FirebaseAuth mAuth, Activity activity, FirebaseFirestore db){
        this.mAuth = mAuth;
        this.activity = activity;
        this.db = db;
    }

    public void signup(String username, String password,final String name, final String location){
        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            DocumentReference documentReference = db.collection("Users").document(user.getUid());
                            Log.d(TAG,documentReference.getId());
                            userId = user.getUid();
                            Map<String, Object> tempUser = new HashMap<>();
                            tempUser.put("name", name);
                            tempUser.put("location", location);
                            createPerson(documentReference,tempUser);
                            activity.startActivity(new Intent(activity, MainActivity.class));
                            Intent intent = new Intent();
                            activity.setResult(RESULT_OK,intent);
                            activity.finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(activity,"Every field need to be filled", LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void createPerson(DocumentReference documentReference,Map<String, Object> user){
        documentReference
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, userId);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
}
