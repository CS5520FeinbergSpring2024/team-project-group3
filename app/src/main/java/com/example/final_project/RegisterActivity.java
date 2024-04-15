package com.example.final_project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextEmail, editTextUsername;
    private RadioButton radioButtonShelterOwner, radioButtonPetOwner;
    private Button buttonRegister;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FirebaseApp.initializeApp(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextUsername = findViewById(R.id.editTextUsername);
        radioButtonShelterOwner = findViewById(R.id.radioButtonShelterOwner);
        radioButtonPetOwner = findViewById(R.id.radioButtonPetOwner);
        buttonRegister = findViewById(R.id.buttonRegister);
        progressDialog = new ProgressDialog(this);

        buttonRegister.setOnClickListener(view -> registerUser());
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String username = editTextUsername.getText().toString().trim();
        String role = radioButtonShelterOwner.isChecked() ? "shelter_owner" : "pet_owner";

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        // Generate a unique ID for the user
        String userId = UUID.randomUUID().toString();

        Map<String, Object> userData = new HashMap<>();
        userData.put("username", username);
        userData.put("email", email);
        userData.put("role", role);

        // Write data to the Realtime Database
        databaseReference.child(userId).setValue(userData)
                .addOnCompleteListener(task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                        // Navigate to next activity based on the role
                        Intent intent;
                        if ("shelter_owner".equals(role)) {
                            intent = new Intent(RegisterActivity.this, ShelterOwnerDashboardActivity.class);
                        } else {
                            intent = new Intent(RegisterActivity.this, PetOwnerViewActivity.class);
                        }
                        intent.putExtra("USER_ID", userId); // Pass userId to the next activity
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Failed to register. Please try again.", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
