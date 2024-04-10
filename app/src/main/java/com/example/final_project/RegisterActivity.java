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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
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
        String role = radioButtonShelterOwner.isChecked() ? "shelter_owner" : "pet_owner";

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        // Registration succeeded, user is now the current user
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            // Construct a new User object or a Map to represent the user
                            Map<String, Object> userData = new HashMap<>();
                            userData.put("username", username);
                            userData.put("email", email);
                            userData.put("role", role);


                            // Add a new document with UID as the document ID in "Users" collection
                            firestore.collection("Users").document(user.getUid()).set(userData)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();

                                        // Direct user to the appropriate activity based on their role
                                        Intent intent = role.equals("shelterOwner") ?
                                                new Intent(RegisterActivity.this, ShelterOwnerDashboardActivity.class) :
                                                new Intent(RegisterActivity.this, PetOwnerViewActivity.class);
                                        startActivity(intent);
                                        finish(); // Finish the registration activity
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.e("RegisterActivity", "Error adding user document", e);
                                        Toast.makeText(RegisterActivity.this, "Failed to create user profile", Toast.LENGTH_LONG).show();
                                    });
                        } else {
                            Toast.makeText(RegisterActivity.this, "User registration succeeded but user data is unavailable", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e("RegisterActivity", "Registration failed", task.getException());
                        Toast.makeText(RegisterActivity.this, "Registration failed. Please try again.", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
