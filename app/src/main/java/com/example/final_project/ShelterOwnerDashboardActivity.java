package com.example.final_project;

import android.content.Intent;
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
        currentUserId = getIntent().getStringExtra("USER_ID");
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

        manageSheltersButton.setOnClickListener(v -> startActivity(new Intent(ShelterOwnerDashboardActivity.this, ManageSheltersActivity.class).putExtra("USER_ID", currentUserId)));

        addPetsButton.setOnClickListener(v -> startActivity(new Intent(ShelterOwnerDashboardActivity.this, PetRegistrationActivity.class).putExtra("USER_ID", currentUserId)));

        viewAdoptionsButton.setOnClickListener(v -> startActivity(new Intent(ShelterOwnerDashboardActivity.this, ViewAdoptionsActivity.class).putExtra("USER_ID", currentUserId)));

        managePetsButton.setOnClickListener(v -> {
            Intent intent = new Intent(ShelterOwnerDashboardActivity.this, PetManagementActivity.class);
            intent.putExtra("USER_ID", currentUserId);
            startActivity(intent);
        });

        viewChatsButton.setOnClickListener(v -> startActivity(new Intent(ShelterOwnerDashboardActivity.this, ChatListActivity.class).putExtra("USER_ID", currentUserId)));
    }
}



