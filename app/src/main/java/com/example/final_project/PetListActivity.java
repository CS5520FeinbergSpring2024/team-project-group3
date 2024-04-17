package com.example.final_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class PetListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PetsAdapter adapter;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        recyclerView = findViewById(R.id.petsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new PetsAdapter(this, new ArrayList<>(), pet -> {
            Intent intent = new Intent(PetListActivity.this, PetDetailActivity.class);
            intent.putExtra("petData", pet);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        firestore = FirebaseFirestore.getInstance();

        // Receive the shelter ID from the intent
        String shelterId = getIntent().getStringExtra("shelterId");
        if (shelterId != null) {
            fetchPets(shelterId);
        } else {
            Toast.makeText(this, "Shelter ID not found.", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchPets(String shelterId) {
        firestore.collection("Pets")
                .whereEqualTo("shelterId", shelterId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Pet> pets = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            pets.add(document.toObject(Pet.class));
                        }
                        if (!pets.isEmpty()) {
                            adapter.updatePets(pets);
                        } else {
                            Toast.makeText(this, "No pets available at this shelter.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("PetListActivity", "Error loading pets: ", task.getException());
                        Toast.makeText(this, "Error fetching pets from database.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
