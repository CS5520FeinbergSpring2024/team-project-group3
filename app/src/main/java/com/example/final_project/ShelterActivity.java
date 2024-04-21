package com.example.final_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

/**
 * Shelter detail.
 */

public class ShelterActivity extends AppCompatActivity {
    private TextView shelterNameText;
    private ImageView shelterImage;
    private TextView descriptionText;
    private Button petsButton;
    private TextView addressText;
    private TextView phoneText;

    private Button homeBtn;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter);
        shelterData shelterData = getIntent().getParcelableExtra("shelterData");
//        for(petData p : shelterData.getAdoptablePets()) {
//            Log.d("price of petdata", p.getPrice());
//        }

        shelterNameText = findViewById(R.id.shelter_name_text);
        shelterImage = findViewById(R.id.shelter_image);
        descriptionText = findViewById(R.id.description_text);
        petsButton = findViewById(R.id.adoption_button);
        addressText = findViewById(R.id.address_text);
        phoneText = findViewById(R.id.phone_number_text);
        homeBtn = findViewById(R.id.homeButton);

        shelterNameText.setText(shelterData.getName());
        descriptionText.setText(shelterData.getDescription());
        addressText.setText("Address: " + shelterData.getAddress());
        phoneText.setText("Phone Number: " + shelterData.getPhoneNumber());

        homeBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShelterActivity.this, BreedViewActivity.class);
                ShelterActivity.this.startActivity(intent);
            }
        }));

        Glide.with(ShelterActivity.this)
                .load(shelterData.getImageUrl())
                .placeholder(R.drawable.brokenlink)
                .into(shelterImage);

        petsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShelterActivity.this, PetAdoptableActivity.class);
                intent.putExtra("shelterData", shelterData);
                ShelterActivity.this.startActivity(intent);
            }
        });


    }
}
