package com.example.velocitybackend.resources;

import com.example.velocitybackend.models.UserModel;
import com.example.velocitybackend.services.UserService;
import com.example.velocitybackend.utils.MongoDBUtil;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    final private UserService userService = new UserService();

    @GET
    public List<UserModel> getUsers() {
        return userService.getAllUsers();
    }

    @POST
    public Response createUser(UserModel user) {
        return userService.createUser(user);
    }

//    @PUT
//    @Path("/{userId}")
//    public Response updateUser(@PathParam("userId") String userId, UserModel user) {
//        Document updatedDoc = new Document("username", user.getUsername())
//                .append("email", user.getEmail());
//        collection.updateOne(Filters.eq("_id", new ObjectId(userId)), new Document("$set", updatedDoc));
//        user.setUserId(userId);
//        return Response.ok(user).build();
//    }
//
//    @DELETE
//    @Path("/{userId}")
//    public Response deleteUser(@PathParam("userId") String userId) {
//        collection.deleteOne(Filters.eq("_id", new ObjectId(userId)));
//        return Response.noContent().build();
//    }
}

