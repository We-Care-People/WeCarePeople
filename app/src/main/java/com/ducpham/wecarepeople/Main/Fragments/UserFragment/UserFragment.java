package com.ducpham.wecarepeople.Main.Fragments.UserFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ducpham.wecarepeople.Login.LoginActivity;
import com.ducpham.wecarepeople.Main.Data.Data;
import com.ducpham.wecarepeople.R;
import com.ducpham.wecarepeople.databinding.FragmentUserBinding;
import com.ducpham.wecarepeople.model.CartItem;
import com.ducpham.wecarepeople.model.Message;
import com.ducpham.wecarepeople.model.Post;
import com.ducpham.wecarepeople.model.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;


public class UserFragment extends Fragment implements Data.Listener{

    static final String TAG = "UserFragment";
    FragmentUserBinding binding;
    FirebaseAuth mAuth;
    Data data;
    User user;
    public UserFragment() {
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
        mAuth = FirebaseAuth.getInstance();
        user = new User();
        data = new Data(this);
        data.getSpecificUser(mAuth.getUid());
        binding = FragmentUserBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        //here data must be an instance of the class MarsDataProvider
        return view;
    }
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        binding.location.setText(user.getLocation());
        binding.name.setText(user.getName());
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

    }

    @Override
    public void getUser(User user) {
        Log.d(TAG,user.getName());
        this.user = user;
        binding.location.setText(user.getLocation());
        binding.name.setText(user.getName());
    }
}