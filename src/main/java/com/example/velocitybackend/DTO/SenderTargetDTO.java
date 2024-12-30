package com.example.velocitybackend.DTO;

public class SenderTargetDTO {
    private String senderId;
    private String targetId;

    public SenderTargetDTO() {}

    public SenderTargetDTO(String senderId, String targetId) {
        this.senderId = senderId;
        this.targetId = targetId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }
}
