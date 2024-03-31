package com.example.final_project;

import android.util.Log;

import java.util.List;

/**
 * data for a shelter object.
 */
public class shelterData {
    private String name;
    private String location;
    private String imageUrl;
    private List<String> breeds;

    public shelterData() {

    }

    public shelterData(String name, String location, String imageUrl, List<String> breeds) {
        this.name = name;
        this.location = location;
        this.imageUrl = imageUrl;
        this.breeds = breeds;
    }

    public String getName() {
        return this.name;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public String getLocation() {
        return this.location;
    }

    /**
     * This method is called to evaluate if the breed that is selected is in a shelter.
     * @param breedName
     * @return
     */
    public boolean containBreed(String breedName) {
//        Log.d("Breeds list", String.valueOf(breeds.size()));
//        Log.d("location", this.getLocation());
        for (String breed : breeds) {
//            Log.d("breed Name", breedName);
//            Log.d("breed in-comparison", breed);
            if (breed.equalsIgnoreCase(breedName)) {
                return true;
            }
        }
//        Log.d("clicked contain breed", "ah");
        return false;
    }
}
