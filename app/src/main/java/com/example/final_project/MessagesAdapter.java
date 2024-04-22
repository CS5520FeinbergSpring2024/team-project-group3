package com.example.final_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder> {
    private List<Message> messages;
    private LayoutInflater inflater;
    private String currentUserId;

    public MessagesAdapter(Context context, List<Message> messages, String currentUserId) {
        this.inflater = LayoutInflater.from(context);
        this.messages = messages;
        this.currentUserId = currentUserId;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.message_item, parent, false);
        return new MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.messageTextView.setText(message.getText());
        // Here you could set the text alignment or color based on sender
        if (message.getSenderId().equals(currentUserId)) {
            // This message was sent by the current user
            holder.messageTextView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
            // Additional styling for sent messages
        } else {
            // This message was received
            holder.messageTextView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            // Additional styling for received messages
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }


    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView messageTextView;

        public MessageViewHolder(View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
        }
    }
}
