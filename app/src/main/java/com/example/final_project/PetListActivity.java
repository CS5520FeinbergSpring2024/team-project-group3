package com.example.final_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PetListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PetsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        recyclerView = findViewById(R.id.petsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        // Inside onCreate method of PetListActivity
        Shelter selectedShelter = getIntent().getParcelableExtra("selectedShelter");
        if (selectedShelter != null) {
            List<Pet> pets = selectedShelter.getAdoptablePets();
            if (pets != null && !pets.isEmpty()) {
                adapter = new PetsAdapter(pets, pet -> {
                    Intent intent = new Intent(PetListActivity.this, PetDetailActivity.class);
                    intent.putExtra("petData", pet);
                    startActivity(intent);
                });
                recyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(this, "No pets available.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Shelter details not available.", Toast.LENGTH_SHORT).show();
        }

    }
}

