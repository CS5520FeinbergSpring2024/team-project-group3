package com.example.final_project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShelterListAdapter extends RecyclerView.Adapter<ShelterListItemViewHolder> {

    private Context context;
    private List<Shelter> shelterList;

    public ShelterListAdapter(Context context, List<Shelter> shelterList) {
        this.context = context;
        this.shelterList = shelterList;
    }

    @NonNull
    @Override
    public ShelterListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItemView = LayoutInflater.from(context).inflate(R.layout.list_item_shelter, parent, false);
        return new ShelterListItemViewHolder(listItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShelterListItemViewHolder holder, int position) {
        Shelter currentShelter = shelterList.get(position);
        holder.bindData(currentShelter);

        // Item click listener to view shelter details
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ShelterDetailActivity.class);
            intent.putExtra("shelterData", currentShelter);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return shelterList.size();
    }

    // Method to update the list of shelters
    public void setShelterList(List<Shelter> shelterList) {
        this.shelterList = shelterList;
        notifyDataSetChanged(); // Notify the RecyclerView about data changes
    }
}