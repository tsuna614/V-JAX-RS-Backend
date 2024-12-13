package com.example.velocitybackend.models;

public class BookModel {
    private String _id;
    private String travelId;
    private String userId;
    private String bookedDate;
    private int amount;
    private String createdAt;

    public BookModel() {}

    public BookModel(String _id, String travelId, String userId, String bookedDate, int amount, String createdAt) {
        this._id = _id;
        this.travelId = travelId;
        this.userId = userId;
        this.bookedDate = bookedDate;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getTravelId() {
        return travelId;
    }

    public void setTravelId(String travelId) {
        this.travelId = travelId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookedDate() {
        return bookedDate;
    }

    public void setBookedDate(String bookedDate) {
        this.bookedDate = bookedDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
