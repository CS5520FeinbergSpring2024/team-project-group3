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

public class ShelterAdapter extends RecyclerView.Adapter<ShelterAdapter.ShelterViewHolder> {

    private List<Shelter> shelterList;
    private Context context;
    private OnShelterClickListener listener;

    public interface OnShelterClickListener {
        void onShelterClicked(Shelter shelter);
    }

    public ShelterAdapter(List<Shelter> shelterList, Context context, OnShelterClickListener listener) {
        this.shelterList = shelterList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ShelterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shelter_item, parent, false);
        return new ShelterViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ShelterViewHolder holder, int position) {
        Shelter shelter = shelterList.get(position);
        holder.bind(shelter);
    }

    @Override
    public int getItemCount() {
        return shelterList.size();
    }

    static class ShelterViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView locationTextView;
        ImageView imageView;

        ShelterViewHolder(View itemView, OnShelterClickListener listener) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.shelterNameTextView);
            locationTextView = itemView.findViewById(R.id.shelterLocationTextView);
            imageView = itemView.findViewById(R.id.shelterImageView);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onShelterClicked((Shelter) itemView.getTag());
                }
            });
        }

        void bind(Shelter shelter) {
            nameTextView.setText(shelter.getName());
            locationTextView.setText(shelter.getLocation());
            Glide.with(itemView.getContext()).load(shelter.getImageUrl()).into(imageView);
            itemView.setTag(shelter); // Set the shelter as the tag for the View
        }
    }
}
