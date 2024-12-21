package com.example.velocitybackend.resources;

import com.example.velocitybackend.models.TravelModel;
import com.example.velocitybackend.services.TravelService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/travel")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TravelResource {
    final TravelService travelService = new TravelService();


    @GET
    public Response getAllTravels() {
        return travelService.getAllTravels();
    }

    @GET
    @Path("/getTravelByPage/")
    public Response getTravelByPage(@QueryParam("travelType") String travelType, @QueryParam("page") int page) {
        return travelService.getTravelByPage(travelType, page);
    }

    // (according to me) id should be at the very bottom
    @GET
    @Path("/{id}")
    public Response getTravelById(@PathParam("id") String id) {
        return travelService.getTravelById(id);
    }

    @POST
    public  Response createTravel(TravelModel travel) {
        return travelService.createTravel(travel);
    }

    @PUT
    @Path("/{id}")
    public Response updateTravelById(@PathParam("id") String id, TravelModel travel) {
        return travelService.updateTravel(id, travel);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTravelById(@PathParam("id") String id) {
        return travelService.deleteTravel(id);
    }
}
