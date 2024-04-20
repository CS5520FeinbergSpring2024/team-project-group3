package com.example.final_project;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    private ListView messagesListView;
    private EditText messageEditText;
    private Button sendMessageButton;
    private MessagesAdapter messagesAdapter;
    private FirebaseFirestore firestore;

    private String chatId;
    private String shelterId;

    private RecyclerView messagesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messagesRecyclerView = findViewById(R.id.messagesRecyclerView); // Updated ID
        messageEditText = findViewById(R.id.messageEditText);
        sendMessageButton = findViewById(R.id.sendMessageButton);

        firestore = FirebaseFirestore.getInstance();

        chatId = getIntent().getStringExtra("chatId");
        shelterId = getIntent().getStringExtra("shelterId");

        // Updated to use a LinearLayoutManager
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Passing the correct arguments to the adapter
        messagesAdapter = new MessagesAdapter(this, new ArrayList<>(),getIntent().getStringExtra("USER_ID"));
        messagesRecyclerView.setAdapter(messagesAdapter);

        fetchMessages();
        setupSendMessageButton();
    }

    private void fetchMessages() {
        firestore.collection("Chats").document(chatId).collection("Messages")
                .orderBy("timestamp")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ArrayList<Message> messages = new ArrayList<>();
                        task.getResult().forEach(document -> messages.add(document.toObject(Message.class)));
                        messagesAdapter.setMessages(messages);
                    } else {
                        Log.e("ChatActivity", "Error fetching messages: ", task.getException());
                    }
                });
    }

    private void setupSendMessageButton() {
        sendMessageButton.setOnClickListener(v -> {
            String messageText = messageEditText.getText().toString();
            if (!messageText.isEmpty()) {
                Map<String, Object> message = new HashMap<>();
                message.put("senderId", getIntent().getStringExtra("USER_ID") );
                message.put("receiverId", shelterId);
                message.put("text", messageText);
                message.put("timestamp", System.currentTimeMillis());

                firestore.collection("Chats").document(chatId).collection("Messages")
                        .add(message)
                        .addOnSuccessListener(documentReference -> messageEditText.setText(""))
                        .addOnFailureListener(e -> Log.e("ChatActivity", "Failed to send message: ", e));
            }
        });
    }
}
