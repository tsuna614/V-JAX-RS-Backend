package com.example.velocitybackend.services;

import com.example.velocitybackend.DTO.AccessTokenDTO;
import com.example.velocitybackend.DTO.AuthResponseDTO;
import com.example.velocitybackend.DTO.RefreshTokenDTO;
import com.example.velocitybackend.models.UserModel;
import com.example.velocitybackend.utils.Config;
import com.example.velocitybackend.utils.UserUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.ws.rs.core.Response;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Date;
import java.util.Optional;

public class AuthService {
    final UserService userService = new UserService();

    String accessTokenSecret = Config.get("ACCESS_TOKEN_SECRET");
    String refreshTokenSecret = Config.get("REFRESH_TOKEN_SECRET");

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

            String accessToken = Jwts.builder()
                    .setSubject(email)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15)) // 15 minutes
                    .signWith(Keys.hmacShaKeyFor(accessTokenSecret.getBytes()))
                    .compact();

            String refreshToken = Jwts.builder()
                    .setSubject(email)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 1 day
                    .signWith(Keys.hmacShaKeyFor(refreshTokenSecret.getBytes()))
                    .compact();

            UserModel updatedRefreshToken = new UserModel();
            updatedRefreshToken.setRefreshToken(refreshToken);

            userService.updateUser(user.getId(), updatedRefreshToken);

            return Response.ok(new AuthResponseDTO("Login successfully", accessToken, refreshToken, user)).build();
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

    public Response refreshAccessToken(RefreshTokenDTO tokenDTO) {
        String accessToken = tokenDTO.getAccessToken();
        String refreshToken = tokenDTO.getRefreshToken();

        try {
            Jwts.parserBuilder()
                    .setSigningKey(accessTokenSecret.getBytes())
                    .build()
                    .parseClaimsJws(accessToken);
            return Response.status(400).entity("Access token is still valid.").build();
        } catch (ExpiredJwtException e) {
            String email = e.getClaims().getSubject();
            Optional<UserModel> response = userService.getUserEmail(email);

            if (response.isEmpty()) {
                return Response.status(401).entity("User not found.").build();
            }

            UserModel user = response.get();

            if (!refreshToken.equals(user.getRefreshToken())) {
                return Response.status(400).entity("Invalid refresh token.").build();
            }

            String newAccessToken = Jwts.builder()
                    .setSubject(email)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15)) // 15 minutes
                    .signWith(Keys.hmacShaKeyFor(accessTokenSecret.getBytes()))
                    .compact();

            return Response.ok(new AccessTokenDTO(newAccessToken)).build();
        }
    }
}
