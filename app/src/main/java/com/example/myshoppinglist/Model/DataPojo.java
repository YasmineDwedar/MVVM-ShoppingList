package com.example.myshoppinglist.Model;

public class DataPojo {

    String Type;
    int amount;
    String note;
    String date;
    String id;

    public DataPojo(){

    }

    public DataPojo(String id,String type, int amount, String note, String date) {
        Type = type;
        this.amount = amount;
        this.note = note;
        this.date = date;
        this.id = id;
    }

    public DataPojo(String myType, int amountInt, String myNote) {
        Type=myType;
        amount =amountInt;
        note=myNote;
    }

    public DataPojo(String myType, int amountInt, String myNote, String myDate) {
        Type=myType;
        amount =amountInt;
        note=myNote;
        date=myDate;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
