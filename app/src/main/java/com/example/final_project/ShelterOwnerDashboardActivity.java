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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_owner_dashboard);

        manageSheltersButton = findViewById(R.id.manageSheltersButton);
        addPetsButton = findViewById(R.id.addPetsButton);
        viewAdoptionsButton = findViewById(R.id.viewAdoptionsButton);

        manageSheltersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to navigate to ManageSheltersActivity
//                startActivity(new Intent(ShelterOwnerDashboardActivity.this, ManageSheltersActivity.class));
            }
        });

        addPetsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to navigate to AddPetActivity
//                startActivity(new Intent(ShelterOwnerDashboardActivity.this, AddPetActivity.class));
            }
        });

        viewAdoptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to navigate to ViewAdoptionsActivity
//                startActivity(new Intent(ShelterOwnerDashboardActivity.this, ViewAdoptionsActivity.class));
            }
        });
    }
}
