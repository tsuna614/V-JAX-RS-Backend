package com.example.velocitybackend.utils;

import com.example.velocitybackend.models.PostModel;
import org.bson.Document;

import java.time.LocalDateTime;

public class PostUtil {
    // Convert MongoDB Document to PostModel
    public static PostModel fromDocument(Document doc) {
        PostModel post = new PostModel();
        post.setId(doc.getObjectId("_id").toString());
        post.setUserId(doc.getString("userId"));
        post.setContent(doc.getString("content"));
        post.setImageUrl(doc.getString("imageUrl"));
        post.setContentType(doc.getString("contentType"));
        post.setLikes(doc.getList("likes", String.class));
        post.setComments(doc.getList("comments", String.class));
        post.setShares(doc.getList("shares", String.class));
        post.setCreatedAt(doc.getString("createdAt"));
        // review posts
        post.setRating(doc.getDouble("rating"));
        post.setTravelId(doc.getString("travelId"));
        // comment posts
        post.setPostId(doc.getString("postId"));
        // share posts
        post.setSharedPostId(doc.getString("sharedPostId"));


        return post;
    }

    // Convert PostModel to MongoDB Document
    public static Document toDocument(PostModel post) {
        return new Document()
                .append("userId", post.getUserId())
                .append("content", post.getContent())
                .append("imageUrl", post.getImageUrl())
                .append("contentType", post.getContentType())
                .append("likes", post.getLikes())
                .append("comments", post.getComments())
                .append("shares", post.getShares())
                .append("rating", post.getRating())
                .append("travelId", post.getTravelId())
                .append("postId", post.getPostId())
                .append("sharedPostId", post.getSharedPostId())
                .append("createdAt", post.getCreatedAt() == null ? LocalDateTime.now().toString() : post.getCreatedAt());

    }

    // Validate the required fields of PostModel
    public static String validatePost(PostModel post) {
        if (post.getUserId() == null || post.getUserId().isEmpty()) {
            return "User ID is required.";
        }
        return null; // No validation errors
    }

    // Create a Document for updating fields (ignore null values)
    public static Document filterNullFields(PostModel post) {
        Document document = new Document();

        if (post.getUserId() != null) {
            document.append("userId", post.getUserId());
        }
        if (post.getContent() != null) {
            document.append("content", post.getContent());
        }
        if (post.getImageUrl() != null) {
            document.append("imageUrl", post.getImageUrl());
        }
        if (post.getContentType() != null) {
            document.append("contentType", post.getContentType());
        }
        if (post.getLikes() != null) {
            document.append("likes", post.getLikes());
        }
        if (post.getComments() != null) {
            document.append("comments", post.getComments());
        }
        if (post.getShares() != null) {
            document.append("shares", post.getShares());
        }

        // Additional fields for review posts
        if (post.getRating() != null) {
            document.append("rating", post.getRating());
        }
        if (post.getTravelId() != null) {
            document.append("travelId", post.getTravelId());
        }

        // Additional fields for comment posts
        if (post.getPostId() != null) {
            document.append("postId", post.getPostId());
        }

        // Additional fields for share posts
        if (post.getSharedPostId() != null) {
            document.append("sharedPostId", post.getSharedPostId());
        }

        return document;
    }
}
