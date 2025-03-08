package com.project.transapp.Model;

public class Trip {

    private String Key;
    private String proprietorName;
    private String partyName;
    private String billNumber;
    private String date;
    private String truckNumber;
    private String from;
    private String to;
    private String extraDelivery;
    private String weight;
    private String rate;
    private double fixAmount;
    private double amount;

    private double totalExpense;

    private String createdOn;

    public Trip(String Key, String proprietorName, String partyName, String billNumber, String date, String truckNumber, String from, String to, String extraDelivery, String weight, String rate, double fixAmount, double amount, String createdOn) {
        this.Key = Key;
        this.proprietorName = proprietorName;
        this.partyName = partyName;
        this.billNumber = billNumber;
        this.date = date;
        this.truckNumber = truckNumber;
        this.from = from;
        this.to = to;
        this.extraDelivery = extraDelivery;
        this.weight = weight;
        this.rate = rate;
        this.fixAmount = fixAmount;
        this.amount = amount;
        this.createdOn = createdOn;
    }

    public Trip() {
    } // Default constructor required for Firebase

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getProprietorName() {
        return proprietorName;
    }

    public void setProprietorName(String proprietorName) {
        this.proprietorName = proprietorName;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTruckNumber() {
        return truckNumber;
    }

    public void setTruckNumber(String truckNumber) {
        this.truckNumber = truckNumber;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getExtraDelivery() {
        return extraDelivery;
    }

    public void setExtraDelivery(String extraDelivery) {
        this.extraDelivery = extraDelivery;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
    public double getFixAmount() {
        return fixAmount;
    }

    public void setFixAmount(double fixAmount) {
        this.fixAmount = fixAmount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
