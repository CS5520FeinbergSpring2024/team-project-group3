package com.example.final_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PetListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PetsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        recyclerView = findViewById(R.id.petsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        shelterData selectedShelter = getIntent().getParcelableExtra("selectedShelter");
        if (selectedShelter != null && selectedShelter.getAdoptablePets() != null) {
            // Define the click listener right here for clarity
            PetsAdapter.OnPetClickListener clickListener = new PetsAdapter.OnPetClickListener() {
                @Override
                public void onPetClick(petData pet) {
                    Intent intent = new Intent(PetListActivity.this, PetDetailActivity.class);
                    // Make sure petData class is Parcelable; seems to be, based on your implementation
                    intent.putExtra("petData", pet);
                    startActivity(intent);
                }
            };

            // Pass the adoptable pets and the click listener to the adapter
            adapter = new PetsAdapter(this, selectedShelter.getAdoptablePets(), clickListener);
            recyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No pets available.", Toast.LENGTH_SHORT).show();
        }
    }
}

