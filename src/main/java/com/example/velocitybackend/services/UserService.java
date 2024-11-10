package com.example.velocitybackend.services;

import com.example.velocitybackend.models.UserModel;
import com.example.velocitybackend.utils.GeneralUtil;
import com.example.velocitybackend.utils.MongoDBUtil;
import com.example.velocitybackend.utils.UserUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import jakarta.ws.rs.core.Response;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    final private MongoCollection<Document> collection = MongoDBUtil.getUserCollection();

    public UserService() {
    }

    public Response getAllUsers() {
        List<UserModel> users = new ArrayList<>();
        for (Document doc : collection.find()) {
            users.add(UserUtil.fromDocument(doc));
        }
        return Response.ok(users).build();
    }

    public Response getUserById(String userId) {
        Document userDoc = collection.find(Filters.eq("_id", new ObjectId(userId))).first();
        if (userDoc == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(UserUtil.fromDocument(userDoc)).build();
    }

    public Response createUser(UserModel user) {
        // validate the required fields (e.g. email, password)
        // if requirement are not met, respond with bad request and error message
        String validateMessage = UserUtil.validateUser(user);
        if (validateMessage != null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(GeneralUtil.getError(validateMessage)).build();
        }
        System.out.println("Local date time: " + LocalDateTime.now());
        System.out.println("Java util time: " + new java.util.Date());
        Document doc = UserUtil.toDocument(user);
        collection.insertOne(doc);
        user.setUserId(doc.getObjectId("_id").toString());
        return Response.status(Response.Status.CREATED).entity(user).build();
    }

    public Response updateUser(String userId, UserModel user) {
        // append all fields from the user except the one that is null and return a Document
        Document updatedDoc = UserUtil.filterNullFields(user);

        collection.updateOne(Filters.eq("_id", new ObjectId(userId)), new Document("$set", updatedDoc));
        user.setUserId(userId);
        return Response.ok(GeneralUtil.getMessage("User updated successfully")).build();
    }

    public Response deleteUser(String userId) {
        Document response = collection.findOneAndDelete(Filters.eq("_id", new ObjectId(userId)));

        if (response == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(GeneralUtil.getError("User not found")).build();
        }
        return Response.ok(GeneralUtil.getMessage("User deleted successfully")).build();
    }
}