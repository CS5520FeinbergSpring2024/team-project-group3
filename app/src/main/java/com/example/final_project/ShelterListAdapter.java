package com.example.final_project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import java.util.List;

import javax.annotation.Nullable;

/**
 * adapter that binds shelter data to create list items.
 */
public class ShelterListAdapter extends ArrayAdapter<shelterData> {

    public ShelterListAdapter(Context context, List<shelterData> shelterDataList) {
        super(context, 0, shelterDataList);
    }

    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_shelter, parent, false);
        }
        shelterData currentShelterData = getItem(position);

        // initialize all the views.
        TextView shelterNameTextView = listItemView.findViewById(R.id.text_view_shelter_name);
        TextView shelterLocationTextView = listItemView.findViewById(R.id.text_view_shelter_location);
        ImageView shelterImageView = listItemView.findViewById(R.id.image_view_shelter);

        // bind data to the views.
        shelterNameTextView.setText(currentShelterData.getName());
        shelterLocationTextView.setText(currentShelterData.getLocation());

        // Load pet image using Glide
        Glide.with(getContext())
                .load(currentShelterData.getImageUrl())
                .placeholder(R.drawable.brokenlink)
                .into(shelterImageView);

        listItemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                shelterData currentShelterData = getItem(position);
                Intent intent = new Intent(getContext(), ShelterActivity.class);
                intent.putExtra("shelterData", currentShelterData);
                getContext().startActivity(intent);
            }
        });

        return listItemView;

    }
}
