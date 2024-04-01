package com.example.final_project;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class PetActivity extends AppCompatActivity {
    TextView petName;
    ImageView petImage;
    TextView petDescription;
    TextView petGender;
    TextView petAge;
    TextView petBreed;
    TextView petFee;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet);
        petData currentPetData = getIntent().getParcelableExtra("currentPet");
        petName = findViewById(R.id.pet_name_text_view);
        petDescription = findViewById(R.id.pet_description_text);
        petGender = findViewById(R.id.pet_gender_text_view);
        petAge = findViewById(R.id.pet_age_text_view);
        petBreed = findViewById(R.id.pet_breed_text_view);
        petFee = findViewById(R.id.pet_fee_text);
        petImage = findViewById(R.id.pet_image_view);

        petName.setText(currentPetData.getName());
        petDescription.setText(currentPetData.getDescription());
        petGender.setText("Gender: " + currentPetData.getGender());
        petAge.setText("Age: " + currentPetData.getAge());
        petFee.setText("Price: "+ currentPetData.getPrice());
        petBreed.setText("Breed: " + currentPetData.getBreed());

        Glide.with(PetActivity.this)
                .load(currentPetData.getImageUrl())
                .placeholder(R.drawable.brokenlink)
                .into(petImage);

    }
}
