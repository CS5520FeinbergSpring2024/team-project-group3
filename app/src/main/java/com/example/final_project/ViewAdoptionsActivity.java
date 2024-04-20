package com.example.final_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewAdoptionsActivity extends AppCompatActivity {
    private RecyclerView applicationsRecyclerView;
    private AdoptionApplicationAdapter adapter;
    private FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_adoptions);

        applicationsRecyclerView = findViewById(R.id.applicationsRecyclerView);
        applicationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AdoptionApplicationAdapter(new ArrayList<>());
        applicationsRecyclerView.setAdapter(adapter);

        firestore = FirebaseFirestore.getInstance();


        fetchAdoptionApplications();
    }

    private void fetchAdoptionApplications() {
        Intent intent = getIntent();
        String currentUserId = intent.getData().getUserInfo();
        firestore.collection("AdoptionApplications")
                .whereEqualTo("shelterId", currentUserId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<AdoptionApplication> applications = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            AdoptionApplication application = document.toObject(AdoptionApplication.class);
                            applications.add(application);
                        }
                        adapter.setAdoptionApplications(applications);
                    } else {
                        Log.e("ViewAdoptionsActivity", "Error getting documents: ", task.getException());
                    }
                });
    }
}

