package com.example.final_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private Button buttonLogin;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseApp.initializeApp(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        editTextEmail = findViewById(R.id.editTextEmail);
        buttonLogin = findViewById(R.id.buttonLogin);
        Button buttonGoToRegister = findViewById(R.id.buttonGoToRegister);

        buttonLogin.setOnClickListener(view -> loginUser());
        buttonGoToRegister.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
    }

    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();

        // Basic email validation
        if (email.isEmpty()) {
            editTextEmail.setError("Email cannot be empty");
            return;
        }

        Query query = databaseReference.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        // Successfully logged in
                        handleSuccessfulLogin(userSnapshot);
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "User does not exist.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("LoginActivity", "Database error: " + databaseError.getMessage());
                Toast.makeText(LoginActivity.this, "Database error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleSuccessfulLogin(DataSnapshot userSnapshot) {
        String userId = userSnapshot.getKey();
        String role = userSnapshot.child("role").getValue(String.class);

        Intent intent;
        if ("shelter_owner".equals(role)) {
            intent = new Intent(LoginActivity.this, ShelterOwnerDashboardActivity.class);
        } else if ("pet_owner".equals(role)) {
            intent = new Intent(LoginActivity.this, PetOwnerViewActivity.class);
        } else {
            Toast.makeText(LoginActivity.this, "Undefined user role.", Toast.LENGTH_SHORT).show();
            return;
        }

        saveUserId(userId);
        intent.putExtra("UserID", userId);
        startActivity(intent);
        finish();
    }

    // Save user ID in SharedPreferences
    private void saveUserId(String userId) {
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("UserID", userId);
        editor.apply();
    }
}



