package com.example.final_project;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

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

        databaseReference = FirebaseDatabase.getInstance().getReference();

        String userId = getUserIdFromPreferences();
        if (userId != null) {
            fetchChatsForUser(userId);
        } else {
            Toast.makeText(this, "No user ID provided.", Toast.LENGTH_LONG).show();
        }
    }

    private String getUserIdFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        return sharedPreferences.getString("UserID", null);
    }

    private void fetchChatsForUser(String userId) {
        Query chatsQuery = databaseReference.child("Chats").orderByChild("participant1").equalTo(userId);
        chatsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Chat> chats = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat != null) {
                        chats.add(chat);
                    }
                }
                adapter.setChats(chats);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ChatListActivity", "Error loading chats: " + databaseError.getMessage());
                Toast.makeText(ChatListActivity.this, "Error fetching chat data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

