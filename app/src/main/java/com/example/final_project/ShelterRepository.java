package com.example.final_project;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ShelterRepository {
    private DatabaseReference sheltersRef;

    public ShelterRepository() {
        // Initialize the reference to the "shelters" node in Firebase Realtime Database
        sheltersRef = FirebaseDatabase.getInstance().getReference("shelters");
    }

    public void fetchSheltersByBreed(String breedName, FetchSheltersCallback callback) {
        // Listen for shelters that have the specified breed in their breedList
        sheltersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Shelter> shelterList = new ArrayList<>();
                for (DataSnapshot shelterSnapshot : dataSnapshot.getChildren()) {
                    Shelter shelter = shelterSnapshot.getValue(Shelter.class);
                    // Assuming breedList is stored as a list or set within the shelter object
                    if (shelter != null && shelter.getBreeds() != null && shelter.getBreeds().contains(breedName)) {
                        shelterList.add(shelter);
                    }
                }
                callback.onSheltersFetched(shelterList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Log or handle errors as appropriate
                callback.onSheltersFetchFailed(databaseError.toException());
            }
        });
    }

    public interface FetchSheltersCallback {
        void onSheltersFetched(List<Shelter> shelters); // Use ShelterData, not shelterData
        void onSheltersFetchFailed(Exception e);
    }
}


