package com.example.final_project;

/**
 * breed data that contains type of pet, breed, descriptions, imageURL.
 */
public class petData {
    String type;
    String breed;
    String description;
    String imageURL;
    public petData( String type, String breed, String description, String imageURL) {
        this.type = type;
        this.breed = breed;
        this.description = description;
        this.imageURL = imageURL;
    }
    public String getType() {
        return type;
    }
    public String getBreed() {
        return breed;
    }
    public String getDescription() {
        return description;
    }
    public String getImageURL() {
        return imageURL;
    }
}
