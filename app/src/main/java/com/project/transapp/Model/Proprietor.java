package com.project.transapp.Model;

public class Proprietor {
    private String key;
    private String proprietorName;
    private String contactNumber;
    private String address;
    private String createdOn;

    // ✅ Default constructor (required for Firebase)
    public Proprietor() {
    }

    // ✅ Constructor with parameters
    public Proprietor(String key, String proprietorName, String contactNumber, String address, String createdOn) {
        this.key = key;
        this.proprietorName = proprietorName;
        this.contactNumber = contactNumber;
        this.address = address;
        this.createdOn = createdOn;
    }

    // ✅ Getters and Setters
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getProprietorName() {
        return proprietorName;
    }

    public void setProprietorName(String proprietorName) {
        this.proprietorName = proprietorName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }
}
