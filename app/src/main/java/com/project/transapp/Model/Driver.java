package com.project.transapp.Model;

public class Driver {
    private String key;
    private String driverName;
    private String licenseNumber;
    private String contactNumber;
    private String createdOn;

    public Driver() {
        // Default constructor for Firebase
    }

    public Driver(String key, String driverName, String licenseNumber, String contactNumber, String createdOn) {
        this.key = key;
        this.driverName = driverName;
        this.licenseNumber = licenseNumber;
        this.contactNumber = contactNumber;
        this.createdOn = createdOn;
    }

    public String getKey() {
        return key;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getCreatedOn() {
        return createdOn;
    }
}
