package com.example.final_project;

public class AdoptionApplication {
    private String id;
    private String petId;
    private String userId;
    private String shelterId;
    private String applicantName;
    private String applicantAddress;
    private String status;

    // Default constructor required for Firestore data fetching
    public AdoptionApplication() {
    }

    // Constructor with all fields
    public AdoptionApplication(String id, String petId, String userId, String shelterId, String applicantName, String applicantAddress, String status) {
        this.id = id;
        this.petId = petId;
        this.userId = userId;
        this.shelterId = shelterId;
        this.applicantName = applicantName;
        this.applicantAddress = applicantAddress;
        this.status = status;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getPetId() {
        return petId;
    }

    public String getUserId() {
        return userId;
    }

    public String getShelterId() {
        return shelterId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public String getApplicantAddress() {
        return applicantAddress;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setShelterId(String shelterId) {
        this.shelterId = shelterId;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public void setApplicantAddress(String applicantAddress) {
        this.applicantAddress = applicantAddress;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

