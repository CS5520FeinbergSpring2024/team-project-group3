package com.example.final_project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShelterListActivity extends AppCompatActivity {

    private RecyclerView shelterRecyclerView;
    private ShelterListAdapter shelterListAdapter;
    private ShelterListViewModel shelterListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelterlist);

        // Initialize ViewModel
        shelterListViewModel = new ViewModelProvider(this).get(ShelterListViewModel.class);

        // Initialize RecyclerView
        shelterRecyclerView = findViewById(R.id.shelter_recycler_view);
        shelterRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        shelterListAdapter = new ShelterListAdapter(this, new ArrayList<>());
        shelterRecyclerView.setAdapter(shelterListAdapter);

        // Fetch shelters by breed
        String breedName = getIntent().getStringExtra("breed_name");
        shelterListViewModel.fetchSheltersByBreed(breedName);

        shelterListViewModel.getShelterListLiveData().observe(this, shelterDataList -> {
            shelterListAdapter.setShelterList(shelterDataList); // Update the adapter
        });

    }
}
