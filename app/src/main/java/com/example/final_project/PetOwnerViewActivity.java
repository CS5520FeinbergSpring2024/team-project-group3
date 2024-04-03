package com.example.final_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class PetOwnerViewActivity extends AppCompatActivity {

    private Button browseSheltersButton;
    private Button viewPetsButton;
    private Button learnAboutAdoptionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_owner_view);

        // Initialize UI Components
        browseSheltersButton = findViewById(R.id.browseSheltersButton);
        viewPetsButton = findViewById(R.id.viewPetsButton);
        learnAboutAdoptionButton = findViewById(R.id.learnAboutAdoptionButton);

        // Set listeners for each button
        setButtonListeners();
    }

    private void setButtonListeners() {
        // Navigate to a list of shelters
        browseSheltersButton.setOnClickListener(v ->
                startActivity(new Intent(PetOwnerViewActivity.this, ShelterListActivity.class)));

        // View pets from a selected or default shelter
        viewPetsButton.setOnClickListener(v -> {
            String shelterId = getSelectedShelterId();
            Intent intent = new Intent(PetOwnerViewActivity.this, PetListActivity.class);
            intent.putExtra("shelterId", shelterId); // Pass the selected shelter ID
            startActivity(intent);
        });

        // Learn about the adoption process
        learnAboutAdoptionButton.setOnClickListener(v ->
                startActivity(new Intent(PetOwnerViewActivity.this, AdoptionLessonActivity.class)));
    }

    private void saveSelectedShelterId(String shelterId) {
        SharedPreferences sharedPref = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("SELECTED_SHELTER_ID", shelterId);
        editor.apply();
    }

    private String getSelectedShelterId() {
        // This method returns a selected or default shelter ID
        // To implement dynamic selection, modify this method to return the user's choice
        SharedPreferences sharedPref = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
        // Assuming "defaultShelterId" is a placeholder. Replace it with actual logic to get the default or selected ID.
        return sharedPref.getString("SELECTED_SHELTER_ID", "defaultShelterId");
    }
}

