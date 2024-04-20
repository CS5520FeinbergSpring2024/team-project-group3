package com.example.final_project;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages adoption education lessons for pet owners.
 * Fetches lesson content from Firestore and displays it in a RecyclerView.
 * Tracks and updates the user's progress as they complete lessons.
 */
public class AdoptionLessonActivity extends AppCompatActivity {

    private static final String TAG = "AdoptionLessonActivity"; // Tag for logging
    private FirebaseFirestore firestore;
    private RecyclerView lessonsRecyclerView;
    private LessonsAdapter adapter;
    private List<Lesson> lessonsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adoption_lesson);

        // Initialize Firestore instance
        firestore = FirebaseFirestore.getInstance();

        // Setup RecyclerView
        lessonsRecyclerView = findViewById(R.id.lessonsRecyclerView);
        lessonsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LessonsAdapter(lessonsList, this);
        lessonsRecyclerView.setAdapter(adapter);

        fetchLessons();
    }

    /**
     * Fetches lesson content from Firestore and updates the UI.
     */
    private void fetchLessons() {
        Log.d(TAG, "Fetching lessons from Firestore");
        firestore.collection("AdoptionLessons")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d(TAG, "Successfully fetched lessons");
                        lessonsList.clear();
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            Lesson lesson = document.toObject(Lesson.class);
                            lessonsList.add(lesson);
                        }
                        adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Failed to fetch lessons: " + e.getMessage(), e);
                        Toast.makeText(AdoptionLessonActivity.this, "Failed to fetch lessons: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Updates the user's progress for a given lesson.
     * @param lessonId The ID of the completed lesson.
     */
    private void updateProgress(String lessonId) {
        Log.d(TAG, "Updating progress for lesson: " + lessonId);
        String userId = getIntent().getStringExtra("USER_ID");
        firestore.collection("Users").document(userId).collection("CompletedLessons").document(lessonId)
                .set(new UserLessonProgress(lessonId, true))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Successfully updated lesson progress");
                        Toast.makeText(AdoptionLessonActivity.this, "Progress updated.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Failed to update progress: " + e.getMessage(), e);
                        Toast.makeText(AdoptionLessonActivity.this, "Failed to update progress.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
