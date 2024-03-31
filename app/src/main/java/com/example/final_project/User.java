package com.example.final_project;

public class User {
    public String username;
    public String role;

    // Default constructor required for calls to DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }
}

