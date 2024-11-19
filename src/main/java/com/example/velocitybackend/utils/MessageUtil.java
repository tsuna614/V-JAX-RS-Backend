package com.example.velocitybackend.utils;

import com.example.velocitybackend.models.MessageModel;
import org.bson.Document;

import java.time.LocalDateTime;
import java.util.Objects;

public class MessageUtil {
    public static MessageModel fromDocument(Document doc) {
        MessageModel message = new MessageModel();
        message.setId(doc.getObjectId("_id").toString());
        message.setConversationId(doc.getString("conversationId"));
        message.setMessage(doc.getString("message"));
        message.setSender(doc.getString("sender"));
        message.setReceiver(doc.getString("receiver"));
        message.setCreatedAt(doc.getString("createdAt"));
        return message;
    }

    public static Document toDocument(MessageModel message) {
        Document doc = new Document();
        doc.append("conversationId", getConversationId(message.getSender(), message.getReceiver()));
        doc.append("message", message.getMessage());
        doc.append("sender", message.getSender());
        doc.append("receiver", message.getReceiver());
        doc.append("createdAt", message.getCreatedAt() == null ? LocalDateTime.now().toString() : message.getCreatedAt());
        return doc;
    }

    public static String validateMessage(MessageModel message) {
        if (message.getSender() == null || message.getSender().isEmpty()) {
            return "Sender's id cannot by empty";
        }
        if (message.getReceiver() == null || message.getReceiver().isEmpty()) {
            return "Receiver's id cannot be empty";
        }
        if (Objects.equals(message.getSender(), message.getReceiver())) {
            return "Sender and receiver cannot be the same";
        }
        if (message.getMessage() == null || message.getMessage().isEmpty()) {
            return "Message cannot be empty";
        }
        return null;
    }

    public static Document filterMessage(MessageModel message) {
        Document doc = new Document();
        if (message.getConversationId() != null) {
            doc.append("conversationId", message.getConversationId());
        }
        if (message.getMessage() != null) {
            doc.append("message", message.getMessage());
        }
        if (message.getSender() != null) {
            doc.append("sender", message.getSender());
        }
        if (message.getReceiver() != null) {
            doc.append("receiver", message.getReceiver());
        }
        if (message.getCreatedAt() != null) {
            doc.append("createdAt", message.getCreatedAt());
        }
        return doc;
    }

    private static String getConversationId(String sender, String receiver) {
        if (sender.compareTo(receiver) < 0) {
            return sender + "-" + receiver;
        } else {
            return receiver + "-" + sender;
        }
    }
}
