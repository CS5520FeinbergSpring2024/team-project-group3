package com.example.final_project;

import android.content.Context;
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
    }

    @Override
    public int getItemCount() {
        return lessonsList.size();
    }

    static class LessonViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;

        public LessonViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.lessonTitle);
            description = itemView.findViewById(R.id.lessonDescription);
        }
    }
}

