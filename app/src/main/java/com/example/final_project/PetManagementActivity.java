package com.example.final_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PetManagementActivity extends AppCompatActivity {
    private ListView listViewPets;
    private DatabaseReference databaseReference;
    private List<String> petNames;
    private String shelterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_management);

        listViewPets = findViewById(R.id.listViewPets);
        petNames = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Retrieve shelterId from Intent
        Intent intent = getIntent();
        shelterId = intent.getStringExtra("SHELTER_ID");
        if (shelterId != null && !shelterId.isEmpty()) {
            loadPets(shelterId);
        } else {
            Toast.makeText(this, "Invalid Shelter ID", Toast.LENGTH_LONG).show();
        }
    }

    private void loadPets(String shelterId) {
        databaseReference.child("Pets")
                .orderByChild("shelterId").equalTo(shelterId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        petNames.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String petName = snapshot.child("name").getValue(String.class); // Assuming pet names are stored under the "name" key
                            if (petName != null) {
                                petNames.add(petName);
                            }
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(PetManagementActivity.this,
                                android.R.layout.simple_list_item_1, petNames);
                        listViewPets.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(PetManagementActivity.this, "Failed to load pets: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
