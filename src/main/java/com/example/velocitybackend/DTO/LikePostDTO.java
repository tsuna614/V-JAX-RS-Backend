package com.example.velocitybackend.DTO;

public class LikePostDTO {
    private String postId;
    private String userId;

    public LikePostDTO() {}

    public LikePostDTO(String postId, String userId) {
        this.postId = postId;
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
