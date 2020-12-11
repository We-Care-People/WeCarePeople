package com.ducpham.wecarepeople.Main.Fragments.PostFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ducpham.wecarepeople.Main.Data.Data;
import com.ducpham.wecarepeople.Main.Fragments.PostFragment.AddPostActivity.AddPostActivity;
import com.ducpham.wecarepeople.R;
import com.ducpham.wecarepeople.databinding.FragmentCartBinding;
import com.ducpham.wecarepeople.databinding.FragmentPostBinding;
import com.ducpham.wecarepeople.model.CartItem;
import com.ducpham.wecarepeople.model.Message;
import com.ducpham.wecarepeople.model.Post;
import com.ducpham.wecarepeople.model.User;
import com.google.firebase.auth.FirebaseAuth;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class PostFragment extends Fragment implements Data.Listener {

    static final String TAG = "PostFragment";
    static final int REQUEST_CODE = 399;
    FirebaseAuth mAuth;
    List<Post> postList;
    List<User> userList;
    FragmentPostBinding binding;
    Data data;
    PostAdapter postAdapter;
    public PostFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        data = new Data(this);
        postList = new ArrayList<>();
        userList = new ArrayList<>();
        data.getPost();
        mAuth = FirebaseAuth.getInstance();
        postAdapter = new PostAdapter(postList);
        binding = FragmentPostBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recycleView.setAdapter(postAdapter);
        binding.recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPost();
            }
        });

    }

    public void addPost(){
        Intent intent = new Intent(getContext(), AddPostActivity.class);
        startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(TAG, "start");
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            String title = data.getStringExtra("title");
            String message = data.getStringExtra("message");
            Post post = new Post(title,mAuth.getCurrentUser().getDisplayName(),mAuth.getUid(),message);
            this.data.sendPost(post);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void getCartItemSuccess(List<CartItem> list) {

    }

    @Override
    public void getUserSuccess(List<User> list) {

    }

    @Override
    public void getMessageSuccess(List<Message> list) {

    }

    @Override
    public void getPostSuccess(List<Post> postList) {
        postAdapter.addAll(postList);
    }

    @Override
    public void getUser(User user) {

    }

}