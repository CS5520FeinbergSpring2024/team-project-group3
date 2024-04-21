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

public class PetsAdapter extends RecyclerView.Adapter<PetsAdapter.ViewHolder> {

    private final Context context;
    public List<Pet> petList;
    private final OnPetClickListener clickListener;

    public PetsAdapter(Context context, List<Pet> petList, OnPetClickListener clickListener) {
        this.context = context;
        this.petList = petList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pet_type, parent, false);
        return new ViewHolder(view);
    }

    public void updatePets(List<Pet> pets) {
        this.petList = pets;
        notifyDataSetChanged();
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pet pet = petList.get(position);
        if (holder.petName != null) {
            holder.petName.setText(pet.getName()); // Use petName, updated to match XML
        }
        if (holder.petImage != null) {
            // Use Glide to load the pet image dynamically from a URL provided by the pet data
            Glide.with(context)
                    .load(pet.getImageUrl())
                    .placeholder(R.drawable.dog_type) // Fallback image
                    .into(holder.petImage);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    Pet pet = petList.get(holder.getAdapterPosition());
                    clickListener.onPetClick(pet);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return petList != null ? petList.size() : 0;
    }

    public interface OnPetClickListener {
        void onPetClick(Pet pet);
    }





    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView petName;
        ImageView petImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            petName = itemView.findViewById(R.id.petTypeName);
            petImage = itemView.findViewById(R.id.petTypeImage);
        }
    }

}

