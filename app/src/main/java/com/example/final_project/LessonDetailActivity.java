package com.example.final_project;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LessonDetailActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private TextView titleTextView, contentTextView;
    private Button completeLessonButton;
    private String lessonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_detail);

        // Initialize Firebase Realtime Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference();

        titleTextView = findViewById(R.id.lessonTitleTextView);
        contentTextView = findViewById(R.id.lessonContentTextView);
        completeLessonButton = findViewById(R.id.completeLessonButton);

        lessonId = getIntent().getStringExtra("lessonId");
        Log.d("LessonDetailActivity", "Lesson ID: " + lessonId);

        if (lessonId != null) {
            fetchLesson(lessonId);
        } else {
            Log.d("LessonDetailActivity", "No Lesson ID passed to Intent");
        }

        completeLessonButton.setOnClickListener(v -> markLessonCompleted(lessonId));
    }

    private void fetchLesson(String lessonId) {
        databaseReference.child("AdoptionLessons").child("Lesson" + lessonId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("LessonDetailActivity", "DataSnapshot: " + dataSnapshot.getValue());
                        Lesson lesson = dataSnapshot.getValue(Lesson.class);
                        if (lesson != null) {
                            titleTextView.setText(lesson.getTitle());
                            contentTextView.setText(lesson.getLesson());
                        } else {
                            Log.d("LessonDetailActivity", "Lesson data is null");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("LessonDetailActivity", "Database error", databaseError.toException());
                        Toast.makeText(LessonDetailActivity.this, "Failed to load lesson.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void markLessonCompleted(String lessonId) {
        String userId = getIntent().getStringExtra("userId");
        Log.d("LessonDetailActivity", "Marking lesson as complete: " + lessonId + " for user: " + userId);
        if (userId != null) {
            databaseReference.child("Users").child(userId).child("CompletedLessons").child(lessonId)
                    .setValue(true)
                    .addOnSuccessListener(aVoid -> {
                        Log.d("LessonDetailActivity", "Lesson marked as completed successfully.");
                        Toast.makeText(LessonDetailActivity.this, "Lesson marked as completed!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Log.e("LessonDetailActivity", "Failed to mark lesson as completed", e);
                        Toast.makeText(LessonDetailActivity.this, "Failed to mark lesson as completed. " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Log.d("LessonDetailActivity", "User ID is null or empty");
            Toast.makeText(this, "User ID is not available. Cannot mark lesson as completed.", Toast.LENGTH_SHORT).show();
        }
    }
}
