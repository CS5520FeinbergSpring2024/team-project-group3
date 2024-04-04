package com.example.final_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class PetManagementActivity extends AppCompatActivity {
    private EditText nameEditText, typeEditText, breedEditText, ageEditText, genderEditText, descriptionEditText, priceEditText;
    private Button updateButton, deleteButton;
    private FirebaseFirestore firestore;
    private String petId; // Note: this should ideally come from the intent or selection

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_management);

        // Initialize Firebase Firestore
        firestore = FirebaseFirestore.getInstance();

        // Initialize UI components
        nameEditText = findViewById(R.id.nameEditText);
        typeEditText = findViewById(R.id.typeEditText);
        breedEditText = findViewById(R.id.breedEditText);
        ageEditText = findViewById(R.id.ageEditText);
        genderEditText = findViewById(R.id.genderEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        priceEditText = findViewById(R.id.priceEditText);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);

        // Retrieve petId from Intent
        Intent intent = getIntent();
        if (intent != null) {
            petId = intent.getStringExtra("PET_ID");
            loadPetDetails(petId); // Method to load pet details
        }

        updateButton.setOnClickListener(v -> updatePetDetails());
        deleteButton.setOnClickListener(v -> deletePet());
    }

    private void loadPetDetails(String petId) {
        // Fetch the pet document from Firestore
        firestore.collection("Pets").document(petId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Retrieve pet details
                        Pet pet = documentSnapshot.toObject(Pet.class);
                        if (pet != null) {
                            // Populate the EditText fields with pet details
                            nameEditText.setText(pet.getName());
                            typeEditText.setText(pet.getType());
                            breedEditText.setText(pet.getBreed());
                            ageEditText.setText(pet.getAge());
                            genderEditText.setText(pet.getGender());
                            descriptionEditText.setText(pet.getDescription());
                            priceEditText.setText(pet.getPrice());
                        } else {
                            Toast.makeText(PetManagementActivity.this, "Pet data is null", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(PetManagementActivity.this, "Pet does not exist", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(PetManagementActivity.this, "Failed to load pet details: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }


    private void updatePetDetails() {
        // Fetch updated values from EditTexts
        String name = nameEditText.getText().toString();
        String type = typeEditText.getText().toString();
        String breed = breedEditText.getText().toString();
        String age = ageEditText.getText().toString();
        String gender = genderEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String price = priceEditText.getText().toString();

        // Update Firestore document for the pet
        DocumentReference petRef = firestore.collection("Pets").document(petId);
        petRef.update("name", name, "type", type, "breed", breed, "age", age, "gender", gender, "description", description, "price", price)
                .addOnSuccessListener(aVoid -> Toast.makeText(PetManagementActivity.this, "Pet details updated successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(PetManagementActivity.this, "Update failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void deletePet() {
        // Delete pet from Firestore
        DocumentReference petRef = firestore.collection("Pets").document(petId);
        petRef.delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(PetManagementActivity.this, "Pet deleted successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Note: optionally return to previous Activity
                })
                .addOnFailureListener(e -> Toast.makeText(PetManagementActivity.this, "Deletion failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
