package com.ducpham.wecarepeople.Main.Fragments.PostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ducpham.wecarepeople.R;
import com.ducpham.wecarepeople.databinding.ItemPostBinding;
import com.ducpham.wecarepeople.model.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{
    static final String TAG = "PostAdapter";
    List<Post> postList;

    public PostAdapter(List<Post> postList){
        this.postList = postList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post tempPost = postList.get(position);
        Glide.with(holder.binding.getRoot()).load(R.drawable.ic_launcher_background).into(holder.binding.userImage);
        holder.binding.title.setText(tempPost.getPostTitle());
        holder.binding.message.setText(tempPost.getMessage());
        holder.binding.name.setText(tempPost.getUserName());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public void addAll(List<Post> postList){
        this.postList.clear();
        this.postList.addAll(postList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemPostBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemPostBinding.bind(itemView);
        }
    }
}
