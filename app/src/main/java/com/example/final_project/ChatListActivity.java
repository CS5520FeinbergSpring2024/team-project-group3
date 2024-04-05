package com.example.final_project;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ChatListActivity extends AppCompatActivity {
    private RecyclerView chatListRecyclerView;
    private ChatListAdapter adapter;
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        chatListRecyclerView = findViewById(R.id.chatListRecyclerView);
        chatListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ChatListAdapter(new ArrayList<>(), this);
        chatListRecyclerView.setAdapter(adapter);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        fetchChatList();
    }

    private void fetchChatList() {
        String currentUserId = auth.getCurrentUser().getUid();
        firestore.collection("Chats")
                .whereEqualTo("petOwnerId", currentUserId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Chat> chats = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Chat chat = document.toObject(Chat.class);
                            chats.add(chat);
                        }
                        adapter.setChats(chats);
                    } else {
                        Log.e("ChatListActivity", "Error getting chat documents: ", task.getException());
                    }
                });
    }
}
