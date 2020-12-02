package com.ducpham.wecarepeople.Login;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserLoginService {

    interface Listener {

        void onLoginSuccess();

        void onLoginFail();
    }

    private static final String TAG = "UserLoginService";

    private final FirebaseAuth mAuth;
    private final Listener listener;

    public UserLoginService(FirebaseAuth mAuth, Listener listener) {
        this.mAuth = mAuth;
        this.listener = listener;
    }

    public void signIn(Activity activity, String username, String password) {
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                listener.onLoginSuccess();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            listener.onLoginFail();
                        }
                    }
                });
    }
}
