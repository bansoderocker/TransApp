package com.project.transapp.Model;
public class Truck {
    private String key;
    private String TruckNumber;
    private String remark;
    private boolean selfOwned; // true = Self Owned, false = Other Owner


    // Required empty constructor for Firebase
    public Truck() {
    }

    public Truck(String key, String truckNumber, String remark,boolean selfOwned) {
        this.key = key;
        this.TruckNumber = truckNumber;
        this.remark = remark;
        this.selfOwned = selfOwned;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTruckNumber() {
        return TruckNumber;
    }

    public void setTruckNumber(String truckNumber) {
        this.TruckNumber = truckNumber;
    }
}
