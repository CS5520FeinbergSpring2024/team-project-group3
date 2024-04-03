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
    private List<shelterData> shelterDataList;

    public ShelterListAdapter(Context context, List<shelterData> shelterDataList) {
        this.context = context;
        this.shelterDataList = shelterDataList;
    }

    @NonNull
    @Override
    public ShelterListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItemView = LayoutInflater.from(context).inflate(R.layout.list_item_shelter, parent, false);
        return new ShelterListItemViewHolder(listItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShelterListItemViewHolder holder, int position) {
        shelterData currentShelterData = shelterDataList.get(position);
        holder.bindData(currentShelterData);

        // Item click listener to view shelter details
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ShelterActivity.class);
            intent.putExtra("shelterData", currentShelterData);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return shelterDataList.size();
    }

    // Method to update the list of shelters
    public void setShelterList(List<shelterData> shelterDataList) {
        this.shelterDataList = shelterDataList;
        notifyDataSetChanged(); // Notify the RecyclerView about data changes
    }
}