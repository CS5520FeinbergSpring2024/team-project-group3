package com.example.final_project;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * shelter activity that is displayed on shelter page.
 */

public class ShelterActivity extends AppCompatActivity {

    private ListView listViewShelters;
    private ShelterListAdapter shelterListAdapter;
    private List<shelterData> shelterDataList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter);

        //initialize views, list, strings, database.
        listViewShelters = findViewById(R.id.listViewShelter);
        shelterDataList = new ArrayList<>();

        String breedName = getIntent().getStringExtra("breed_name");
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = database.getReference("shelters");

        // Add ValueEventListener to fetch shelter data
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                shelterDataList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String shelterName = snapshot.child("name").getValue(String.class);
                    String imageUrl = snapshot.child("imageUrl").getValue(String.class);
                    String location = snapshot.child("location").getValue(String.class);

                    List<String> breeds = new ArrayList<>();
                    for (DataSnapshot breedSnapshot : snapshot.child("breeds").getChildren()) {
                        String breedValue = breedSnapshot.getValue(String.class);
                        breeds.add(breedValue);
                    }
                    // attach shelter elements to the new shelter object so that it can be added to the list and create the listView
                    // if it matches certain criteria.
                    shelterData s = new shelterData(shelterName, location, imageUrl, breeds);
                    if (s.containBreed(breedName)) {
                        shelterDataList.add(s);
                        shelterListAdapter = new ShelterListAdapter(ShelterActivity.this, shelterDataList);
                        listViewShelters.setAdapter(shelterListAdapter);
                    }

                }

                Log.d("OnDataChange Shelter activity list", "triggered");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
                Log.e("Firebase error", "Failed to read value.", databaseError.toException());
            }
        });

    }
}
