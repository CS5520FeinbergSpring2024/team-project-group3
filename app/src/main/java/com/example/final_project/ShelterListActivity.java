package com.example.final_project;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQueryBounds;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShelterListActivity extends AppCompatActivity {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private RecyclerView recyclerView;
    private ShelterAdapter adapter;
    private FirebaseFirestore firestore;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_list);

        recyclerView = findViewById(R.id.shelterRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ShelterAdapter(new ArrayList<>(), this, shelter -> {
            Intent intent = new Intent(this, ShelterDetailActivity.class);
            intent.putExtra("shelterId", shelter.getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        firestore = FirebaseFirestore.getInstance();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        checkLocationPermissionAndFetch();
    }

    private void checkLocationPermissionAndFetch() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fetchCurrentLocationAndShelters();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchCurrentLocationAndShelters();
            } else {
                Toast.makeText(this, "Location permission denied. Cannot fetch nearby shelters.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void fetchCurrentLocationAndShelters() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                fetchSheltersNearby(location.getLatitude(), location.getLongitude());
            } else {
                Toast.makeText(this, "Failed to get current location.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchSheltersNearby(double latitude, double longitude) {
        double radiusInKm = 10.0;
        GeoLocation center = new GeoLocation(latitude, longitude);

        List<GeoQueryBounds> bounds = GeoFireUtils.getGeoHashQueryBounds(center, radiusInKm * 1000);
        List<Task<QuerySnapshot>> tasks = new ArrayList<>();

        for (GeoQueryBounds bound : bounds) {
            Query query = firestore.collection("Shelters")
                    .orderBy("geohash")
                    .startAt(bound.startHash)
                    .endAt(bound.endHash);

            tasks.add(query.get());
        }

        Tasks.whenAllSuccess(tasks).addOnSuccessListener(list -> {
            List<Shelter> nearbyShelters = new ArrayList<>();
            for (Object object : list) {
                QuerySnapshot snapshots = (QuerySnapshot) object;
                for (DocumentSnapshot documentSnapshot : snapshots.getDocuments()) {
                    double lat = documentSnapshot.getDouble("lat");
                    double lng = documentSnapshot.getDouble("lng");

                    GeoLocation docLocation = new GeoLocation(lat, lng);
                    double distanceInM = GeoFireUtils.getDistanceBetween(docLocation, center);
                    if (distanceInM <= radiusInKm * 1000) {
                        Shelter shelter = documentSnapshot.toObject(Shelter.class);
                        nearbyShelters.add(shelter);
                    }
                }
            }

            adapter.setShelterList(nearbyShelters);
            adapter.notifyDataSetChanged();
        }).addOnFailureListener(e -> Log.w("ShelterListActivity", "Error getting documents: ", e));
    }
}

