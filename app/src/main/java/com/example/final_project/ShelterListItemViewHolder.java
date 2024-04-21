package com.example.final_project;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class ShelterListItemViewHolder extends RecyclerView.ViewHolder {
    private TextView shelterNameTextView;
    private TextView shelterLocationTextView;
    private ImageView shelterImageView;

    public ShelterListItemViewHolder(@NonNull View itemView) {
        super(itemView);

        // Initialize views
        shelterNameTextView = itemView.findViewById(R.id.text_view_shelter_name);
        shelterLocationTextView = itemView.findViewById(R.id.text_view_shelter_location);
        shelterImageView = itemView.findViewById(R.id.image_view_shelter);
    }

    // Method to bind data to the views within a list item
    public void bindData(Shelter shelter) {
        shelterNameTextView.setText(shelter.getName());
        shelterLocationTextView.setText(shelter.getLocation());

        // Load pet image using Glide
        Glide.with(itemView.getContext())
                .load(shelter.getImageUrl())
                .placeholder(R.drawable.brokenlink) // Placeholder image
                .into(shelterImageView);
    }
}

