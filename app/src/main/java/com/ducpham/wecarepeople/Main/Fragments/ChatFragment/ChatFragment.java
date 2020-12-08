package com.ducpham.wecarepeople.Main.Fragments.ChatFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ducpham.wecarepeople.Main.Data.Data;
import com.ducpham.wecarepeople.Main.Fragments.CartFragment.CartAdapter;
import com.ducpham.wecarepeople.R;
import com.ducpham.wecarepeople.databinding.FragmentCartBinding;
import com.ducpham.wecarepeople.databinding.FragmentChatBinding;
import com.ducpham.wecarepeople.databinding.FragmentUserBinding;
import com.ducpham.wecarepeople.model.CartItem;
import com.ducpham.wecarepeople.model.Message;
import com.ducpham.wecarepeople.model.User;

import java.util.ArrayList;
import java.util.List;


public class ChatFragment extends Fragment implements Data.Listener{
    FragmentChatBinding binding;
    ChatAdapter chatAdapter;
    List<User> users;
    Data data;
    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        data = new Data(this);
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        data = new Data(this);
        data.getUsers();
        binding = FragmentChatBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        users = new ArrayList<>();
        chatAdapter = new ChatAdapter(users);
        binding.recycleView.setAdapter(chatAdapter);
        binding.recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void getUserSuccess(List<User> list) {
        this.users.clear();
        this.users.addAll(list);
        this.chatAdapter.notifyDataSetChanged();
    }

    @Override
    public void getMessageSuccess(List<Message> list) {

    }

    @Override
    public void getCartItemSuccess(List<CartItem> list) {

    }
}