package com.personal.simpleexpensetracker.models;

public class AddExpenseModel {
    String notes, date, category, id;
    int amount;

    public AddExpenseModel() {
    }

    public AddExpenseModel(String notes, String date, String category, String id, int amount) {
        this.notes = notes;
        this.date = date;
        this.category = category;
        this.id = id;
        this.amount = amount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
