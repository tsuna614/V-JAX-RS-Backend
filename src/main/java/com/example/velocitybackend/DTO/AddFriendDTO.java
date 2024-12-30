package com.example.velocitybackend.DTO;

public class AddFriendDTO {
    private String senderId;
    private String receiverId;

    public AddFriendDTO() {}

    public AddFriendDTO(String senderId, String receiverId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }
}
