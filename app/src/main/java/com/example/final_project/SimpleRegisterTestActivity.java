package com.example.final_project;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class SimpleRegisterTestActivity extends AppCompatActivity {

    private static final String TAG = "SimpleRegisterTest";
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_register_test); // Use a simple layout

        // Initialize Firebase
        FirebaseApp.initializeApp(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Perform a simple write operation to test Firebase Realtime Database connectivity
        testRealtimeDatabaseWrite();
    }

    private void testRealtimeDatabaseWrite() {
        Map<String, Object> testUserData = new HashMap<>();
        testUserData.put("username", "TestUser");
        testUserData.put("email", "testuser@example.com");
        testUserData.put("role", "test_role");

        // Writing to the "Users" node with a child of "TestUserUID"
        databaseReference.child("Users").child("TestUserUID")
                .setValue(testUserData)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "User data successfully written to Realtime Database!");
                    } else {
                        Log.e(TAG, "Error writing user data", task.getException());
                    }
                });
    }
}
