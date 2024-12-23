package com.example.velocitybackend.services;

import com.example.velocitybackend.models.NotificationModel;
import com.example.velocitybackend.utils.NotificationUtil;
import com.example.velocitybackend.utils.GeneralUtil;
import com.example.velocitybackend.utils.MongoDBUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import jakarta.ws.rs.core.Response;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    final private MongoCollection<Document> notificationCollection = MongoDBUtil.getNotificationCollection();

    public Response getAllNotifications() {
        try {
            final List<NotificationModel> notifications = new ArrayList<>();
            for (Document document : notificationCollection.find()) {
                notifications.add(NotificationUtil.fromDocument(document));
            }
            return Response.ok(notifications).build();
        } catch (Exception error) {
            return Response.status(500).entity(error.getMessage()).build();
        }
    }

    public Response getNotificationById(String id) {
        try {
            final Document response = notificationCollection.find(Filters.eq("_id", new ObjectId(id))).first();
            if (response == null) {
                return Response.status(404).entity("No notification with the id " + id + " was found.").build();
            }
            return Response.ok(NotificationUtil.fromDocument(response)).build();
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

    public Response createNotification(NotificationModel notification) {
        try {
            String validateMessage = NotificationUtil.validateNotification(notification);
            if (validateMessage != null) {
                return Response.status(500).entity(validateMessage).build();
            }
            Document doc = NotificationUtil.toDocument(notification);
            notificationCollection.insertOne(doc);
            notification.setId(doc.getObjectId("_id").toString());
            return Response.ok(notification).build();
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

    public Response updateNotification(String id, NotificationModel notification) {
        try {
            Document reqBody = NotificationUtil.filterNotification(notification);

            notificationCollection.updateOne(Filters.eq("_id", new ObjectId(id)), reqBody);
            return Response.ok(GeneralUtil.getMessage("Notification updated successfully")).build();
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

    public Response deleteNotification(String id) {
        try {
            Document response = notificationCollection.findOneAndDelete(Filters.eq("_id", new ObjectId(id)));

            if (response == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(GeneralUtil.getError("Notification not found")).build();
            }
            return Response.ok(GeneralUtil.getMessage("Notification deleted successfully")).build();
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }
}
