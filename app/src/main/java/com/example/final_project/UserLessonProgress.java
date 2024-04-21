package com.example.final_project;

public class UserLessonProgress {
    private String lessonId;
    private boolean completed;
    // Maybe add a timestamp or other relevant fields

    // Default constructor required for Firestore data mapping
    public UserLessonProgress() {
    }

    public UserLessonProgress(String lessonId, boolean completed) {
        this.lessonId = lessonId;
        this.completed = completed;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

}

