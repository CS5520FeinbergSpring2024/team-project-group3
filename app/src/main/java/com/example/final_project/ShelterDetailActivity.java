package com.example.final_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class ShelterDetailActivity extends AppCompatActivity {

    private TextView nameTextView, locationTextView, descriptionTextView, phoneNumberTextView, addressTextView, yearOfBusinessTextView;
    private ImageView imageView;
    private Button viewPetsButton;
    private String shelterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_detail);

        nameTextView = findViewById(R.id.shelterNameDetailTextView);
        locationTextView = findViewById(R.id.shelterLocationDetailTextView);
        descriptionTextView = findViewById(R.id.shelterDescriptionTextView);
        phoneNumberTextView = findViewById(R.id.shelterPhoneTextView);
        addressTextView = findViewById(R.id.shelterAddressTextView);
        yearOfBusinessTextView = findViewById(R.id.shelterYearTextView);
        imageView = findViewById(R.id.shelterDetailImageView);
        viewPetsButton = findViewById(R.id.viewPetsButton);

        // Get the shelter object from the intent
        Shelter shelter = getIntent().getParcelableExtra("shelterData");

        if (shelter != null) {
            shelterId = shelter.getId(); // Assume Shelter class has getShelterId()
            nameTextView.setText(shelter.getName());
            locationTextView.setText(shelter.getLocation());
            descriptionTextView.setText(shelter.getDescription());
            phoneNumberTextView.setText(shelter.getPhoneNumber());
            addressTextView.setText(shelter.getAddress());
            yearOfBusinessTextView.setText(String.valueOf(shelter.getYearOfBusiness()));
            Glide.with(this).load(shelter.getImageUrl()).into(imageView);

            viewPetsButton.setOnClickListener(v -> {
                Intent intent = new Intent(ShelterDetailActivity.this, PetListActivity.class);
                intent.putExtra("shelterId", shelterId);
                startActivity(intent);
            });
        }
    }
}

