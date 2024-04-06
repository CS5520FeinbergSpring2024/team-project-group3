package com.example.final_project;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.GeoPoint;

import java.util.List;

public class Shelter implements Parcelable {
    private String id;
    private String name;
    private String location;
    private String imageUrl;
    private List<String> breeds;
    private String description;
    private String phoneNumber;
    private String address;
    private String yearOfBusiness;
    private GeoPoint geoLocation; // For storing geolocation
    private List<Pet> adoptablePets; // List of adoptable pets

    // Transient field for distance calculation
    private transient double distanceToUser;

    // Default constructor needed for Firestore data fetch
    public Shelter() {
    }

    // Constructor with parameters (without GeoPoint)
    // Adjusted constructor
    public Shelter(String name, String location, String imageUrl, String description,
                   String phoneNumber, String address, String yearOfBusiness, GeoPoint geoLocation) {
        this.name = name;
        this.location = location;
        this.imageUrl = imageUrl;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.yearOfBusiness = yearOfBusiness;
        this.geoLocation = geoLocation; // Set the geolocation
    }

    // Parcelable implementation
    protected Shelter(Parcel in) {
        id = in.readString();
        name = in.readString();
        location = in.readString();
        imageUrl = in.readString();
        breeds = in.createStringArrayList();
        description = in.readString();
        phoneNumber = in.readString();
        address = in.readString();
        yearOfBusiness = in.readString();
        geoLocation = in.readParcelable(GeoPoint.class.getClassLoader());
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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(location);
        dest.writeString(imageUrl);
        dest.writeStringList(breeds);
        dest.writeString(description);
        dest.writeString(phoneNumber);
        dest.writeString(address);
        dest.writeString(yearOfBusiness);
        dest.writeParcelable((Parcelable) geoLocation, flags);
        dest.writeTypedList(adoptablePets);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public List<String> getBreeds() { return breeds; }
    public void setBreeds(List<String> breeds) { this.breeds = breeds; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getYearOfBusiness() { return yearOfBusiness; }
    public void setYearOfBusiness(String yearOfBusiness) { this.yearOfBusiness = yearOfBusiness; }
    public GeoPoint getGeoLocation() { return geoLocation; }
    public void setGeoLocation(GeoPoint geoLocation) { this.geoLocation = geoLocation; }
    public List<Pet> getAdoptablePets() { return adoptablePets; }

    // Getter and setter for the transient field
    public double getDistanceToUser() {
        return distanceToUser;
    }

    public void setDistanceToUser(double distanceToUser) {
        this.distanceToUser = distanceToUser;
    }
    public void setAdoptablePets(List<Pet> adoptablePets) { this.adoptablePets = adoptablePets; }
}
