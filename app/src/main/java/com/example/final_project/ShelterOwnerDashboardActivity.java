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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_owner_dashboard);

        manageSheltersButton = findViewById(R.id.manageSheltersButton);
        addPetsButton = findViewById(R.id.addPetsButton);
        viewAdoptionsButton = findViewById(R.id.viewAdoptionsButton);
        managePetsButton = findViewById(R.id.managePetsButton);

        manageSheltersButton.setOnClickListener(v -> startActivity(new Intent(ShelterOwnerDashboardActivity.this, ManageSheltersActivity.class)));

        addPetsButton.setOnClickListener(v -> startActivity(new Intent(ShelterOwnerDashboardActivity.this, PetRegistrationActivity.class)));

        viewAdoptionsButton.setOnClickListener(v -> startActivity(new Intent(ShelterOwnerDashboardActivity.this, ViewAdoptionsActivity.class)));

        managePetsButton.setOnClickListener(v -> startActivity(new Intent(ShelterOwnerDashboardActivity.this, PetManagementActivity.class)));
    }
}

