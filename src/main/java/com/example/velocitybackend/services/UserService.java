package com.example.velocitybackend.services;

import com.example.velocitybackend.DTO.SenderTargetDTO;
import com.example.velocitybackend.models.UserModel;
import com.example.velocitybackend.utils.GeneralUtil;
import com.example.velocitybackend.utils.MongoDBUtil;
import com.example.velocitybackend.utils.UserUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import jakarta.ws.rs.NotFoundException;
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

    public Response friendAction(SenderTargetDTO reqBody, String action) {
        try {
            Document doc1 = collection.find(Filters.eq("_id", new ObjectId(reqBody.getSenderId()))).first();
            Document doc2 = collection.find(Filters.eq("_id", new ObjectId(reqBody.getTargetId()))).first();

            if (doc1 == null || doc2 == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            UserModel user1 = UserUtil.fromDocument(doc1);
            UserModel user2 = UserUtil.fromDocument(doc2);

            List<String> user1Friends = user1.getFriendsId() == null ? new ArrayList<>() : new ArrayList<>(user1.getFriendsId());
            List<String> user2Friends = user2.getFriendsId() == null ? new ArrayList<>() : new ArrayList<>(user2.getFriendsId());

            if (action.equals("add"))
            {
                user1Friends.add(user2.getId());
                user2Friends.add(user1.getId());
            } else if (action.equals("remove")) {
                user1Friends.removeIf(friendId -> friendId.equals(user2.getId()));
                user2Friends.removeIf(friendId -> friendId.equals(user1.getId()));
            }

            user1.setFriendsId(user1Friends);
            user2.setFriendsId(user2Friends);

            collection.updateOne(Filters.eq("_id", new ObjectId(user1.getId())), new Document("$set", UserUtil.filterNullFields(user1)));
            collection.updateOne(Filters.eq("_id", new ObjectId(user2.getId())), new Document("$set", UserUtil.filterNullFields(user2)));

            return Response.ok(GeneralUtil.getMessage("Success")).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    public Response toggleBookmark(SenderTargetDTO reqBody) {
        try {
            Document userDoc = collection.find(Filters.eq("_id", new ObjectId(reqBody.getSenderId()))).first();

            if (userDoc == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(GeneralUtil.getError("User not found")).build();
            }

            UserModel user = UserUtil.fromDocument(userDoc);

            List<String> userBookmarkedTravelsList = user.getBookmarksId() == null ? new ArrayList<>() : new ArrayList<>(user.getBookmarksId());

            if (userBookmarkedTravelsList.contains(reqBody.getTargetId())) {
                userBookmarkedTravelsList.removeIf(travelId -> travelId.equals(reqBody.getTargetId()));
            } else {
                userBookmarkedTravelsList.add(reqBody.getTargetId());
            }

            user.setBookmarksId(userBookmarkedTravelsList);

            collection.updateOne(Filters.eq("_id", new ObjectId(user.getId())), new Document("$set", UserUtil.filterNullFields(user)));

            return Response.ok(GeneralUtil.getMessage("Success")).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void increaseUserProgression(String id, Double point) {
        try {
            Document userDoc = collection.find(Filters.eq("_id", new ObjectId(id))).first();

            if (userDoc == null) {
                throw new NotFoundException("User not found");
            }

            UserModel user = UserUtil.fromDocument(userDoc);

            if (user.getProgression() == null) {
                user.setProgression(point);
            } else {
                user.setProgression(user.getProgression() + point);
            }

            collection.updateOne(Filters.eq("_id", new ObjectId(user.getId())), new Document("$set", UserUtil.filterNullFields(user)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}