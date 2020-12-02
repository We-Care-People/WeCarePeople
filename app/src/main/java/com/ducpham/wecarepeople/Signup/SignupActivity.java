package com.ducpham.wecarepeople.Signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ducpham.wecarepeople.Main.MainActivity;
import com.ducpham.wecarepeople.R;
import com.ducpham.wecarepeople.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.widget.Toast.LENGTH_SHORT;

public class SignupActivity extends AppCompatActivity {
    public static final String TAG = "Signup";
    private FirebaseAuth mAuth;
    private ActivitySignupBinding binding;
    private FirebaseFirestore db;

    private UserSignupService userSignupService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //setup signup
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        //listener for signup
        FirebaseUser user = mAuth.getCurrentUser();

        userSignupService = new UserSignupService(mAuth,SignupActivity.this, db);
        if(user != null){
            startActivity(new Intent(this, MainActivity.class));
        }
        binding.signupBtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }

    public void signup(){
        String userName = binding.username.getText().toString();
        String password = binding.password.getText().toString();
        String name = binding.nameSignup.getText().toString();
        String location = binding.location.getText().toString();

        if(userName.isEmpty() || password.isEmpty() || name.isEmpty() || location.isEmpty()){
            Log.d(TAG,userName + " " + password + " " + name + " " + location);
            Toast.makeText(this,"Every field need to be filled", LENGTH_SHORT).show();
        }
        else{
            binding.username.setText("");
            binding.password.setText("");
            binding.nameSignup.setText("");
            binding.location.setText("");
            userSignupService.signup(userName,password,name,location);
        }

    }
}