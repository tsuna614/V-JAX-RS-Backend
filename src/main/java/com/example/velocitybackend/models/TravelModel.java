package com.example.velocitybackend.models;

import java.util.List;

public class TravelModel {
    // Fields
    private String id;
    private String title;
    private String description;
    private String body;
    private List<String> imageUrl;
    private String travelType;
    private int price;
    private int rating;
    private String createdAt;

    //  Additional fields for each travel type
    private String city;
    private String capacity;
    private String address;
    private String contact;
    private String origin;
    private String destination;
    private String startDate;
    private String endDate;
    private String airline;
    private String location;
    private String carType;

    public TravelModel() {}

    public TravelModel(String id, String title, String description, String body, List<String> imageUrl, String travelType, int price, int rating, String city, String capacity, String address, String contact, String origin, String destination, String startDate, String endDate, String airline, String location, String carType, String createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.body = body;
        this.imageUrl = imageUrl;
        this.travelType = travelType;
        this.price = price;
        this.rating = rating;
        this.city = city;
        this.capacity = capacity;
        this.address = address;
        this.contact = contact;
        this.origin = origin;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.airline = airline;
        this.location = location;
        this.carType = carType;
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTravelType() {
        return travelType;
    }

    public void setTravelType(String travelType) {
        this.travelType = travelType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }
}
