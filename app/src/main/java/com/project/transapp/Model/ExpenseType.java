package com.project.transapp.Model;

public class ExpenseType {
    private String Key;
    private String Text;
    private String Type; // Added 'type' (Credit/Debit)

    private String CreatedOn;

    public ExpenseType() {
        // Default constructor for Firebase
    }

    public ExpenseType(String key, String text,String type,String createdOn) {
        this.Key = key;
        this.Text = text;
        this.Type = type;
        this.CreatedOn = createdOn;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        this.Key = key;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        this.Text = text;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        this.Type = type;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        this.CreatedOn = createdOn;
    }
}
