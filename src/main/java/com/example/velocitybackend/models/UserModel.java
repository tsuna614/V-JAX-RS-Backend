package com.example.velocitybackend.models;

import org.bson.Document;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class UserModel {
    private String userId;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String profileImageUrl;
    private List<String> friendsId;
    private List<String> bookmarksId;
    private String refreshToken;
    private String createdAt;

    public UserModel() {}

    public UserModel(String userId, String email, String password, String firstName, String lastName, String phoneNumber, String profileImageUrl, List<String> friendsId, List<String> bookmarksId, String refreshToken, String createdAt) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.profileImageUrl = profileImageUrl;
        this.friendsId = friendsId;
        this.bookmarksId = bookmarksId;
        this.refreshToken = refreshToken;
        this.createdAt = createdAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public List<String> getFriendsId() {
        return friendsId;
    }

    public void setFriendsId(List<String> friendsId) {
        this.friendsId = friendsId;
    }

    public List<String> getBookmarksId() {
        return bookmarksId;
    }

    public void setBookmarksId(List<String> bookmarksId) {
        this.bookmarksId = bookmarksId;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public static UserModel fromDocument(Document doc) {
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

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
                .append("createdAt", (LocalDateTime.now()).toString());
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
