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
    private Button chatWithSheltersButton; // Button for navigating to chat list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_owner_view);

        initializeButtons();
        setButtonListeners();
    }

    private void initializeButtons() {
        browseSheltersButton = findViewById(R.id.browseSheltersButton);
        viewPetsButton = findViewById(R.id.viewPetsButton);
        learnAboutAdoptionButton = findViewById(R.id.learnAboutAdoptionButton);
        chatWithSheltersButton = findViewById(R.id.chatWithSheltersButton);
    }

    private void setButtonListeners() {
        browseSheltersButton.setOnClickListener(v -> {

            // This navigates to ShelterListActivity which now incorporates geolocation to display nearby shelters
            startActivity(new Intent(PetOwnerViewActivity.this, ShelterListActivity.class));
        });

        viewPetsButton.setOnClickListener(v -> {
            // This functionality can remain as it was, unless it needs to be updated for other reasons
        });

        learnAboutAdoptionButton.setOnClickListener(v -> {
            // This button navigates to a static informational activity about pet adoption
            startActivity(new Intent(PetOwnerViewActivity.this, AdoptionLessonActivity.class));
        });

        chatWithSheltersButton.setOnClickListener(v -> {
            // Navigate to ChatListActivity where the user can see a list of their chats with shelters
            startActivity(new Intent(PetOwnerViewActivity.this, ChatListActivity.class));
        });
    }
}

