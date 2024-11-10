package com.example.velocitybackend.resources;

import com.example.velocitybackend.models.UserModel;
import com.example.velocitybackend.services.UserService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    final private UserService userService = new UserService();

    @GET
    public Response getUsers() {
        return userService.getAllUsers();
    }

    @GET
    @Path("/getUserById/{userId}")
    public Response getUsersById(@PathParam("userId") String userId) {
        return userService.getUserById(userId);
    }

    @POST
    public Response createUser(UserModel user) {
        return userService.createUser(user);
    }

    @PUT
    @Path("/{userId}")
    public Response updateUser(@PathParam("userId") String userId, UserModel user) {
        return userService.updateUser(userId, user);
    }

    @DELETE
    @Path("/{userId}")
    public Response deleteUser(@PathParam("userId") String userId) {
        return userService.deleteUser(userId);
    }

}

