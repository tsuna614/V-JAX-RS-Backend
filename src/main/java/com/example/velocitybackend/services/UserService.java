package com.example.velocitybackend.services;

import com.example.velocitybackend.models.UserModel;
import com.example.velocitybackend.utils.MongoDBUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import jakarta.ws.rs.core.Response;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private MongoCollection<Document> collection = MongoDBUtil.getUserCollection();

    public UserService() {
    }

    public List<UserModel> getAllUsers() {
        List<UserModel> users = new ArrayList<>();
        for (Document doc : collection.find()) {
            UserModel user = new UserModel();
            user.setUserId(doc.getObjectId("_id").toString());
            user.setEmail(doc.getString("email"));
            user.setPassword(doc.getString("password"));
            user.setFirstName(doc.getString("firstName"));
            user.setLastName(doc.getString("lastName"));
            user.setPhoneNumber(doc.getString("phoneNumber"));
            user.setProfileImageUrl(doc.getString("profileImageUrl"));
            user.setFriendsId(doc.getList("friendsId", String.class));
            user.setBookmarksId(doc.getList("bookmarksId", String.class));
            user.setRefreshToken(doc.getString("refreshToken"));
            users.add(user);
        }
        return users;
    }

    public Response createUser(UserModel user) {
        Document doc = new Document()
                .append("email", user.getEmail())
                .append("password", user.getPassword())
                .append("firstName", user.getFirstName())
                .append("lastName", user.getLastName())
                .append("phoneNumber", user.getPhoneNumber())
                .append("profileImageUrl", user.getProfileImageUrl())
                .append("friendsId", user.getFriendsId())
                .append("bookmarksId", user.getBookmarksId())
                .append("refreshToken", user.getRefreshToken())
                .append("createdAt", LocalDateTime.now());
        collection.insertOne(doc);
        user.setUserId(doc.getObjectId("_id").toString());
        System.out.println(user);
        return Response.status(Response.Status.CREATED).entity(user).build();
    }

//    public Response updateUser(UserModel user) {
//        Document updatedDoc = new Document("username", user.getUsername())
//                .append("email", user.getEmail());
//        collection.updateOne(Filters.eq("_id", new ObjectId(userId)), new Document("$set", updatedDoc));
//        user.setUserId(userId);
//        return Response.ok(user).build();
//    }
}