package com.example.velocitybackend.resources;

import com.example.velocitybackend.DTO.RefreshTokenDTO;
import com.example.velocitybackend.models.UserModel;
import com.example.velocitybackend.services.AuthService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {
    final AuthService authService = new AuthService();

    @POST
    @Path("/register")
    public Response register(UserModel user) {
        return authService.register(user);
    }

    @POST
    @Path("/login")
    public Response login(UserModel user) {
        return authService.login(user.getEmail(), user.getPassword());
    }

    @POST
    @Path("/refresh")
    public Response refresh(RefreshTokenDTO tokenDTO) {
        return authService.refreshAccessToken(tokenDTO);
    }
}
