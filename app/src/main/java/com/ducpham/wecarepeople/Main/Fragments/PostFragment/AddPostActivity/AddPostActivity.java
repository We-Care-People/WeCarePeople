package com.ducpham.wecarepeople.Main.Fragments.PostFragment.AddPostActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ducpham.wecarepeople.R;
import com.ducpham.wecarepeople.databinding.ActivityAddPostBinding;
import com.ducpham.wecarepeople.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

public class AddPostActivity extends AppCompatActivity {

    static final String TAG = "AddPostActivity";
    ActivityAddPostBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPost();
            }
        });
    }

    public void sendPost(){
        String title = String.valueOf(binding.title.getText());
        String message = String.valueOf(binding.message.getText());

        if(title.equals("") || message.equals("")){
            Toast.makeText(this,"Please fill in all the fields",Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent();
            intent.putExtra("title",title);
            intent.putExtra("message",message);
            setResult(RESULT_OK,intent);
            binding.title.setText("");
            binding.message.setText("");
            finish();
        }
    }
}