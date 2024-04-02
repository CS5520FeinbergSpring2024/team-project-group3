package com.example.final_project;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * data for a shelter object.
 */
public class shelterData implements Parcelable {
    private String name;
    private String location;
    private String imageUrl;
    private List<String> breeds;

    private String description;
    private String phoneNumber;
    private String address;
    private String yearOfBusiness;
    private List<petData> adoptablePets;

    public shelterData() {

    }

    public shelterData(String name, String location, String imageUrl, List<String> breeds,
                       String description, String phoneNumber, String address, String yearOfBusiness, List<petData> adoptablePets) {
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

    public String getName() {
        return this.name;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public String getLocation() {
        return this.location;
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

    public List<petData> getAdoptablePets() {
        return adoptablePets;
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

    protected shelterData(Parcel in) {
        name = in.readString();
        location = in.readString();
        imageUrl = in.readString();
        breeds = in.createStringArrayList();
        description = in.readString();
        phoneNumber = in.readString();
        address = in.readString();
        yearOfBusiness = in.readString();
        adoptablePets = in.createTypedArrayList(petData.CREATOR);
    }

    public static final Creator<shelterData> CREATOR = new Creator<shelterData>() {
        @Override
        public shelterData createFromParcel(Parcel in) {
            return new shelterData(in);
        }

        @Override
        public shelterData[] newArray(int size) {
            return new shelterData[size];
        }
    };
}
