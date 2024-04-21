package com.example.final_project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        if (email.isEmpty() || username.isEmpty()) {
            Toast.makeText(this, "Email and username cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Checking Email...");
        progressDialog.show();

        // Check if the email is already in use
        databaseReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                if (dataSnapshot.exists()) {
                    Toast.makeText(RegisterActivity.this, "Email already in use. Please use a different email.", Toast.LENGTH_LONG).show();
                } else {
                    createUser(email, username);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "Failed to check email: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void createUser(String email, String username) {
        String role = radioButtonShelterOwner.isChecked() ? "shelter_owner" : "pet_owner";
        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        String userId = UUID.randomUUID().toString();  // Generate a unique ID for the user

        Map<String, Object> userData = new HashMap<>();
        userData.put("username", username);
        userData.put("email", email);
        userData.put("role", role);

        databaseReference.child(userId).setValue(userData)
                .addOnCompleteListener(task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        saveUserId(userId);  // Save user ID to SharedPreferences
                        navigateToDashboard(role, userId);  // Navigate based on the user role
                    } else {
                        Toast.makeText(RegisterActivity.this, "Failed to register. Please try again.", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void saveUserId(String userId) {
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("UserID", userId);
        editor.apply();
    }

    private void navigateToDashboard(String role, String userId) {
        Intent intent;
        if ("shelter_owner".equals(role)) {
            intent = new Intent(RegisterActivity.this, ShelterOwnerDashboardActivity.class);
        } else {
            intent = new Intent(RegisterActivity.this, PetOwnerViewActivity.class);
        }
        intent.putExtra("UserID", userId);
        startActivity(intent);
        finish();
    }
}
