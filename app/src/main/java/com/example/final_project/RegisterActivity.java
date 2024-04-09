package com.example.final_project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextEmail, editTextPassword, editTextUsername;
    private RadioButton radioButtonShelterOwner, radioButtonPetOwner;
    private Button buttonRegister;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        FirebaseApp.initializeApp(this);
        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(PlayIntegrityAppCheckProviderFactory.getInstance());
        progressDialog = new ProgressDialog(this);

        // Initialization of UI components
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextUsername = findViewById(R.id.editTextUsername);
        radioButtonShelterOwner = findViewById(R.id.radioButtonShelterOwner);
        radioButtonPetOwner = findViewById(R.id.radioButtonPetOwner);
        buttonRegister = findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(view -> registerUser());
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String username = editTextUsername.getText().toString().trim();
        String role = radioButtonShelterOwner.isChecked() ? "shelterOwner" : "petOwner";

        // Basic input validation
        if (email.isEmpty() || password.isEmpty() || username.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Advanced email validation
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (!Pattern.compile(emailRegex).matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            String userId = user.getUid();
                            firestore.collection("Users").document(userId)
                                    .set(new User(username, email, role, new HashMap<>()))
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();

                                        // Intent Logic
                                        Intent intent = role.equals("shelterOwner") ? new Intent(RegisterActivity.this, ShelterOwnerDashboardActivity.class) : new Intent(RegisterActivity.this, PetOwnerViewActivity.class);
                                        startActivity(intent);
                                        finish(); // Close the registration activity
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.e("RegisterActivity", "Failed to save user data:", e);
                                        Toast.makeText(RegisterActivity.this, "Registration failed. Please try again.", Toast.LENGTH_LONG).show();
                                    });
                        } else {
                            Log.e("RegisterActivity", "User object is null after authentication");
                            Toast.makeText(RegisterActivity.this, "An error occurred. Please try again.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Log.e("RegisterActivity", "Authentication failed:", task.getException());
                        Toast.makeText(RegisterActivity.this, "Authentication failed. Please check your email and password.", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
