package com.example.velocitybackend.utils;

import com.example.velocitybackend.models.TravelModel;
import org.bson.Document;

import java.time.LocalDateTime;

public class TravelUtil {
    // Convert MongoDB Document to TravelModel
    public static TravelModel fromDocument(Document doc) {
        TravelModel travel = new TravelModel();
        travel.setId(doc.getObjectId("_id").toString());
        travel.setTitle(doc.getString("title"));
        travel.setDescription(doc.getString("description"));
        travel.setBody(doc.getString("body"));
        travel.setImageUrl(doc.getList("imageUrl", String.class));
        travel.setTravelType(doc.getString("travelType"));
        travel.setPrice(doc.getInteger("price", 0));
        travel.setRating(doc.getInteger("rating", 0));
        travel.setCity(doc.getString("city"));
        travel.setCapacity(doc.getString("capacity"));
        travel.setAddress(doc.getString("address"));
        travel.setContact(doc.getString("contact"));
        travel.setOrigin(doc.getString("origin"));
        travel.setDestination(doc.getString("destination"));
        travel.setStartDate(doc.getString("startDate"));
        travel.setEndDate(doc.getString("endDate"));
        travel.setAirline(doc.getString("airline"));
        travel.setLocation(doc.getString("location"));
        travel.setCarType(doc.getString("carType"));
        travel.setCreatedAt(doc.getString("createdAt"));

        return travel;
    }

    // Convert TravelModel to MongoDB Document
    public static Document toDocument(TravelModel travel) {
        return new Document()
                .append("title", travel.getTitle())
                .append("description", travel.getDescription())
                .append("body", travel.getBody())
                .append("imageUrl", travel.getImageUrl())
                .append("travelType", travel.getTravelType())
                .append("price", travel.getPrice())
                .append("rating", travel.getRating())
                .append("city", travel.getCity())
                .append("capacity", travel.getCapacity())
                .append("address", travel.getAddress())
                .append("contact", travel.getContact())
                .append("origin", travel.getOrigin())
                .append("destination", travel.getDestination())
                .append("startDate", travel.getStartDate())
                .append("endDate", travel.getEndDate())
                .append("airline", travel.getAirline())
                .append("location", travel.getLocation())
                .append("carType", travel.getCarType())
                .append("createdAt", travel.getCreatedAt() == null ? LocalDateTime.now().toString() : travel.getCreatedAt());
    }

    // Validate the required fields of TravelModel
    public static String validateTravel(TravelModel travel) {
        if (travel.getTitle() == null || travel.getTitle().isEmpty()) {
            return "Title is required.";
        }
        if (travel.getPrice() < 0) {
            return "Price must not be negative.";
        }
        if (travel.getTravelType() == null || travel.getTravelType().isEmpty()) {
            return "Travel type is required.";
        }
        return null; // No validation errors
    }

    // Create a Document for updating fields (ignore null values)
    public static Document filterNullFields(TravelModel travel) {
        Document document = new Document();
        if (travel.getTitle() != null) {
            document.append("title", travel.getTitle());
        }
        if (travel.getDescription() != null) {
            document.append("description", travel.getDescription());
        }
        if (travel.getBody() != null) {
            document.append("body", travel.getBody());
        }
        if (travel.getImageUrl() != null) {
            document.append("imageUrl", travel.getImageUrl());
        }
        if (travel.getTravelType() != null) {
            document.append("travelType", travel.getTravelType());
        }
        if (travel.getPrice() > 0) {
            document.append("price", travel.getPrice());
        }
        if (travel.getRating() > 0) {
            document.append("rating", travel.getRating());
        }
        if (travel.getCity() != null) {
            document.append("city", travel.getCity());
        }
        if (travel.getCapacity() != null) {
            document.append("capacity", travel.getCapacity());
        }
        if (travel.getAddress() != null) {
            document.append("address", travel.getAddress());
        }
        if (travel.getContact() != null) {
            document.append("contact", travel.getContact());
        }
        if (travel.getOrigin() != null) {
            document.append("origin", travel.getOrigin());
        }
        if (travel.getDestination() != null) {
            document.append("destination", travel.getDestination());
        }
        if (travel.getStartDate() != null) {
            document.append("startDate", travel.getStartDate());
        }
        if (travel.getEndDate() != null) {
            document.append("endDate", travel.getEndDate());
        }
        if (travel.getAirline() != null) {
            document.append("airline", travel.getAirline());
        }
        if (travel.getLocation() != null) {
            document.append("location", travel.getLocation());
        }
        if (travel.getCarType() != null) {
            document.append("carType", travel.getCarType());
        }
        return document;
    }
}
