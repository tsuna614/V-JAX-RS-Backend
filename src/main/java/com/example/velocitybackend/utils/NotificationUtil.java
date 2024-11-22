package com.example.velocitybackend.utils;

import com.example.velocitybackend.models.NotificationModel;
import org.bson.Document;

import java.time.LocalDateTime;

public class NotificationUtil {
    public static NotificationModel fromDocument(Document doc) {
        NotificationModel notification = new NotificationModel();
        notification.setId(doc.getObjectId("_id").toString());
        notification.setType(doc.getString("type"));
        notification.setSender(doc.getString("sender"));
        notification.setReceiver(doc.getString("receiver"));
        notification.setCreatedAt(doc.getString("createdAt"));
        return notification;
    }

    public static Document toDocument(NotificationModel notification) {
        Document doc = new Document();
        doc.append("type", notification.getType());
        doc.append("sender", notification.getSender());
        doc.append("receiver", notification.getReceiver());
        doc.append("createdAt", notification.getCreatedAt() == null ? LocalDateTime.now().toString() : notification.getCreatedAt());
        return doc;
    }

    public static String validateNotification(NotificationModel notification) {
        if (notification.getType() == null || notification.getType().isEmpty()) {
            return "Type cannot be empty";
        }
        if (notification.getSender() == null || notification.getSender().isEmpty()) {
            return "Sender's id cannot be empty";
        }
        if (notification.getReceiver() == null || notification.getReceiver().isEmpty()) {
            return "Receiver's id cannot be empty";
        }
        return null;
    }

    public static Document filterNotification(NotificationModel notification) {
        Document doc = new Document();
        if (notification.getType() != null) {
            doc.append("type", notification.getType());
        }
        if (notification.getReceiver() != null) {
            doc.append("receiver", notification.getReceiver());
        }
        if (notification.getSender() != null) {
            doc.append("sender", notification.getSender());
        }
        if (notification.getCreatedAt() != null) {
            doc.append("createdAt", notification.getCreatedAt());
        }
        return doc;
    }
}
