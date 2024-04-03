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
        firestore.collection("AdoptionLessons")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                Lesson lesson = d.toObject(Lesson.class);
                                lessonsList.add(lesson);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdoptionLessonActivity.this, "Failed to fetch lessons.", Toast.LENGTH_SHORT).show();
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
