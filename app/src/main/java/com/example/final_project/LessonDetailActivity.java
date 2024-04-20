package com.example.final_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class LessonDetailActivity extends AppCompatActivity {
    private FirebaseFirestore firestore;
    private TextView titleTextView, contentTextView;
    private Button completeLessonButton;
    private String lessonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_detail);

        firestore = FirebaseFirestore.getInstance();

        titleTextView = findViewById(R.id.lessonTitleTextView);
        contentTextView = findViewById(R.id.lessonContentTextView);
        completeLessonButton = findViewById(R.id.completeLessonButton);

        lessonId = getIntent().getStringExtra("lessonId");
        if (lessonId != null) {
            fetchLesson(lessonId);
        }

        completeLessonButton.setOnClickListener(v -> markLessonCompleted(lessonId));
    }

    private void fetchLesson(String lessonId) {
        firestore.collection("AdoptionLessons").document(lessonId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    Lesson lesson = documentSnapshot.toObject(Lesson.class);
                    if (lesson != null) {
                        titleTextView.setText(lesson.getTitle());
                        contentTextView.setText(lesson.getDescription()); // Assuming the lesson object has a description field
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(LessonDetailActivity.this, "Failed to load lesson.", Toast.LENGTH_SHORT).show());
    }

    private void markLessonCompleted(String lessonId) {
        // Assuming userId is passed via Intent or obtained from a session manager
        String userId = getIntent().getStringExtra("userId");
        if (userId != null && !userId.isEmpty()) {
            // Set or update the lesson completion status
            firestore.collection("Users").document(userId)
                    .collection("CompletedLessons").document(lessonId)
                    .set(new HashMap<String, Object>() {{ put("completed", true); }})
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(LessonDetailActivity.this, "Lesson marked as completed!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(LessonDetailActivity.this, "Failed to mark lesson as completed. " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(this, "User ID is not available. Cannot mark lesson as completed.", Toast.LENGTH_SHORT).show();
        }
    }


}

