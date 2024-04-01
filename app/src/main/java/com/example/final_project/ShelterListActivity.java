package com.example.final_project;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

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

public class ShelterListActivity extends AppCompatActivity {

    private ListView listViewShelters;
    private ShelterListAdapter shelterListAdapter;

    private TextView breedNameTextView;
    private List<shelterData> shelterDataList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelterlist);

        //initialize views, list, strings, database.
        listViewShelters = findViewById(R.id.listViewShelter);
        shelterDataList = new ArrayList<>();
        breedNameTextView = findViewById(R.id.breed_name_text_view);

        String breedName = getIntent().getStringExtra("breed_name");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        breedNameTextView.setText(breedName.toUpperCase());

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
                    String description = snapshot.child("description").getValue(String.class);
                    String phoneNumber = snapshot.child("phoneNumber").getValue(String.class);
                    String address = snapshot.child("address").getValue(String.class);
                    String yearOfBusiness = snapshot.child("yearofbusiness").getValue(String.class);

                    List<String> breeds = new ArrayList<>();
                    for (DataSnapshot breedSnapshot : snapshot.child("breeds").getChildren()) {
                        String breedValue = breedSnapshot.getValue(String.class);
                        breeds.add(breedValue);
                    }

                    List<petData> petDataList = new ArrayList<>();
                    for (DataSnapshot petSnapshot : snapshot.child("adoptablePet").getChildren()) {
                        String type = petSnapshot.getKey().substring(0, 3);
                        Log.d("type", type);
                        String age = petSnapshot.child("age").getValue(String.class);
                        String descriptionPet = petSnapshot.child("description").getValue(String.class);
                        String gender = petSnapshot.child("gender").getValue(String.class);
                        String id = petSnapshot.child("id").getValue(String.class);
                        String imageUrlPet = petSnapshot.child("imageUrl").getValue(String.class);
                        String name = petSnapshot.child("name").getValue(String.class);
                        String breed = petSnapshot.child("breed").getValue(String.class);
                        String price = petSnapshot.child("price").getValue(String.class);
                        Log.d("Price", price);

                        petData pet = new petData(type, age, descriptionPet, gender, id, imageUrlPet, name, breed, price);
                        petDataList.add(pet);
                    }

                    // attach shelter elements to the new shelter object so that it can be added to the list and create the listView
                    // if it matches certain criteria.
                    shelterData s = new shelterData(shelterName, location, imageUrl, breeds, description, phoneNumber, address, yearOfBusiness, petDataList);
//                    Log.d("updated shelter: ", s.getAddress() + s.getAdoptablePets().size());
                    if (s.containBreed(breedName)) {
                        shelterDataList.add(s);
                        shelterListAdapter = new ShelterListAdapter(ShelterListActivity.this, shelterDataList);
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
