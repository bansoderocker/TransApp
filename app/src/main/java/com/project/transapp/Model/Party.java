package com.project.transapp.Model;

public class Party {
    private String key;
    private String createdOn;
    private String pageNumber;
    private String partyName;
    private String remark;

    // Empty constructor for Firebase
    public Party() {
    }

    // Constructor
    public Party(String key, String createdOn, String pageNumber, String partyName, String remark) {
        this.key = key;
        this.createdOn = createdOn;
        this.pageNumber = pageNumber;
        this.partyName = partyName;
        this.remark = remark;
    }

    // Getters and Setters
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
