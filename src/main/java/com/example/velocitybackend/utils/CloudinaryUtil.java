package com.example.velocitybackend.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.cloudinary.Api;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Map;

@Path("/cloudinary")
public class CloudinaryUtil {

    private static final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", Config.get("CLOUDINARY_CLOUD_NAME"),
            "api_key", Config.get("CLOUDINARY_API_KEY"),
            "api_secret", Config.get("CLOUDINARY_API_SECRET")
    ));

    @POST
    @Path("/uploadImage")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadImage(Map<String, String> request) {
        try {
            String imagePath = request.get("imagePath");
            String id = request.get("id");
            Map uploadResult = cloudinary.uploader().upload(imagePath, ObjectUtils.asMap(
                    "public_id", id,
                    "folder", "user_images"
            ));
            return Response.ok(uploadResult).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/uploadVideo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadVideo(Map<String, String> request) {
        try {
            String videoPath = request.get("videoPath");
            String id = request.get("id");
            Map uploadResult = cloudinary.uploader().upload(videoPath, ObjectUtils.asMap(
                    "public_id", id,
                    "resource_type", "video",
                    "folder", "user_videos"
            ));
            return Response.ok(uploadResult).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/deleteItem")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteItem(@QueryParam("id") String id, @QueryParam("resourceType") String resourceType) {
        try {
            Map destroyResult = cloudinary.uploader().destroy(id, ObjectUtils.asMap(
                    "resource_type", resourceType
            ));
            return Response.ok(destroyResult).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
