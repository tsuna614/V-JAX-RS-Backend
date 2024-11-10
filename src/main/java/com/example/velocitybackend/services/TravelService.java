package com.example.velocitybackend.services;

import com.example.velocitybackend.models.TravelModel;
import com.example.velocitybackend.utils.GeneralUtil;
import com.example.velocitybackend.utils.MongoDBUtil;
import com.example.velocitybackend.utils.TravelUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import jakarta.ws.rs.core.Response;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class TravelService {
    final private MongoCollection<Document> collection = MongoDBUtil.getTravelCollection();

    public Response getAllTravels() {
        List<TravelModel> travels = new ArrayList<>();
        for (Document doc : collection.find()) {
            travels.add(TravelUtil.fromDocument(doc));
        }
        return Response.ok(travels).build();
    }

    public Response getTravelById(String id) {
        Document travelDoc = collection.find(Filters.eq("_id", new ObjectId(id))).first();
        if (travelDoc == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(TravelUtil.fromDocument(travelDoc)).build();
    }

    public Response createTravel(TravelModel travel) {
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
    }

    public Response updateTravel(String id, TravelModel travel) {
        Document updatedDoc = TravelUtil.filterNullFields(travel);

        collection.updateOne(Filters.eq("_id", new ObjectId(id)), new Document("$set", updatedDoc));
        travel.setId(id);
        return Response.ok(GeneralUtil.getMessage("Travel updated successfully")).build();
    }

    public Response deleteTravel(String id) {
        Document response = collection.findOneAndDelete(Filters.eq("_id", new ObjectId(id)));

        if (response == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(GeneralUtil.getError("Travel not found")).build();
        }
        return Response.ok(GeneralUtil.getMessage("Travel deleted successfully")).build();
    }
}
