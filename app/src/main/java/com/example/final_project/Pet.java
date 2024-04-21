package com.example.final_project;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * Pet object.
 */

public class Pet implements Parcelable {
    private String type;
    private String age;
    private String description;
    private String gender;
    private String id;
    private String imageUrl;
    private String name;
    private String breed;
    private String price;

    public Pet() {
    }

    public Pet(String type, String age, String description, String gender, String id, String imageUrl, String name, String breed, String price) {
        this.type = type;
        this.age = age;
        this.description = description;
        this.gender = gender;
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.breed = breed;
        this.price = price;
    }

    // Getter methods

    public String getPrice() {
        return price;
    }
    public String getType() {
        return type;
    }

    public String getAge() {
        return age;
    }

    public String getDescription() {
        return description;
    }

    public String getGender() {
        return gender;
    }

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    protected Pet(Parcel in) {
        type = in.readString();
        age = in.readString();
        description = in.readString();
        gender = in.readString();
        id = in.readString();
        imageUrl = in.readString();
        name = in.readString();
        breed = in.readString();
        price = in.readString();

    }
    public static final Creator<Pet> CREATOR = new Creator<Pet>() {
        @Override
        public Pet createFromParcel(Parcel in) {
            return new Pet(in);
        }

        @Override
        public Pet[] newArray(int size) {
            return new Pet[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(age);
        dest.writeString(description);
        dest.writeString(gender);
        dest.writeString(id);
        dest.writeString(imageUrl);
        dest.writeString(name);
        dest.writeString(breed);
        dest.writeString(price);
    }
}

