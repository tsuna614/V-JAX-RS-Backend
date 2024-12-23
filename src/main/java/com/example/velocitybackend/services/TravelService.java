package com.example.velocitybackend.services;

import com.example.velocitybackend.models.PostModel;
import com.example.velocitybackend.models.TravelModel;
import com.example.velocitybackend.utils.GeneralUtil;
import com.example.velocitybackend.utils.MongoDBUtil;
import com.example.velocitybackend.utils.TravelUtil;
import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import jakarta.ws.rs.core.Response;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.List;

public class TravelService {
    final private MongoCollection<Document> collection = MongoDBUtil.getTravelCollection();
    final PostService postService = new PostService();

    private int getTravelRating(String travelId) {
        int totalRating = 0;

        List<PostModel> ratingPostList;

        try (final Response response = postService.getAllRatingPosts(travelId)) {
            if (response.getStatus() == 200) {
                ratingPostList = (List<PostModel>) response.getEntity();

                if (ratingPostList == null || ratingPostList.isEmpty()) return 0;

                for (PostModel post : ratingPostList) {
                    totalRating += post.getRating();
                }

                return totalRating / ratingPostList.size();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public Response getAllTravels() {
        try {
            List<TravelModel> travels = new ArrayList<>();
            for (Document doc : collection.find()) {
                travels.add(TravelUtil.fromDocument(doc));
            }
            return Response.ok(travels).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    public Response getTravelByPage(String travelType, int page) {
        try {
            // page starts from 1, not 0
            if (page <= 0) return Response.status(Response.Status.BAD_REQUEST).build();

            List<TravelModel> travels = new ArrayList<>();
            for (Document doc : collection.find(
                    Filters.eq("travelType", travelType)
            )) {
                travels.add(TravelUtil.fromDocument(doc));
            }

            // filter the travel based on the chosen page
            List<TravelModel> filteredTravels = new ArrayList<>();

            int indexOfFirstObject = (page - 1) * 5;

            for (int i = indexOfFirstObject; i < indexOfFirstObject + 5; i++) {
                // because you can't get travels[10] if travels length is 10
                if (travels.size() > i) {
                    travels.get(i).setRating(getTravelRating(travels.get(i).getId()));
                    filteredTravels.add(travels.get(i));
                }
            }

            return Response.ok(filteredTravels).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    public Response getTravelById(String id) {
        try {
            Document travelDoc = collection.find(Filters.eq("_id", new ObjectId(id))).first();
            if (travelDoc == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(TravelUtil.fromDocument(travelDoc)).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    public Response createTravel(TravelModel travel) {
        try {
            // validate the required fields (e.g. email, password)
            // if requirement are not met, respond with bad request and error message
            String validateMessage = TravelUtil.validateTravel(travel);
            if (validateMessage != null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(GeneralUtil.getError(validateMessage)).build();
            }
            Document doc = TravelUtil.toDocument(travel);
            collection.insertOne(doc);
            travel.setId(doc.getObjectId("_id").toString());
            return Response.status(Response.Status.CREATED).entity(travel).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
    public Response updateTravel(String id, TravelModel travel) {
        try {
            Document updatedDoc = TravelUtil.filterNullFields(travel);

            collection.updateOne(Filters.eq("_id", new ObjectId(id)), new Document("$set", updatedDoc));
            travel.setId(id);
            return Response.ok(GeneralUtil.getMessage("Travel updated successfully")).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    public Response deleteTravel(String id) {
        try {
            Document response = collection.findOneAndDelete(Filters.eq("_id", new ObjectId(id)));

            if (response == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(GeneralUtil.getError("Travel not found")).build();
            }
            return Response.ok(GeneralUtil.getMessage("Travel deleted successfully")).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
