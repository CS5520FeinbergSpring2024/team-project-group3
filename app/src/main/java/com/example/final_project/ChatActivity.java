package com.example.final_project;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    private EditText messageEditText;
    private Button sendMessageButton;
    private MessagesAdapter messagesAdapter;
    private FirebaseFirestore firestore;
    private String chatId;
    private RecyclerView messagesRecyclerView;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        firestore = FirebaseFirestore.getInstance();
        messageEditText = findViewById(R.id.messageEditText);
        sendMessageButton = findViewById(R.id.sendMessageButton);
        messagesRecyclerView = findViewById(R.id.messagesRecyclerView);

        // Retrieve chatId and currentUserId from Intent
        chatId = getIntent().getStringExtra("chatId");
        currentUserId = getIntent().getStringExtra("UserID");

        if (currentUserId == null) {
            Toast.makeText(this, "No user ID provided.", Toast.LENGTH_LONG).show();
            finish(); // Close activity if no user ID is provided
            return;
        }

        setupRecyclerView();
        fetchMessages();
        setupSendMessageButton();
    }

    private void setupRecyclerView() {
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        messagesAdapter = new MessagesAdapter(this, new ArrayList<>(), currentUserId);
        messagesRecyclerView.setAdapter(messagesAdapter);
    }

    private void fetchMessages() {
        firestore.collection("Chats").document(chatId).collection("Messages")
                .orderBy("timestamp")
                .addSnapshotListener((snapshots, e) -> {
                    if (e != null) {
                        Log.e("ChatActivity", "Listen failed.", e);
                        return;
                    }

                    ArrayList<Message> messages = new ArrayList<>();
                    for (DocumentSnapshot doc : snapshots.getDocuments()) {
                        Message message = doc.toObject(Message.class);
                        messages.add(message);
                    }
                    messagesAdapter.setMessages(messages);
                });
    }

    private void setupSendMessageButton() {
        sendMessageButton.setOnClickListener(v -> {
            String messageText = messageEditText.getText().toString().trim();
            if (!messageText.isEmpty()) {
                Map<String, Object> message = new HashMap<>();
                message.put("senderId", currentUserId);
                message.put("text", messageText);
                message.put("timestamp", System.currentTimeMillis());

                // Adding a new message to Firestore
                firestore.collection("Chats").document(chatId).collection("Messages")
                        .add(message)
                        .addOnSuccessListener(documentReference -> {
                            messageEditText.setText("");
                            Log.d("ChatActivity", "Message sent successfully");
                        })
                        .addOnFailureListener(e -> {
                            Log.e("ChatActivity", "Failed to send message: ", e);
                            Toast.makeText(ChatActivity.this, "Failed to send message: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });
    }
}

