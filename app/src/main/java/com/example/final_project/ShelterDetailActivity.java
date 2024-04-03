package com.example.final_project;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class ShelterDetailActivity extends AppCompatActivity {

    private TextView nameTextView, locationTextView, descriptionTextView, phoneNumberTextView, addressTextView, yearOfBusinessTextView;
    private ImageView imageView;

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

        Shelter shelter = getIntent().getParcelableExtra("shelter");
        if (shelter != null) {
            nameTextView.setText(shelter.getName());
            locationTextView.setText(shelter.getLocation());
            descriptionTextView.setText(shelter.getDescription());
            phoneNumberTextView.setText(shelter.getPhoneNumber());
            addressTextView.setText(shelter.getAddress());
            yearOfBusinessTextView.setText(shelter.getYearOfBusiness());
            Glide.with(this).load(shelter.getImageUrl()).into(imageView);
        }
    }
}
