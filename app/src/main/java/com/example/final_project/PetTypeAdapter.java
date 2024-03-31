package com.example.final_project;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Pet type adapter class that bind required elements to generate list of pet types.
 */
public class PetTypeAdapter extends RecyclerView.Adapter<PetTypeAdapter.PetTypeViewHolder> {
    private List<PetType> petTypes;
    private Context context;

    /**
     * Constructs a new PetTypeAdapter.
     * @param context  The context of where the adapter is constructed.
     * @param petTypes The list of PetType objects to be displayed by the adapter.
     */
    public PetTypeAdapter(Context context, List<PetType> petTypes) {
        this.context = context;
        this.petTypes = petTypes;
    }

    /**
     * method for when recyclerView needs a new viewHolder of a given view type.
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return a new viewholder that holds a view of that given view type.
     */
    @NonNull
    @Override
    public PetTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pet_type, parent, false);
        return new PetTypeViewHolder(view);
    }

    /**
     * Helps to update the contents of the view.
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */

    @Override
    public void onBindViewHolder(@NonNull PetTypeViewHolder holder, int position) {
        PetType petType = petTypes.get(position);
        holder.bind(petType);
//        Log.d("Pet Image URL", petType.getImageUrl());
    }

    @Override
    public int getItemCount() {
        return petTypes.size();
    }

    /**
     * ViewHolder class to hold views for each item in the RecyclerView.
     */
    static class PetTypeViewHolder extends RecyclerView.ViewHolder {
        private ImageView petImage;
        private TextView petName;

        /**
         * @param itemView The view representing a single item in the RecyclerView.
         */
        public PetTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            petImage = itemView.findViewById(R.id.petTypeImage);
            petName = itemView.findViewById(R.id.petTypeName);

        }

        /**
         * Binds elements to the views in the ViewHolder.
         * @param petType The PetType object containing data to be displayed.
         */
        public void bind(final PetType petType) {
            petName.setText(petType.getName());
            int resourceId = itemView.getContext().getResources().getIdentifier(petType.getImageUrl(), "drawable", itemView.getContext().getPackageName());
            if (resourceId != 0) {
                petImage.setImageResource(resourceId);
            } else {
                petImage.setImageResource(R.drawable.ic_launcher_foreground);
            }

            // make pet image clickable and call api methods to fetch data.
            petImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BreedApi api = new BreedApi();
                    api.fetchBreedData(petType.getName(), new BreedApi.FetchBreedDataCallback() {
                        @Override
                        public void onDataFetched(List<petData> petDataList) {
                            BreedViewActivity act = (BreedViewActivity) itemView.getContext();
                            act.updateListView(petDataList);

                        }
                    });
                    Toast.makeText(itemView.getContext(), "Selected Pet Type: " + petType.getName(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}


