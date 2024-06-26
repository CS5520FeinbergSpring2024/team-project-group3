package com.example.final_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LessonsAdapter extends RecyclerView.Adapter<LessonsAdapter.LessonViewHolder> {
    private List<Lesson> lessonsList;
    private Context context;

    public LessonsAdapter(List<Lesson> lessonsList, Context context) {
        this.lessonsList = lessonsList;
        this.context = context;
    }

    @NonNull
    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lesson_item, parent, false);
        return new LessonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonViewHolder holder, int position) {
        Lesson lesson = lessonsList.get(position);
        holder.title.setText(lesson.getTitle());
        holder.description.setText(lesson.getDescription());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, LessonDetailActivity.class);
            intent.putExtra("lessonId", lesson.getLessonID());
            intent.putExtra("userId", getCurrentUserId());
            context.startActivity(intent);
        });
    }

    private String getCurrentUserId() {
        SharedPreferences prefs = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        return prefs.getString("UserID", null);  // Return null or a default value if no user ID is found
    }


    @Override
    public int getItemCount() {
        return lessonsList.size();
    }

    static class LessonViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;

        LessonViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.lessonTitle);
            description = itemView.findViewById(R.id.lessonDescription);
        }
    }
}

