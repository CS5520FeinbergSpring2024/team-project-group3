package com.example.final_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AdoptionApplicationActivity extends AppCompatActivity {

    private EditText applicantNameEditText;
    private EditText applicantAddressEditText;
    private Button submitApplicationButton;
    private FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adoption_application);

        // Initialize Firestore and FirebaseAuth
        firestore = FirebaseFirestore.getInstance();


        applicantNameEditText = findViewById(R.id.applicantNameEditText);
        applicantAddressEditText = findViewById(R.id.applicantAddressEditText);
        submitApplicationButton = findViewById(R.id.submitApplicationButton);

        // Retrieve the pet ID passed to this activity
        String petId = getIntent().getStringExtra("petId");

        submitApplicationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitApplication(petId);
            }
        });
    }

    private void submitApplication(String petId) {
        Intent intent = getIntent();
        String userId = intent.getStringExtra("SHELTER_ID");
        String name = applicantNameEditText.getText().toString();
        String address = applicantAddressEditText.getText().toString();

        // Build the application document
        Map<String, Object> application = new HashMap<>();
        application.put("petId", petId);
        application.put("userId", userId);
        application.put("applicantName", name);
        application.put("applicantAddress", address);
        application.put("status", "pending"); // Default status

        // Submit to Firestore
        firestore.collection("AdoptionApplications")
                .add(application)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(AdoptionApplicationActivity.this, "Application Submitted Successfully", Toast.LENGTH_LONG).show();
                    finish(); // Go back to the previous screen
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AdoptionApplicationActivity.this, "Failed to Submit Application", Toast.LENGTH_LONG).show();
                });
    }
}
