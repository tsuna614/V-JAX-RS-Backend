package com.example.velocitybackend.models;
import java.util.List;

public class UserModel {
    private String id;
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
    private String userType;
    private List<String> createdTravelsId;
    private Double progression;

    public UserModel() {}

    public UserModel(String id, String email, String password, String firstName, String lastName, String phoneNumber, String profileImageUrl, List<String> friendsId, List<String> bookmarksId, String refreshToken, String createdAt, String userType, List<String> createdTravelsId, Double progression) {
        this.id = id;
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
        this.userType = userType;
        this.createdTravelsId = createdTravelsId;
        this.progression = progression;
    }

    // for some reason, THIS is the reason why response data is userId instead of id (it was getUserId)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public List<String> getCreatedTravelsId() {
        return createdTravelsId;
    }

    public void setCreatedTravelsId(List<String> createdTravelsId) {
        this.createdTravelsId = createdTravelsId;
    }

    public Double getProgression() {
        return progression;
    }

    public void setProgression(Double progression) {
        this.progression = progression;
    }
}
