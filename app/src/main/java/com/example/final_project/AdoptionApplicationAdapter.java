package com.example.final_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AdoptionApplicationAdapter extends RecyclerView.Adapter<AdoptionApplicationAdapter.ViewHolder> {

    private List<AdoptionApplication> applications;

    public AdoptionApplicationAdapter(List<AdoptionApplication> applications) {
        this.applications = applications;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adoption_application_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AdoptionApplication application = applications.get(position);
        holder.applicantName.setText(application.getApplicantName());
        holder.applicantAddress.setText(application.getApplicantAddress());
        holder.status.setText(application.getStatus());
        holder.petId.setText(application.getPetId());
        holder.userId.setText(application.getUserId());
        holder.shelterId.setText(application.getShelterId());
    }

    @Override
    public int getItemCount() {
        return applications.size();
    }

    public void setAdoptionApplications(List<AdoptionApplication> applications) {
        this.applications = applications;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView applicantName, applicantAddress, status, petId, userId, shelterId;

        public ViewHolder(View view) {
            super(view);
            applicantName = view.findViewById(R.id.applicantNameTextView);
            applicantAddress = view.findViewById(R.id.applicantAddressTextView);
            status = view.findViewById(R.id.statusTextView);
            petId = view.findViewById(R.id.petIdTextView); // Assuming this TextView is added
            userId = view.findViewById(R.id.userIdTextView); // Assuming this TextView is added
            shelterId = view.findViewById(R.id.shelterIdTextView); // Assuming this TextView is added
        }
    }
}
