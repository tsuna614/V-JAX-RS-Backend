package com.example.velocitybackend.services;

import com.example.velocitybackend.models.UserModel;
import com.example.velocitybackend.utils.UserUtil;
import jakarta.ws.rs.core.Response;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

public class AuthService {
    final UserService userService = new UserService();

    public Response login(String email, String password) {
        try {
            Optional<UserModel> response = userService.getUserEmail(email);
            if (response.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("User not found.")
                        .build();
            }

            UserModel user = response.get();

            System.out.println(password);
            System.out.println(user.getPassword());

            if (!BCrypt.checkpw(password, user.getPassword())) {
                return Response.status(401).entity("Invalid password.").build();
            }

            return Response.ok("Login successfully").build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    public Response register(UserModel user) {
        try {
            Optional<UserModel> response = userService.getUserEmail(user.getEmail());

            // the email is already in use
            if (response.isPresent()) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("The email that you entered is already in use! Please try again.")
                        .build();
            }

            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            user.setPassword(hashedPassword);

            // might need to work on this later
            userService.createUser(user);

            return Response.ok("User registered successfully.").build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
