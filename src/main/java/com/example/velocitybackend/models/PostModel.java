package com.example.velocitybackend.models;

import java.util.List;

public class PostModel {
    // Fields
    private String id;
    private String userId;
    private String content;
    private String imageUrl;
    private String contentType;
    private List<String> likes;
    private List<String> comments;
    private List<String> shares;
    private String createdAt;

    // For review posts only
    private Double rating;
    private String travelId;

    // For comment posts only
    private String postId;

    // For share posts only
    private String sharedPostId;

    public PostModel() {}

    public PostModel(String id, String userId, String content, String imageUrl, String contentType, List<String> likes, List<String> comments, List<String> shares, Double rating, String travelId, String postId, String sharedPostId, String createdAt) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.imageUrl = imageUrl;
        this.contentType = contentType;
        this.likes = likes;
        this.comments = comments;
        this.shares = shares;
        this.rating = rating;
        this.travelId = travelId;
        this.postId = postId;
        this.sharedPostId = sharedPostId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public List<String> getShares() {
        return shares;
    }

    public void setShares(List<String> shares) {
        this.shares = shares;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getTravelId() {
        return travelId;
    }

    public void setTravelId(String travelId) {
        this.travelId = travelId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getSharedPostId() {
        return sharedPostId;
    }

    public void setSharedPostId(String sharedPostId) {
        this.sharedPostId = sharedPostId;
    }
}
