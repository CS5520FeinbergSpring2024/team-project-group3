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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("AdoptionLessons");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lessonsList.clear(); // Clear existing data
                for (DataSnapshot lessonSnapshot : dataSnapshot.getChildren()) {
                    // Assuming that your Lesson class has the same fields as the Realtime Database nodes
                    Lesson lesson = lessonSnapshot.getValue(Lesson.class);
                    if (lesson != null) {
                        lessonsList.add(lesson);
                    }
                }
                adapter.notifyDataSetChanged(); // Notify the adapter of data changes
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                Toast.makeText(AdoptionLessonActivity.this, "Failed to load lessons.", Toast.LENGTH_SHORT).show();
            }
        });
    }




    /**
     * Updates the user's progress for a given lesson.
     * @param lessonId The ID of the completed lesson.
     */
    private void updateProgress(String lessonId) {
        Log.d(TAG, "Updating progress for lesson: " + lessonId);
        String userId = getIntent().getStringExtra("UserID");
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
