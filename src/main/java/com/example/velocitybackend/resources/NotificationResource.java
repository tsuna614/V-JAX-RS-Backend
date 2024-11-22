package com.example.velocitybackend.resources;

import com.example.velocitybackend.models.NotificationModel;
import com.example.velocitybackend.services.NotificationService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/notification")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class NotificationResource {
    final NotificationService notificationService = new NotificationService();

    @GET
    public Response getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @GET
    @Path("/{id}")
    public Response getNotificationById(@PathParam("id") String id) {
        return notificationService.getNotificationById(id);
    }

    @POST
    public  Response createNotification(NotificationModel notification) {
        return notificationService.createNotification(notification);
    }

    @PUT
    @Path("/{id}")
    public Response updateNotificationById(@PathParam("id") String id, NotificationModel notification) {
        return notificationService.updateNotification(id, notification);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteNotificationById(@PathParam("id") String id) {
        return notificationService.deleteNotification(id);
    }
}
