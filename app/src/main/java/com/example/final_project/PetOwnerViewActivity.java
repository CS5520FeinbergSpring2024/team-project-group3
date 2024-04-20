package com.example.final_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class PetOwnerViewActivity extends AppCompatActivity {

    private Button browseSheltersButton;
    private Button viewPetsButton;
    private Button learnAboutAdoptionButton;
    private Button chatWithSheltersButton;
    private String currentUserId; // Variable to hold the user ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_owner_view);

        // Retrieve the user ID from the intent
        currentUserId = getIntent().getStringExtra("UserID");
        if (currentUserId == null) {
            finish(); // Redirect to login screen or show an error because user ID is crucial
            return;
        }

        initializeButtons();
        setButtonListeners();
    }

    private void initializeButtons() {
        browseSheltersButton = findViewById(R.id.browseSheltersButton);
        learnAboutAdoptionButton = findViewById(R.id.learnAboutAdoptionButton);
        chatWithSheltersButton = findViewById(R.id.chatWithSheltersButton);
    }

    private void setButtonListeners() {
        browseSheltersButton.setOnClickListener(v -> {
            // Pass the user ID to the ShelterListActivity
            Intent intent = new Intent(PetOwnerViewActivity.this, ShelterListActivity.class);
            intent.putExtra("UserID", currentUserId);
            startActivity(intent);
        });


        learnAboutAdoptionButton.setOnClickListener(v -> {
            // Pass the user ID if necessary or just open the static informational activity
            Intent intent = new Intent(PetOwnerViewActivity.this, AdoptionLessonActivity.class);
            intent.putExtra("UserID", currentUserId);
            startActivity(intent);
        });

        chatWithSheltersButton.setOnClickListener(v -> {
            // Navigate to ChatListActivity, passing the user ID
            Intent intent = new Intent(PetOwnerViewActivity.this, ChatListActivity.class);
            intent.putExtra("UserID", currentUserId);
            startActivity(intent);
        });
    }
}

