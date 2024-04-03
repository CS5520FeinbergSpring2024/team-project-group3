package com.example.final_project;

public class Lesson {
    private String lessonId; // Unique identifier for each lesson
    private String title;
    private String description;
    private String imageUrl; // Optional: URL to an image for the lesson

    // Default constructor is required for Firestore's automatic data mapping.
    public Lesson() {
    }

    public Lesson(String lessonId, String title, String description, String imageUrl) {
        this.lessonId = lessonId;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl; // Initialize imageUrl if using images for lessons
    }

    // Getters and Setters
    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}


