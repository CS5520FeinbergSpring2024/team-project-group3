package com.example.final_project;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class PetRegistrationActivity extends AppCompatActivity {
    private EditText petNameEditText, petBreedEditText, petAgeEditText, petDescriptionEditText, petGenderEditText, petPriceEditText, petTypeEditText;
    private Button uploadImageButton, submitPetButton;
    private ImageView petImageView;
    private Uri imageUri;

    private FirebaseFirestore firestore;
    private FirebaseStorage storage;

    private StorageReference storageReference;

    private static final int READ_MEDIA_IMAGES_REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_registration);

        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        petNameEditText = findViewById(R.id.petNameEditText);
        petBreedEditText = findViewById(R.id.petBreedEditText);
        petAgeEditText = findViewById(R.id.petAgeEditText);
        petDescriptionEditText = findViewById(R.id.petDescriptionEditText);
        petGenderEditText = findViewById(R.id.petGenderEditText);
        petPriceEditText = findViewById(R.id.petPriceEditText);
        petTypeEditText = findViewById(R.id.petTypeEditText);
        uploadImageButton = findViewById(R.id.uploadImageButton);
        submitPetButton = findViewById(R.id.submitPetButton);
        petImageView = findViewById(R.id.petImageView);

        uploadImageButton.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, READ_MEDIA_IMAGES_REQUEST_CODE);
            } else {
                openImagePicker();
            }
        });

        submitPetButton.setOnClickListener(v -> {
            if (imageUri != null) {
                uploadImageAndSavePetInfo();
            } else {
                Toast.makeText(PetRegistrationActivity.this, "Please upload an image", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_MEDIA_IMAGES_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImagePicker();
            } else {
                Toast.makeText(this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            petImageView.setImageURI(imageUri);
        }
    }

    private void uploadImageAndSavePetInfo() {
        try {
            // Create a local file from URI
            File localFile = File.createTempFile("images", "jpg");
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            FileOutputStream fileOutputStream = new FileOutputStream(localFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, length);
            }
            inputStream.close();
            fileOutputStream.close();

            // Create a file reference
            final StorageReference filePath = storageReference.child("pet_images/" + localFile.getName());
            UploadTask uploadTask = filePath.putFile(Uri.fromFile(localFile));

            // Handle success and failure
            uploadTask.addOnSuccessListener(taskSnapshot -> filePath.getDownloadUrl().addOnSuccessListener(uri -> savePetInfo(uri.toString())))
                    .addOnFailureListener(e -> Toast.makeText(PetRegistrationActivity.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show());

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to create a file from image.", Toast.LENGTH_SHORT).show();
        }
    }

    private void savePetInfo(String imageUrl) {
        String name = petNameEditText.getText().toString().trim();
        String breed = petBreedEditText.getText().toString().trim();
        String age = petAgeEditText.getText().toString().trim();
        String description = petDescriptionEditText.getText().toString().trim();
        String gender = petGenderEditText.getText().toString().trim();
        String price = petPriceEditText.getText().toString().trim();
        String type = petTypeEditText.getText().toString().trim();

        Pet pet = new Pet(type, age, description, gender, "", imageUrl, name, breed, price);
        firestore.collection("Pets").add(pet)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(PetRegistrationActivity.this, "Pet registered successfully", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(PetRegistrationActivity.this, "Pet registration failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}

