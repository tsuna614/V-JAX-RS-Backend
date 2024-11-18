package com.example.velocitybackend.utils;

import com.example.velocitybackend.models.UserModel;
import org.bson.Document;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class UserUtil {
    public static UserModel fromDocument(Document doc) {
        UserModel user = new UserModel();
        user.setUserId(doc.getObjectId("_id").toString());
        user.setEmail(doc.getString("email"));
        user.setPassword(doc.getString("password"));
        user.setFirstName(doc.getString("firstName"));
        user.setLastName(doc.getString("lastName"));
        user.setPhoneNumber(doc.getString("phoneNumber"));
        user.setProfileImageUrl(doc.getString("profileImageUrl"));
        user.setFriendsId(doc.getList("friendsId", String.class));
        user.setBookmarksId(doc.getList("bookmarksId", String.class));
        user.setRefreshToken(doc.getString("refreshToken"));
        user.setCreatedAt(doc.getString("createdAt"));

//        Date createdAt = doc.getDate("createdAt");
//        user.setCreatedAt(createdAt != null ? isoFormat.format(createdAt) : null);

        return user;
    }

    public static Document toDocument(UserModel user) {
        return new Document()
                .append("email", user.getEmail())
                .append("password", user.getPassword())
                .append("firstName", user.getFirstName())
                .append("lastName", user.getLastName())
                .append("phoneNumber", user.getPhoneNumber())
                .append("profileImageUrl", user.getProfileImageUrl())
                .append("friendsId", user.getFriendsId())
                .append("bookmarksId", user.getBookmarksId())
                .append("refreshToken", user.getRefreshToken())
                .append("createdAt", user.getCreatedAt() == null ? LocalDateTime.now().toString() : user.getCreatedAt());
    }

    static public String validateUser(UserModel user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return "Email is required.";
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return "Password is required.";
        }
        return null; // No validation errors
    }

    // the same as toDocument() method, but this will return a Document with the exact body that the user send
    // (matching the fields in UserModel of course)
    static public Document filterNullFields(UserModel user) {
        Document document = new Document();
        if (user.getEmail() != null) {
            document.append("email", user.getEmail());
        }
        if (user.getPassword() != null) {
            document.append("password", user.getPassword());
        }
        if (user.getFirstName() != null) {
            document.append("firstName", user.getFirstName());
        }
        if (user.getLastName() != null) {
            document.append("lastName", user.getLastName());
        }
        if (user.getPhoneNumber() != null) {
            document.append("phoneNumber", user.getPhoneNumber());
        }
        if (user.getProfileImageUrl() != null) {
            document.append("profileImageUrl", user.getProfileImageUrl());
        }
        if (user.getFriendsId() != null) {
            document.append("friendsId", user.getFriendsId());
        }
        if (user.getBookmarksId() != null) {
            document.append("bookmarksId", user.getBookmarksId());
        }
        if (user.getRefreshToken() != null) {
            document.append("refreshToken", user.getRefreshToken());
        }
        return document;
    }
}
