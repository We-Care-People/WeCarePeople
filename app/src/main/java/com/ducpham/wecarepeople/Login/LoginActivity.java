package com.ducpham.wecarepeople.Login;

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

import com.ducpham.wecarepeople.Main.MainActivity;
import com.ducpham.wecarepeople.R;
import com.ducpham.wecarepeople.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ducpham.wecarepeople.Signup.SignupActivity;

public class LoginActivity extends AppCompatActivity implements UserLoginService.Listener{

    public static final int REQUEST_CODE = 321;
    public static final String TAG = "LoginActivity";

    private ActivityLoginBinding binding;
    private UserLoginService userLoginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            startActivity(new Intent(this, MainActivity.class));
        }
        userLoginService = new UserLoginService(mAuth, this);
        signInOnClickListener();
        navigateToSignUpActivity();
    }

    public void signInOnClickListener() {
        binding.signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameS = binding.username.getText().toString();
                String passwordS = binding.password.getText().toString();
                if (usernameS.isEmpty() || passwordS.isEmpty()) {
                    showLongToastMessage(R.string.sign_in_error_messge);
                } else {
                    userLoginService.signIn(LoginActivity.this, usernameS, passwordS);
                }
            }
        });
    }

    public void navigateToSignUpActivity() {
        binding.signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    public void onLoginSuccess() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    @Override
    public void onLoginFail() {
        showLongToastMessage(R.string.auth_failed);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(TAG, "start");
        if (requestCode == REQUEST_CODE) {
            showLongToastMessage(R.string.sign_up_error_messge);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    void showLongToastMessage(int message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}