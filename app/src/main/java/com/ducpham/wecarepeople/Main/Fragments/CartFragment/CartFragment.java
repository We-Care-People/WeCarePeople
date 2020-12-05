package com.ducpham.wecarepeople.Main.Fragments.CartFragment;

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
import com.ducpham.wecarepeople.Main.Fragments.CartFragment.AddService.AddToCartActivity;
import com.ducpham.wecarepeople.databinding.FragmentCartBinding;
import com.ducpham.wecarepeople.model.CartItem;
import com.google.firebase.auth.FirebaseAuth;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


public class CartFragment extends Fragment implements Data.Listener, CartAdapter.Listener{
    static final String TAG = "CartFragment";
    static final int REQUEST_CODE = 398;
    FragmentCartBinding binding;
    FirebaseAuth mAuth;
    CartAdapter cartAdapter;
    List<CartItem> cartList;
    Data data;
    public CartFragment() {
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
        data.getCartItems();
        mAuth = FirebaseAuth.getInstance();
        binding = FragmentCartBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cartList = new ArrayList<>();
        cartAdapter = new CartAdapter(this,cartList);
        binding.recycleView.setAdapter(cartAdapter);
        binding.recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });
    }

    public void addItem(){
        Intent intent = new Intent(getContext(), AddToCartActivity.class);
        startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(TAG, "start");
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Toast.makeText(getContext(),"Success",Toast.LENGTH_SHORT).show();
            CartItem item =  Parcels.unwrap(data.getParcelableExtra("item"));
            this.data.addItem(item);
        }

        super.onActivityResult(requestCode, resultCode, data);
        this.data.getCartItems();
    }


    @Override
    public void getCartItemSuccess(List<CartItem> list) {
        // Log.d(TAG,String.valueOf(Data.getInstance().itemList()));
        cartList.clear();
        cartList.addAll(list);
        Log.d(TAG,String.valueOf(cartList.size()));
        cartAdapter.notifyDataSetChanged();
    }

    @Override
    public void getListSuccess(String id) {
        data.deleteItem(id);
    }
}