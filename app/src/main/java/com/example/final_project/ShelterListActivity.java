package com.example.final_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShelterListActivity extends AppCompatActivity {
    private static final String TAG = "ShelterListActivity";

    private RecyclerView recyclerView;
    private ShelterAdapter adapter;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_list);

        recyclerView = findViewById(R.id.shelterRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ShelterAdapter(new ArrayList<>(), this, shelter -> {
            Intent intent = new Intent(this, ShelterDetailActivity.class);
            intent.putExtra("shelterId", shelter.getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        firestore = FirebaseFirestore.getInstance();
        fetchShelters();
    }

    private void fetchShelters() {
        firestore.collection("Shelters")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    Log.d(TAG, "Firestore query successful. Documents retrieved: " + querySnapshot.size());

                    List<Shelter> sheltersList = new ArrayList<>();
                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {

                        Shelter shelter = document.toObject(Shelter.class);
                        shelter.setId(document.getId()); // Set the ID
                        sheltersList.add(shelter);
                    }

                    Log.d(TAG, "Retrieved shelters: " + sheltersList);
                    adapter.setShelterList(sheltersList);
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error getting documents: ", e);
                    Toast.makeText(this, "Error fetching shelters.", Toast.LENGTH_SHORT).show();
                });
    }

}
