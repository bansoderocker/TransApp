package com.project.transapp.Model;
public class Trip {
    private String key;
    private double amount;
    private String particular;
    private double tempExpenseAmount;
    private String tempExpenseName;
    private String billName;
    private String billNumber;
    private String remark;
    private String searchText;
    private String tripDate;
    private double tripAmount;
    private String truckNumber;
    private String partyKey;
    private String createdOn;
    private String particularFrom;
    private String particularTo;

    // **No-argument constructor required for Firebase**
    public Trip() {
    }

    public Trip(String key, double amount, String particular, double tempExpenseAmount, String tempExpenseName,
                String billName, String billNumber, String remark, String searchText, String tripDate,
                double tripAmount, String truckNumber, String partyKey, String createdOn, String particularFrom,
                String particularTo) {
        this.key = key;
        this.amount = amount;
        this.particular = particular;
        this.tempExpenseAmount = tempExpenseAmount;
        this.tempExpenseName = tempExpenseName;
        this.billName = billName;
        this.billNumber = billNumber;
        this.remark = remark;
        this.searchText = searchText;
        this.tripDate = tripDate;
        this.tripAmount = tripAmount;
        this.truckNumber = truckNumber;
        this.partyKey = partyKey;
        this.createdOn = createdOn;
        this.particularFrom = particularFrom;
        this.particularTo = particularTo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }

    public double getTempExpenseAmount() {
        return tempExpenseAmount;
    }

    public void setTempExpenseAmount(double tempExpenseAmount) {
        this.tempExpenseAmount = tempExpenseAmount;
    }

    public String getTempExpenseName() {
        return tempExpenseName;
    }

    public void setTempExpenseName(String tempExpenseName) {
        this.tempExpenseName = tempExpenseName;
    }

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getTripDate() {
        return tripDate;
    }

    public void setTripDate(String tripDate) {
        this.tripDate = tripDate;
    }

    public double getTripAmount() {
        return tripAmount;
    }

    public void setTripAmount(double tripAmount) {
        this.tripAmount = tripAmount;
    }

    public String getTruckNumber() {
        return truckNumber;
    }

    public void setTruckNumber(String truckNumber) {
        this.truckNumber = truckNumber;
    }

    public String getPartyKey() {
        return partyKey;
    }

    public void setPartyKey(String partyKey) {
        this.partyKey = partyKey;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getParticularFrom() {
        return particularFrom;
    }

    public void setParticularFrom(String particularFrom) {
        this.particularFrom = particularFrom;
    }

    public String getParticularTo() {
        return particularTo;
    }

    public void setParticularTo(String particularTo) {
        this.particularTo = particularTo;
    }
}
