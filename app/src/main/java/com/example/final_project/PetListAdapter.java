package com.example.final_project;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;

/**
 * Breed list adapter that helps bind breed information to the list.
 */
public class PetListAdapter extends ArrayAdapter<petData> {
    private List<petData> petDataList;
    public PetListAdapter(Context context, List<petData> petDataList) {
        super(context, 0, petDataList);
        this.petDataList = petDataList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_pet, parent, false);
        }

        petData currentPetData = getItem(position);

        // initialize list elements.
        TextView petNameTextView = listItemView.findViewById(R.id.text_view_pet_name);
        TextView petDescriptionTextView = listItemView.findViewById(R.id.text_view_pet_description);
        ImageView petImageView = listItemView.findViewById(R.id.image_view_pet);

        // set list elements.
        petNameTextView.setText(currentPetData.getBreed());
        petDescriptionTextView.setText(currentPetData.getDescription());
        String imgUrl = "https://cdn2.the"+currentPetData.getType()+"api.com/images/"+ currentPetData.getImageURL()+".jpg";
        // Load pet image using Glide
        Glide.with(getContext())
                .load(imgUrl)
                        .placeholder(R.drawable.brokenlink)
                .into(petImageView);

        // make each list item clickable so that each of them opens a new shelter activity
        listItemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                petData selectedBreed = getItem(position);
//                Log.d("breed list", "started");
                Intent intent = new Intent(getContext(), ShelterActivity.class);
                intent.putExtra("breed_name", selectedBreed.getBreed());
                getContext().startActivity(intent);
            }
        });
        return listItemView;
    }

    public void clear() {
        petDataList.clear();
        notifyDataSetChanged();
    }
}

