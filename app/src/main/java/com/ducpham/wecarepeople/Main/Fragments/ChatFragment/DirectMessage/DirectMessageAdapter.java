package com.ducpham.wecarepeople.Main.Fragments.ChatFragment.DirectMessage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ducpham.wecarepeople.R;
import com.ducpham.wecarepeople.databinding.LeftChatBinding;
import com.ducpham.wecarepeople.databinding.RightChatBinding;
import com.ducpham.wecarepeople.model.Message;

import java.util.List;

public class DirectMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int SENDER = 0, RECEIVER = 1;
    List<Message> messageList;
    String userId;
    Context context;


    public DirectMessageAdapter(String userId, List<Message> messageList){
        this.userId = userId;
        this.messageList = messageList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(context);

        switch (viewType) {
            case SENDER:
                View v1 = inflater.inflate(R.layout.right_chat,parent, false);
                viewHolder = new ViewHolderRight(v1);
                break;
            default:
                View v = inflater.inflate(R.layout.left_chat,parent, false);
                viewHolder = new ViewHolderLeft(v);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messageList.get(position);
        switch (holder.getItemViewType()) {
            case SENDER:
                ViewHolderRight vh1 = (ViewHolderRight) holder;
                vh1.binding.text.setText(message.getMessage());
                break;
            default:
                ViewHolderLeft vh2 = (ViewHolderLeft) holder;
                vh2.binding.text.setText(message.getMessage());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messageList.get(position);
        if (userId.equals(message.getSender())) {
            return SENDER;
        }
        return RECEIVER;
    }


    public class ViewHolderRight extends RecyclerView.ViewHolder{
        RightChatBinding binding;
        public ViewHolderRight(@NonNull View itemView) {
            super(itemView);
            binding = RightChatBinding.bind(itemView);
        }
    }

    public class ViewHolderLeft extends RecyclerView.ViewHolder{
        LeftChatBinding binding;
        public ViewHolderLeft(@NonNull View itemView) {
            super(itemView);
            binding = LeftChatBinding.bind(itemView);
        }
    }
}
