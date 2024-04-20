package com.example.final_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.GeoPoint;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

// Import necessary libraries for geocoding (Assuming Google's Geocoding API or similar)

public class ShelterRegistrationActivity extends AppCompatActivity {

    private EditText nameEditText, locationEditText, descriptionEditText, phoneNumberEditText, addressEditText, yearOfBusinessEditText, imageUrlEditText;
    private Button registerShelterButton;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_registration);

        firestore = FirebaseFirestore.getInstance();

        // Initialize all EditTexts and Buttons
        initializeFormFields();

        registerShelterButton.setOnClickListener(view -> registerShelter());
    }

    private void initializeFormFields() {
        nameEditText = findViewById(R.id.shelterNameEditText);
        locationEditText = findViewById(R.id.shelterLocationEditText);
        descriptionEditText = findViewById(R.id.shelterDescriptionEditText);
        phoneNumberEditText = findViewById(R.id.shelterPhoneNumberEditText);
        addressEditText = findViewById(R.id.shelterAddressEditText);
        yearOfBusinessEditText = findViewById(R.id.shelterYearOfBusinessEditText);
        imageUrlEditText = findViewById(R.id.shelterImageUrlEditText);
        registerShelterButton = findViewById(R.id.registerShelterButton);
    }

    private void registerShelter() {
        // Extract information from EditTexts
        String name = nameEditText.getText().toString();
        String location = locationEditText.getText().toString(); // Simplified location description
        String description = descriptionEditText.getText().toString();
        String phoneNumber = phoneNumberEditText.getText().toString();
        String address = addressEditText.getText().toString();
        String yearOfBusiness = yearOfBusinessEditText.getText().toString();
        String imageUrl = imageUrlEditText.getText().toString();

        new Thread(() -> {
            GeoPoint geoLocation = geocodeAddress(address);
            runOnUiThread(() -> {
                if (geoLocation != null) {
                    // Create new Shelter with geoLocation
                    Shelter newShelter = new Shelter(name, location, imageUrl, description, phoneNumber, address, yearOfBusiness, geoLocation);
                    saveShelterToFirestore(newShelter);
                    // Return to shelter management screen
                    startActivity(new Intent(ShelterRegistrationActivity.this, ManageSheltersActivity.class));
                } else {
                    Toast.makeText(this, "Failed to geocode address.", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }

    public GeoPoint geocodeAddress(String address) {
        GeoPoint geoPoint = null;
        try {
            // Replace spaces with the URL escape character for spaces
            String addressEncoded = address.replace(" ", "%20");
            String apiKey = "AIzaSyBEmsEDTuy6qlfrKfB8NDJV4fhSXnejBcY";
            URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + addressEncoded + "&key=" + apiKey);

            // Open a connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            // Check if the request was successful
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                StringBuilder inline = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                // Write all the JSON data into a string using a scanner
                while (scanner.hasNext()) {
                    inline.append(scanner.nextLine());
                }

                // Close the scanner
                scanner.close();

                // Using the JSON simple library parse the string into a json object
                JSONObject dataObj = new JSONObject(inline.toString());
                JSONArray results = dataObj.getJSONArray("results");
                if (results.length() > 0) {
                    JSONObject location = results.getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
                    double lat = location.getDouble("lat");
                    double lng = location.getDouble("lng");
                    geoPoint = new GeoPoint(lat, lng);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return geoPoint;
    }

    private void saveShelterToFirestore(@NonNull Shelter newShelter) {
        firestore.collection("shelters").add(newShelter)
                .addOnSuccessListener(documentReference -> Toast.makeText(ShelterRegistrationActivity.this, "Shelter registered successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(ShelterRegistrationActivity.this, "Failed to register shelter", Toast.LENGTH_SHORT).show());
    }
}
