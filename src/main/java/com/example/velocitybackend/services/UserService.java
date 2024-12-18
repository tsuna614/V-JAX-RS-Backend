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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {
    final private MongoCollection<Document> collection = MongoDBUtil.getUserCollection();

    public UserService() {
    }

    public Response getAllUsers() {
        try {
            List<UserModel> users = new ArrayList<>();
            for (Document doc : collection.find()) {
                users.add(UserUtil.fromDocument(doc));
            }
            return Response.ok(users).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    public Response getUserById(String id) {
        try {
            Document userDoc = collection.find(Filters.eq("_id", new ObjectId(id))).first();
            if (userDoc == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(UserUtil.fromDocument(userDoc)).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    public Response getUserByEmail(String email) {
        try {
            Document userDoc = collection.find(Filters.eq("email", email)).first();
            if (userDoc == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(UserUtil.fromDocument(userDoc)).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    // for authentication service
    public Optional<UserModel> getUserEmail(String email) {
        try {
            Document userDoc = collection.find(Filters.eq("email", email)).first();
            if (userDoc == null) {
                return Optional.empty();
            }
            return Optional.of(UserUtil.fromDocument(userDoc));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Response createUser(UserModel user) {
        try {
            // validate the required fields (e.g. email, password)
            // if requirement are not met, respond with bad request and error message
            String validateMessage = UserUtil.validateUser(user);
            if (validateMessage != null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(GeneralUtil.getError(validateMessage)).build();
            }
            Document doc = UserUtil.toDocument(user);
            collection.insertOne(doc);
            user.setId(doc.getObjectId("_id").toString());
            return Response.status(Response.Status.CREATED).entity(user).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    public Response updateUser(String id, UserModel user) {
        try {
            // append all fields from the user except the one that is null and return a Document
            Document updatedDoc = UserUtil.filterNullFields(user);

            collection.updateOne(Filters.eq("_id", new ObjectId(id)), new Document("$set", updatedDoc));
            user.setId(id);
            return Response.ok(GeneralUtil.getMessage("User updated successfully")).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    public Response deleteUser(String id) {
        try {
            Document response = collection.findOneAndDelete(Filters.eq("_id", new ObjectId(id)));

            if (response == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(GeneralUtil.getError("User not found")).build();
            }
            return Response.ok(GeneralUtil.getMessage("User deleted successfully")).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}