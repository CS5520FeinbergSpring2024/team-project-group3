package com.example.final_project;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Data for a shelter object.
 */
public class Shelter implements Parcelable {
    private String name;
    private String location;
    private String imageUrl;
    private List<String> breeds;
    private String description;
    private String phoneNumber;
    private String address;
    private String yearOfBusiness;
    private List<Pet> adoptablePets;

    // Default constructor
    public Shelter() {
    }

    // Constructor with parameters
    public Shelter(String name, String location, String imageUrl, List<String> breeds,
                   String description, String phoneNumber, String address, String yearOfBusiness, List<Pet> adoptablePets) {
        this.name = name;
        this.location = location;
        this.imageUrl = imageUrl;
        this.breeds = breeds;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.yearOfBusiness = yearOfBusiness;
        this.adoptablePets = adoptablePets;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<String> getBreeds() {
        return breeds;
    }

    public String getDescription() {
        return description;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getYearOfBusiness() {
        return yearOfBusiness;
    }

    public List<Pet> getAdoptablePets() {
        return adoptablePets;
    }

    // Method to check if a shelter contains a specific breed
    public boolean containsBreed(String breedName) {
        return breeds != null && breeds.contains(breedName);
    }

    // Parcelable implementation
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(location);
        dest.writeString(imageUrl);
        dest.writeStringList(breeds);
        dest.writeString(description);
        dest.writeString(phoneNumber);
        dest.writeString(address);
        dest.writeString(yearOfBusiness);
        dest.writeTypedList(adoptablePets);
    }

    protected Shelter(Parcel in) {
        name = in.readString();
        location = in.readString();
        imageUrl = in.readString();
        breeds = in.createStringArrayList();
        description = in.readString();
        phoneNumber = in.readString();
        address = in.readString();
        yearOfBusiness = in.readString();
        adoptablePets = in.createTypedArrayList(Pet.CREATOR);
    }

    public static final Creator<Shelter> CREATOR = new Creator<Shelter>() {
        @Override
        public Shelter createFromParcel(Parcel in) {
            return new Shelter(in);
        }

        @Override
        public Shelter[] newArray(int size) {
            return new Shelter[size];
        }
    };
}

