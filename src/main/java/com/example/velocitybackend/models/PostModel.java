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

    // For review posts only
    private Double rating;
    private String travelId;

    // For comment posts only
    private String postId;

    // For share posts only
    private String sharedPostId;
}
