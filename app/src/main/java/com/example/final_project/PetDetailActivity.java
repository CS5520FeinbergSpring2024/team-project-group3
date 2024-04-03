package com.example.final_project;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class PetDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_detail);

        Pet pet = getIntent().getParcelableExtra("petData");
        if (pet != null) {
            ((TextView) findViewById(R.id.pet_name)).setText(pet.getName());
            ((TextView) findViewById(R.id.pet_description)).setText(pet.getDescription());
            ((TextView) findViewById(R.id.pet_breed)).setText(pet.getBreed());
            ((TextView) findViewById(R.id.pet_age)).setText(String.format("%s years old", pet.getAge()));
            ((TextView) findViewById(R.id.pet_gender)).setText(pet.getGender());

            ImageView petImageView = findViewById(R.id.pet_image);
            Glide.with(this).load(pet.getImageUrl()).into(petImageView);
        }
    }
}
