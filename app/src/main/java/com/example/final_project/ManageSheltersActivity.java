package com.example.final_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

public class ManageSheltersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button addButton;
    private ShelterAdapter adapter;
    private List<Shelter> shelterList = new ArrayList<>();
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_shelters);

        firestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.sheltersRecyclerView);
        addButton = findViewById(R.id.addShelterButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ShelterAdapter(shelterList, this, new ShelterAdapter.OnShelterClickListener() {
            @Override
            public void onShelterClicked(Shelter shelter) {
                Intent intent = new Intent(ManageSheltersActivity.this, ShelterDetailActivity.class);
                intent.putExtra("shelter", shelter);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        addButton.setOnClickListener(v -> startActivity(new Intent(ManageSheltersActivity.this, ShelterRegistrationActivity.class)));

        loadShelters();
    }

    private void loadShelters() {
        firestore.collection("shelters").get().addOnSuccessListener(queryDocumentSnapshots -> {
            shelterList.clear();
            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                Shelter shelter = document.toObject(Shelter.class);
                if (shelter != null) { // Always check for null after converting document
                    shelterList.add(shelter);
                }
            }
            adapter.notifyDataSetChanged();
        }).addOnFailureListener(e -> Toast.makeText(ManageSheltersActivity.this, "Failed to load shelters.", Toast.LENGTH_SHORT).show());
    }
}
