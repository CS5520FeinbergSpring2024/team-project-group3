package com.example.final_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewAdoptionsActivity extends AppCompatActivity {
    private RecyclerView applicationsRecyclerView;
    private AdoptionApplicationAdapter adapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_adoptions);

        applicationsRecyclerView = findViewById(R.id.applicationsRecyclerView);
        applicationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdoptionApplicationAdapter(new ArrayList<>());
        applicationsRecyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        fetchAdoptionApplications();
    }

    private void fetchAdoptionApplications() {
        Intent intent = getIntent();
        String currentUserId = intent.getStringExtra("UserID");
        if (currentUserId != null) {
            databaseReference.child("AdoptionApplications")
                    .orderByChild("shelterId").equalTo(currentUserId)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            List<AdoptionApplication> applications = new ArrayList<>();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                AdoptionApplication application = snapshot.getValue(AdoptionApplication.class);
                                applications.add(application);
                            }
                            adapter.setAdoptionApplications(applications);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.e("ViewAdoptionsActivity", "Error loading applications: ", databaseError.toException());
                            Toast.makeText(ViewAdoptionsActivity.this, "Error fetching applications from database.", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "User ID not found.", Toast.LENGTH_SHORT).show();
        }
    }
}
