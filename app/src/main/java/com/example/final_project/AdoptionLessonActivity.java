package com.example.final_project;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Manages adoption education lessons for pet owners.
 * Fetches lesson content from Firestore and displays it in a RecyclerView.
 * Tracks and updates the user's progress as they complete lessons.
 */
public class AdoptionLessonActivity extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    private RecyclerView lessonsRecyclerView;
    private LessonsAdapter adapter;
    private List<Lesson> lessonsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adoption_lesson);

        // Initialize Firebase Auth and Firestore instances
        auth = FirebaseAuth.getInstance();
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
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    
                    DataSnapshot lessonSnapshot = snapshot.child("Lesson");
                    if (lessonSnapshot.exists()) {
                        Lesson lesson = lessonSnapshot.getValue(Lesson.class);
                        if (lesson != null) {
                            lesson.setLessonId(snapshot.getKey()); // Set the ID from the snapshot key
                            lessonsList.add(lesson);
                        }
                    }
                }
                adapter.notifyDataSetChanged(); // Notify the adapter of data changes
                if (lessonsList.isEmpty()) {
                    Toast.makeText(AdoptionLessonActivity.this, "No lessons available.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AdoptionLessonActivity.this, "Failed to fetch lessons: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




    /**
     * Updates the user's progress for a given lesson.
     * Could be expanded to include completion tracking, quizzes, etc.
     * @param lessonId The ID of the completed lesson.
     */
    private void updateProgress(String lessonId) {
        // Assuming a "Users" collection with a subcollection "CompletedLessons"
        String userId = auth.getCurrentUser().getUid();
        firestore.collection("Users").document(userId).collection("CompletedLessons").document(lessonId)
                .set(new UserLessonProgress(lessonId, true)) // Simple object to mark completion. Requires implementation.
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AdoptionLessonActivity.this, "Progress updated.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdoptionLessonActivity.this, "Failed to update progress.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
