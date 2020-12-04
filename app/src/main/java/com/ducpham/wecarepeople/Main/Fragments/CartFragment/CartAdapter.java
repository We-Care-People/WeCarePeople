package com.ducpham.wecarepeople.Main.Fragments.CartFragment;


import android.content.Context;
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

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    Context context;
    List<CartItem> cartList;
    Data data;

    public CartAdapter(Context context, List<CartItem> cartList, Data data){
        this.context = context;
        this.cartList = cartList;
        this.data = data;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CartItem cartItem = cartList.get(position);
        Glide.with(context).asBitmap().load(cartItem.getImageUrl()).into(holder.binding.itemImage);
        holder.binding.name.setText(cartItem.getName());
        holder.binding.description.setText(cartItem.getDes());
        holder.binding.situtaion.setText(cartItem.getSituation());
        holder.binding.category.setText(cartItem.getCategory());
        holder.binding.count.setText(String.valueOf(cartItem.getCount()));
        holder.binding.gone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goneHandle(cartItem.getItemId());
            }
        });
        Glide.with(context).load(cartItem.getImageUrl()).into(holder.binding.itemImage);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }
    public void goneHandle(String id){
        data.deleteItem(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemCartBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemCartBinding.bind(itemView);
        }
    }
}
