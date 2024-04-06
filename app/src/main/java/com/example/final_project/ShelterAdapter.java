package com.example.final_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Locale;

public class ShelterAdapter extends RecyclerView.Adapter<ShelterAdapter.ShelterViewHolder> {

    private List<Shelter> shelterList;
    private final Context context;
    private final OnShelterClickListener listener;

    public interface OnShelterClickListener {
        void onShelterClicked(Shelter shelter);
    }

    public ShelterAdapter(List<Shelter> shelterList, Context context, OnShelterClickListener listener) {
        this.shelterList = shelterList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ShelterViewHolder holder, int position) {
        Shelter shelter = shelterList.get(position);
        holder.bind(context, shelter, listener);
    }


    @NonNull
    @Override
    public ShelterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shelter_item, parent, false);
        return new ShelterViewHolder(view);
    }

    // Method to update the list of shelters and refresh the RecyclerView
    public void setShelterList(List<Shelter> newShelterList) {
        this.shelterList = newShelterList;
        notifyDataSetChanged(); // Notify the adapter to refresh the RecyclerView
    }


    @Override
    public int getItemCount() {
        return shelterList.size();
    }

    static class ShelterViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView locationTextView;
        ImageView imageView;

        ShelterViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.shelterNameTextView);
            locationTextView = itemView.findViewById(R.id.shelterLocationTextView);
            imageView = itemView.findViewById(R.id.shelterImageView);
        }

        void bind(Context context, Shelter shelter, OnShelterClickListener listener) {
            nameTextView.setText(shelter.getName());
            locationTextView.setText(shelter.getLocation());
            Glide.with(context).load(shelter.getImageUrl()).into(imageView);
            itemView.setOnClickListener(v -> listener.onShelterClicked(shelter));
        }
    }

}

