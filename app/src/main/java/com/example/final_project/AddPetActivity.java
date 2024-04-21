package com.example.final_project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class AddPetActivity extends AppCompatActivity {

    private EditText petNameEditText, petTypeEditText, petDescriptionEditText, petAgeEditText,
            petGenderEditText, petBreedEditText, petPriceEditText, petImageUrlEditText;
    private Button submitPetButton;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        firestore = FirebaseFirestore.getInstance();

        petNameEditText = findViewById(R.id.petNameEditText);
        petTypeEditText = findViewById(R.id.petTypeEditText);
        petDescriptionEditText = findViewById(R.id.petDescriptionEditText);
        petAgeEditText = findViewById(R.id.petAgeEditText);
        petGenderEditText = findViewById(R.id.petGenderEditText);
        petBreedEditText = findViewById(R.id.petBreedEditText);
        petPriceEditText = findViewById(R.id.petPriceEditText);
        petImageUrlEditText = findViewById(R.id.petImageUrlEditText);
        submitPetButton = findViewById(R.id.submitPetButton);

        submitPetButton.setOnClickListener(v -> submitPet());
    }

    private void submitPet() {
        String name = petNameEditText.getText().toString();
        String type = petTypeEditText.getText().toString();
        String description = petDescriptionEditText.getText().toString();
        String age = petAgeEditText.getText().toString();
        String gender = petGenderEditText.getText().toString();
        String breed = petBreedEditText.getText().toString();
        String price = petPriceEditText.getText().toString();
        String imageUrl = petImageUrlEditText.getText().toString();

        Pet newPet = new Pet(type, age, description, gender, "", imageUrl, name, breed, price);
        firestore.collection("Pets").add(newPet)
                .addOnSuccessListener(documentReference -> Toast.makeText(AddPetActivity.this, "Pet added successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(AddPetActivity.this, "Failed to add pet", Toast.LENGTH_SHORT).show());
    }
}
