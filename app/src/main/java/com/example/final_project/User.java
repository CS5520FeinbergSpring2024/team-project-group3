package com.example.final_project;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String name;
    private String email;
    private String role;
    private Map<String, Boolean> completedLessons;
    private HashMap chats;

    // Default constructor required for Firestore data fetching
    public User() {
        completedLessons = new HashMap<>();

    }

    // Updated constructor to include role
    public User(String name, String email, String role) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.completedLessons = new HashMap<>();
    }

    public User(String name, String email, String role, HashMap chats) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.completedLessons = new HashMap<>(); // No change needed
        this.chats = chats;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Map<String, Boolean> getCompletedLessons() {
        return completedLessons;
    }

    public void setCompletedLessons(Map<String, Boolean> completedLessons) {
        this.completedLessons = completedLessons;
    }

    // Add a completed lesson
    public void addCompletedLesson(String lessonId) {
        completedLessons.put(lessonId, true);
    }

    // Check if a lesson is completed
    public boolean isLessonCompleted(String lessonId) {
        Boolean completed = completedLessons.get(lessonId);
        return completed != null && completed;
    }
}

