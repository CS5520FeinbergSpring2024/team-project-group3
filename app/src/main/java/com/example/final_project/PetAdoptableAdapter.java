package com.example.final_project;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.List;

public class PetAdoptableAdapter extends ArrayAdapter<petData> {

    public PetAdoptableAdapter(Context context, List<petData> petList) {
        super(context, 0, petList);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_pet, parent, false);
        }

        petData currentPet = getItem(position);

        ImageView petImageView = listItemView.findViewById(R.id.pet_image);
        TextView petNameTextView = listItemView.findViewById(R.id.pet_name_text);
        TextView petGenderTextView = listItemView.findViewById(R.id.pet_gender_text);
        TextView petAgeTextView = listItemView.findViewById(R.id.pet_age_text);
        TextView petBreedTextView = listItemView.findViewById(R.id.pet_breed_text);

        Glide.with(getContext())
                .load(currentPet.getImageUrl())
                .placeholder(R.drawable.brokenlink)
                .into(petImageView);

        petNameTextView.setText(currentPet.getName());
        petGenderTextView.setText("Gender: " + currentPet.getGender());
        petAgeTextView.setText("Age: " + currentPet.getAge());
        petBreedTextView.setText("Breed: " + currentPet.getBreed());

        listItemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                petData currentPet = getItem(position);
                Intent intent = new Intent(getContext(), PetActivity.class);
                intent.putExtra("currentPet", currentPet);
                getContext().startActivity(intent);
            }
        });

        return listItemView;
    }
}
