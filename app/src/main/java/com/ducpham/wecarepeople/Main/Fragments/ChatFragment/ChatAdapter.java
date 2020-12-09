package com.ducpham.wecarepeople.Main.Fragments.ChatFragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ducpham.wecarepeople.Main.Fragments.ChatFragment.DirectMessage.DirectMessageActivity;
import com.ducpham.wecarepeople.R;
import com.ducpham.wecarepeople.databinding.ItemChatBinding;
import com.ducpham.wecarepeople.model.User;

import org.parceler.Parcels;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    List<User> users;
    Listener listener;
    public ChatAdapter(Listener listener, List<User> users){
        this.listener = listener;
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final User tempUser = users.get(position);
        holder.binding.name.setText(tempUser.getName());
        Glide.with(holder.binding.getRoot()).load(R.drawable.ic_launcher_background).into(holder.binding.userImage);
        holder.binding.itemChatContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.navigateToDirectMessage(tempUser);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void getUsers(List<User> list){
        this.users.clear();
        this.users.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemChatBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemChatBinding.bind(itemView);
        }
    }

    public interface Listener{
        void navigateToDirectMessage(User tempUser);
    }
}
