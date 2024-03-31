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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_owner_view);

        browseSheltersButton = findViewById(R.id.browseSheltersButton);
        viewPetsButton = findViewById(R.id.viewPetsButton);
        learnAboutAdoptionButton = findViewById(R.id.learnAboutAdoptionButton);

        browseSheltersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to navigate to BrowseSheltersActivity
//                startActivity(new Intent(PetOwnerViewActivity.this, ShelterListActivity.class));
            }
        });

        viewPetsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to navigate to ViewPetsActivity, which could be a detailed list or map
//                startActivity(new Intent(PetOwnerViewActivity.this, PetListActivity.class));
            }
        });

        learnAboutAdoptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to navigate to LearnAboutAdoptionActivity
                startActivity(new Intent(PetOwnerViewActivity.this, AdoptionLessonActivity.class));
            }
        });
    }
}
