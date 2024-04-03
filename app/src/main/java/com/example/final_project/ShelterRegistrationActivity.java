package com.example.final_project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class ShelterRegistrationActivity extends AppCompatActivity {

    private EditText nameEditText, locationEditText, descriptionEditText, phoneNumberEditText, addressEditText, yearOfBusinessEditText, imageUrlEditText;
    private Button registerShelterButton;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_registration);

        firestore = FirebaseFirestore.getInstance();

        nameEditText = findViewById(R.id.shelterNameEditText);
        locationEditText = findViewById(R.id.shelterLocationEditText);
        descriptionEditText = findViewById(R.id.shelterDescriptionEditText);
        phoneNumberEditText = findViewById(R.id.shelterPhoneNumberEditText);
        addressEditText = findViewById(R.id.shelterAddressEditText);
        yearOfBusinessEditText = findViewById(R.id.shelterYearOfBusinessEditText);
        imageUrlEditText = findViewById(R.id.shelterImageUrlEditText);
        registerShelterButton = findViewById(R.id.registerShelterButton);

        registerShelterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerShelter();
            }
        });
    }

    private void registerShelter() {
        String name = nameEditText.getText().toString();
        String location = locationEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String phoneNumber = phoneNumberEditText.getText().toString();
        String address = addressEditText.getText().toString();
        String yearOfBusiness = yearOfBusinessEditText.getText().toString();
        String imageUrl = imageUrlEditText.getText().toString();

        Shelter newShelter = new Shelter(name, location, imageUrl, null, description, phoneNumber, address, yearOfBusiness, null);

        firestore.collection("shelters").add(newShelter)
                .addOnSuccessListener(documentReference -> Toast.makeText(ShelterRegistrationActivity.this, "Shelter registered successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(ShelterRegistrationActivity.this, "Failed to register shelter", Toast.LENGTH_SHORT).show());
    }
}
