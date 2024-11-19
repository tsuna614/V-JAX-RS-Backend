package com.example.velocitybackend.resources;

import com.example.velocitybackend.models.MessageModel;
import com.example.velocitybackend.services.MessageService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/message")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {
    final MessageService bookService = new MessageService();

    @GET
    public Response getAllMessages() {
        return bookService.getAllMessages();
    }

    @GET
    @Path("/{id}")
    public Response getMessageById(@PathParam("id") String id) {
        return bookService.getMessageById(id);
    }

    @POST
    public  Response createMessage(MessageModel message) {
        return bookService.createMessage(message);
    }

    @PUT
    @Path("/{id}")
    public Response updateMessageById(@PathParam("id") String id, MessageModel message) {
        return bookService.updateMessage(id, message);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteMessageById(@PathParam("id") String id) {
        return bookService.deleteMessage(id);
    }
}
