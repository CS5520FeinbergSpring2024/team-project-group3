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
    private Button chatWithSheltersButton; // New button for chats

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_owner_view);

        browseSheltersButton = findViewById(R.id.browseSheltersButton);
        viewPetsButton = findViewById(R.id.viewPetsButton);
        learnAboutAdoptionButton = findViewById(R.id.learnAboutAdoptionButton);
        chatWithSheltersButton = findViewById(R.id.chatWithSheltersButton); // Initialize the chat button

        setButtonListeners();
    }

    private void setButtonListeners() {
        browseSheltersButton.setOnClickListener(v ->
                startActivity(new Intent(PetOwnerViewActivity.this, ShelterListActivity.class)));

        viewPetsButton.setOnClickListener(v -> {
            // Implementation remains the same as before, ensuring pet listing functionality
        });

        learnAboutAdoptionButton.setOnClickListener(v ->
                startActivity(new Intent(PetOwnerViewActivity.this, AdoptionLessonActivity.class)));

        chatWithSheltersButton.setOnClickListener(v ->
                startActivity(new Intent(PetOwnerViewActivity.this, ChatListActivity.class))); // Navigate to ChatListActivity
    }
}

