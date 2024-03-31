package com.example.final_project;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BreedViewActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PetTypeAdapter adapter;

    private ListView listView;
    private List<PetType> petTypes;

    /**
     * Breed View that contains pet types which can show breed once clicked.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breed);

        // Initialize views and list
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listView = findViewById(R.id.list_view);
        petTypes = new ArrayList<>();

        //Fetch data from firebase.
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("pet_types");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                petTypes.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PetType petType = snapshot.getValue(PetType.class);
                    petTypes.add(petType);
//                    Log.d("Firebase data", snapshot.toString());
//                    Log.d("Pet Type", snapshot.getValue(PetType.class).getImageUrl());
                }
                // Create and set the adapter
                adapter = new PetTypeAdapter(BreedViewActivity.this, petTypes);
                recyclerView.setAdapter(adapter);
//                Log.d("OnDataChange", "triggered");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
                Log.e("Firebase error", "Failed to read value.", databaseError.toException());
            }
        });
    }

    /**
     * Update breed list (is called in the PetTypeAdapter)
     * @param petDataList list that contains breed objects.
     */
    public void updateListView(List<petData> petDataList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PetListAdapter adapter = new PetListAdapter(BreedViewActivity.this, petDataList);
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
            }
        });
    }
}
