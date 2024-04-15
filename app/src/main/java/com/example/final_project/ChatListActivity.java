package com.example.final_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatListActivity extends AppCompatActivity {
    private RecyclerView chatListRecyclerView;
    private ChatListAdapter adapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        chatListRecyclerView = findViewById(R.id.chatListRecyclerView);
        chatListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ChatListAdapter(new ArrayList<>(), this);
        chatListRecyclerView.setAdapter(adapter);

        FirebaseApp.initializeApp(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Retrieve the user email from the intent or SharedPreferences
        String userEmail = getIntent().getStringExtra("userEmail");

        if (userEmail != null) {
            fetchChatList(userEmail);
        } else {
            Toast.makeText(this, "No user email provided.", Toast.LENGTH_LONG).show();
        }
    }

    private void fetchChatList(String userEmail) {
        Query query = databaseReference.child("Users").orderByChild("email").equalTo(userEmail);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        // Assuming each user email corresponds to a unique user
                        String userId = userSnapshot.getKey();
                        fetchChatsForUser(userId);
                        break; // Exit after finding the first match
                    }
                } else {
                    Toast.makeText(ChatListActivity.this, "User not found.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ChatListActivity", "Error loading user: " + databaseError.getMessage());
            }
        });
    }

    private void fetchChatsForUser(String userId) {
        DatabaseReference chatsRef = databaseReference.child("Chats");
        Query chatsQuery = chatsRef.orderByChild("participant1").equalTo(userId);
        chatsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Chat> chats = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    chats.add(chat);
                }
                adapter.setChats(chats);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ChatListActivity", "Error loading chats: " + databaseError.getMessage());
            }
        });
    }
}
