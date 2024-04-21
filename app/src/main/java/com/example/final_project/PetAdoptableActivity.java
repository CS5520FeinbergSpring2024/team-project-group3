package com.example.final_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class PetAdoptableActivity extends AppCompatActivity {
    private TextView shelterName;
    private ImageView shelterImage;
    private TextView adoptionText;

    private ListView availablePetsList;

    private Button homeBtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_adoptable);
        shelterData shelterData = getIntent().getParcelableExtra("shelterData");
//        Log.d("adoptable pet size:", String.valueOf(shelterData.getAdoptablePets().size()));

        shelterName = findViewById(R.id.shelter_name_text);
        adoptionText = findViewById(R.id.adaptable_pet_text);
        shelterImage = findViewById(R.id.shelter_image);
        availablePetsList = findViewById(R.id.available_pets_list);
        homeBtn = findViewById(R.id.homeButton);


        if (shelterData.getName().isEmpty() || shelterData.getName() == null) {
            shelterName.setText("Name not displayed");
        } else {
            shelterName.setText(shelterData.getName());
        }
        Log.d("shelterData", shelterData.getName());
        adoptionText.setText("Pets Available for Adoption"); // redundant actually.

        homeBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetAdoptableActivity.this, BreedViewActivity.class);
                PetAdoptableActivity.this.startActivity(intent);
            }
        }));

        Glide.with(PetAdoptableActivity.this)
                .load(shelterData.getImageUrl())
                .placeholder(R.drawable.brokenlink)
                .into(shelterImage);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PetAdoptableAdapter adapter = new PetAdoptableAdapter(PetAdoptableActivity.this, shelterData.getAdoptablePets());
                adapter.notifyDataSetChanged();
                availablePetsList.setAdapter(adapter);
            }
        });



    }
}