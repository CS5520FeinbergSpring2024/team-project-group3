package com.example.final_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
        DatabaseReference petsRef = FirebaseDatabase.getInstance().getReference("Pets");
        petsRef.orderByChild("shelterId").equalTo(shelterId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            List<Pet> pets = new ArrayList<>();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Pet pet = snapshot.getValue(Pet.class);
                                pets.add(pet);
                            }
                            adapter.updatePets(pets);
                        } else {
                            Toast.makeText(PetListActivity.this, "No pets available at this shelter.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("PetListActivity", "Error loading pets: " + databaseError.getMessage());
                        Toast.makeText(PetListActivity.this, "Error fetching pets from database.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
