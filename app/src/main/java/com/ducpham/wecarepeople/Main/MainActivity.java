package com.ducpham.wecarepeople.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.ducpham.wecarepeople.Main.Fragments.CartFragment.CartFragment;
import com.ducpham.wecarepeople.Main.Fragments.ChatFragment.ChatFragment;
import com.ducpham.wecarepeople.Main.Fragments.PostFragment.PostFragment;
import com.ducpham.wecarepeople.Main.Fragments.UserFragment.UserFragment;
import com.ducpham.wecarepeople.R;
import com.ducpham.wecarepeople.databinding.ActivityMainBinding;
import com.ducpham.wecarepeople.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends AppCompatActivity {
    static final String TAG = "MainActivity";

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ActivityMainBinding binding;
    private UserInfoService userInfoService;
    final FragmentManager fragmentManager = getSupportFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        DocumentReference documentReference = db.collection("Users").document(firebaseUser.getUid());
        userInfoService = new UserInfoService(documentReference,this,binding);
        //setSupportActionBar(binding.toolbar);

        User user = userInfoService.getUser();
        Log.d(TAG,firebaseUser.getUid());
        setBottomNavigation();

    }

    public void setBottomNavigation(){
        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            Fragment fragment;
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_post:
                        fragment = new PostFragment();
                        break;
                    case R.id.action_cart:
                        fragment = new CartFragment();
                        break;
                    case R.id.action_chat:
                        fragment = new ChatFragment();
                        break;
                    default:
                        fragment = new UserFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer,fragment).commit();
                return  true;
            }
        });
        binding.bottomNavigation.setSelectedItemId(R.id.action_post);
    }
}