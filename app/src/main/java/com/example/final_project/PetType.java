package com.example.final_project;

/**
 * pet type that can be bind to firebase database.
 */
public class PetType {
    private String name;
    private String imageUrl;

    public PetType() {
    }

    public PetType(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }
}

