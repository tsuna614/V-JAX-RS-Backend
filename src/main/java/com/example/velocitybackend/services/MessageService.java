package com.example.velocitybackend.services;


import com.example.velocitybackend.models.MessageModel;
import com.example.velocitybackend.utils.MessageUtil;
import com.example.velocitybackend.utils.GeneralUtil;
import com.example.velocitybackend.utils.MongoDBUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import jakarta.ws.rs.core.Response;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class MessageService {
    final private MongoCollection<Document> collection = MongoDBUtil.getMessageCollection();

    public Response getAllMessages() {
        try {
            final List<MessageModel> messages = new ArrayList<>();
            for (Document document : collection.find()) {
                messages.add(MessageUtil.fromDocument(document));
            }
            return Response.ok(messages).build();
        } catch (Exception error) {
            return Response.status(500).entity(error.getMessage()).build();
        }
    }

    public Response getMessageById(String id) {
        try {
            final Document response = collection.find(Filters.eq("_id", new ObjectId(id))).first();
            if (response == null) {
                return Response.status(404).entity("No message with the id " + id + " was found.").build();
            }
            return Response.ok(MessageUtil.fromDocument(response)).build();
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

    public Response createMessage(MessageModel message) {
        try {
            String validateMessage = MessageUtil.validateMessage(message);
            if (validateMessage != null) {
                return Response.status(500).entity(validateMessage).build();
            }
            Document doc = MessageUtil.toDocument(message);
            collection.insertOne(doc);
//            message.setId(doc.getObjectId("_id").toString());
            return Response.ok(MessageUtil.fromDocument(doc)).build();
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

    public Response updateMessage(String id, MessageModel message) {
        try {
            Document reqBody = MessageUtil.filterMessage(message);

            collection.updateOne(Filters.eq("_id", new ObjectId(id)), reqBody);
            return Response.ok(GeneralUtil.getMessage("Message updated successfully")).build();
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

    public Response deleteMessage(String id) {
        try {
            Document response = collection.findOneAndDelete(Filters.eq("_id", new ObjectId(id)));

            if (response == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(GeneralUtil.getError("Message not found")).build();
            }
            return Response.ok(GeneralUtil.getMessage("Message deleted successfully")).build();
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }
}
