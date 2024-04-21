package com.example.final_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ShelterOwnerDashboardActivity extends AppCompatActivity {

    private Button manageSheltersButton;
    private Button addPetsButton;
    private Button viewAdoptionsButton;
    private Button managePetsButton;
    private Button viewChatsButton;
    private String currentUserId; // Variable to hold the user ID passed from the login or register activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_owner_dashboard);

        // Retrieve the user ID from the intent
        currentUserId = getIntent().getStringExtra("UserID");
        if (currentUserId == null) {
            // Log error or handle case where the user ID is not passed
            finish(); // or redirect to the login screen
            return;
        }




        manageSheltersButton = findViewById(R.id.manageSheltersButton);
        addPetsButton = findViewById(R.id.addPetsButton);
        viewAdoptionsButton = findViewById(R.id.viewAdoptionsButton);
        managePetsButton = findViewById(R.id.managePetsButton);
        viewChatsButton = findViewById(R.id.viewChatsButton);

        manageSheltersButton.setOnClickListener(v -> startActivity(new Intent(ShelterOwnerDashboardActivity.this, ManageSheltersActivity.class).putExtra("UserID", currentUserId)));

        addPetsButton.setOnClickListener( v -> {
                    Intent intent = new Intent(ShelterOwnerDashboardActivity.this, PetRegistrationActivity.class);
                    intent.putExtra("UserID", currentUserId);
                    startActivity(intent);
                } );


        viewAdoptionsButton.setOnClickListener(v -> startActivity(new Intent(ShelterOwnerDashboardActivity.this, ViewAdoptionsActivity.class).putExtra("UserID", currentUserId)));

        managePetsButton.setOnClickListener(v -> {
            Intent intent = new Intent(ShelterOwnerDashboardActivity.this, PetManagementActivity.class);
            intent.putExtra("UserID", currentUserId);
            startActivity(intent);
        });

        viewChatsButton.setOnClickListener(v -> startActivity(new Intent(ShelterOwnerDashboardActivity.this, ChatListActivity.class).putExtra("UserID", currentUserId)));
    }

    private void saveUserIdToPreferences(String userId) {
        SharedPreferences sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UserID", userId);
        editor.apply();
    }
}



