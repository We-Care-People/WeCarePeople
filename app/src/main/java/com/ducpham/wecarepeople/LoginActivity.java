package com.ducpham.wecarepeople;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 321;
    public static final String TAG = "LoginActivity";
    EditText username;
    EditText password;
    Button siginBtn;
    TextView signup;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //set up sign in
        username = findViewById(R.id.userNameLogin);
        password = findViewById(R.id.passwordLogin);
        siginBtn = findViewById(R.id.signinBtn);
        signup = findViewById(R.id.signupBtn);
        mAuth = FirebaseAuth.getInstance();

        //Sign in activity
        siginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupActivity();
            }
        });

        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            startActivity(new Intent(this,MainActivity.class));
        }
    }

    //Click signin btn
    public void signin(){
        String usernameS = username.getText().toString();
        String passwordS = password.getText().toString();
        if(usernameS.isEmpty() || passwordS.isEmpty()){
            Toast.makeText(LoginActivity.this,"Please put in your email and password", Toast.LENGTH_SHORT).show();
        }
        Task<AuthResult> authResultTask = mAuth.signInWithEmailAndPassword(usernameS, passwordS)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user != null){
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    //CLick signup btn
    public void signupActivity(){
        Intent intent = new Intent(this,SignupActivity.class);
        startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(TAG,"start");
        if(requestCode == REQUEST_CODE){
            Toast.makeText(LoginActivity.this,"Signup Fail", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}