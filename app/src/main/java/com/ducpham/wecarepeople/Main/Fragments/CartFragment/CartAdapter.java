package com.ducpham.wecarepeople.Main.Fragments.CartFragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ducpham.wecarepeople.Main.Data.Data;
import com.ducpham.wecarepeople.R;
import com.ducpham.wecarepeople.databinding.ItemCartBinding;
import com.ducpham.wecarepeople.model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    List<CartItem> cartList;
    Listener listener;

    public CartAdapter(Listener listener, List<CartItem> cartList){
        this.listener = listener;
        this.cartList = cartList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CartItem cartItem = cartList.get(position);
        Glide.with(holder.binding.getRoot().getContext()).asBitmap().load(cartItem.getImageUrl()).into(holder.binding.itemImage);
        holder.binding.name.setText(cartItem.getName());
        holder.binding.description.setText(cartItem.getDescription());
        holder.binding.situtaion.setText(cartItem.getSituation());
        holder.binding.category.setText(cartItem.getCategory());
        holder.binding.count.setText(String.valueOf(cartItem.getCount()));
        holder.binding.gone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goneHandle(cartItem.getItemId());
            }
        });
    }
    @Override
    public int getItemCount() {
        return cartList.size();
    }
    public void goneHandle(String id){
        listener.getListSuccess(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemCartBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemCartBinding.bind(itemView);
        }
    }
    public interface Listener{
        void getListSuccess(String id);
    }
}
