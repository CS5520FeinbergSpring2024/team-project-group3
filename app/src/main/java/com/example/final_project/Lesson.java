package com.example.final_project;

public class Lesson {
    private String title;
    private String description;

    // Default constructor is required for Firestore's automatic data mapping.
    public Lesson() {
    }

    public Lesson(String title, String description) {
        this.title = title;
        this.description = description;
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
}

