package com.ducpham.wecarepeople.Main.Fragments.ChatFragment.DirectMessage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.ducpham.wecarepeople.Main.Data.Data;
import com.ducpham.wecarepeople.R;
import com.ducpham.wecarepeople.databinding.ActivityDirectMessageBinding;
import com.ducpham.wecarepeople.model.CartItem;
import com.ducpham.wecarepeople.model.Message;
import com.ducpham.wecarepeople.model.Post;
import com.ducpham.wecarepeople.model.User;
import com.google.firebase.auth.FirebaseAuth;


import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DirectMessageActivity extends AppCompatActivity implements Data.Listener{
    final static String TAG = "DirectMessageActivity";
    ActivityDirectMessageBinding binding;
    User user;
    FirebaseAuth mAuth;
    Data data;
    List<Message> list;
    DirectMessageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDirectMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        user = Parcels.unwrap(getIntent().getParcelableExtra("user"));
        data = new Data(this);
        list = new ArrayList<>();
        adapter = new DirectMessageAdapter(mAuth.getUid(),list);
        data.getMessage(mAuth.getUid(),user.getUserId());
        binding.recycleView.setAdapter(adapter);
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        Glide.with(this).load(R.drawable.ic_launcher_foreground).into(binding.image);
        binding.name.setText(user.getName());
        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });
    }

    void send(){
        Message message = new Message(mAuth.getUid(),user.getUserId(),binding.text.getText().toString(), new Date());
        Log.d(TAG,String.valueOf(message.getDate()));
        data.sendMessage(message);
        binding.text.setText("");
    }

    @Override
    public void getCartItemSuccess(List<CartItem> list) {

    }

    @Override
    public void getUserSuccess(List<User> list) {

    }

    @Override
    public void getMessageSuccess(List<Message> list) {
        adapter.addAll(list);
    }

    @Override
    public void getPostSuccess(List<Post> postList) {

    }

    @Override
    public void getUser(User user) {

    }
}