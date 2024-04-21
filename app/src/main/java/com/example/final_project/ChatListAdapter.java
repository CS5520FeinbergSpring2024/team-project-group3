package com.example.final_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    private List<Chat> chats;
    private Context context;

    public ChatListAdapter(List<Chat> chats, Context context) {
        this.chats = chats;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chat chat = chats.get(position);
        // Set the last message text and timestamp
        holder.lastMessageTextView.setText(chat.getLastMessage());
        // Convert timestamp to readable date and set it, just an example placeholder
        holder.timestampTextView.setText(String.valueOf(chat.getLastMessageTimestamp()));
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView lastMessageTextView, timestampTextView;

        public ViewHolder(View view) {
            super(view);
            lastMessageTextView = view.findViewById(R.id.lastMessageTextView);
            timestampTextView = view.findViewById(R.id.timestampTextView);
        }
    }
}
