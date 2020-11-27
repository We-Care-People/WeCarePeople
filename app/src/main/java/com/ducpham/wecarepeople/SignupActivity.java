package com.ducpham.wecarepeople;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.widget.Toast.LENGTH_SHORT;

public class SignupActivity extends AppCompatActivity {
    public static final String TAG = "Signup";
    EditText usernameSignUp;
    EditText passwordSignUp;
    EditText nameSignUp;
    EditText locationSignUp;
    Button signupS;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //setup signup
        usernameSignUp = findViewById(R.id.userNameSignup);
        passwordSignUp = findViewById(R.id.passwordSignup);
        nameSignUp = findViewById(R.id.nameSignup);
        locationSignUp = findViewById(R.id.location);
        signupS = findViewById(R.id.signupBtnSignup);
        mAuth = FirebaseAuth.getInstance();

        //listener for signup
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            startActivity(new Intent(this,MainActivity.class));
        }
        signupS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }

    public void signup(){
        String userName = usernameSignUp.getText().toString();
        String password = passwordSignUp.getText().toString();
        String name = nameSignUp.getText().toString();
        String location = locationSignUp.getText().toString();

        if(userName.isEmpty() || password.isEmpty() || name.isEmpty() || location.isEmpty()){
            Log.d(TAG,userName + " " + password + " " + name + " " + location);
            Toast.makeText(this,"Every field need to be filled", LENGTH_SHORT).show();
        }
        else{
            usernameSignUp.setText("");
            passwordSignUp.setText("");
            nameSignUp.setText("");
            locationSignUp.setText("");
            mAuth.createUserWithEmailAndPassword(userName, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                startActivity(new Intent(SignupActivity.this,MainActivity.class));
                                Intent intent = new Intent();
                                setResult(RESULT_OK,intent);
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignupActivity.this,"Every field need to be filled", LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });
        }

    }
}