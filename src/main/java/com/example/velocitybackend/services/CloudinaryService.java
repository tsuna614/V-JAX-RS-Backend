package com.example.velocitybackend.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.velocitybackend.utils.Config;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Map;
import java.util.UUID;

public class CloudinaryService {
    private static final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", Config.get("CLOUDINARY_CLOUD_NAME"),
            "api_key", Config.get("CLOUDINARY_API_KEY"),
            "api_secret", Config.get("CLOUDINARY_API_SECRET")
    ));

    public Response uploadImage(String imagePath) {
        try {
            String id = UUID.randomUUID().toString();
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


    public Response uploadVideo(String videoPath) {
        try {
            String id = UUID.randomUUID().toString();
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


    public Response deleteItem(String id, String resourceType) {
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
