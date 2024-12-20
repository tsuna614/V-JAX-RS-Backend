package com.example.velocitybackend.resources;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.cloudinary.Api;
import com.example.velocitybackend.middlewares.FileUploadService;
import com.example.velocitybackend.services.CloudinaryService;
import com.example.velocitybackend.utils.Config;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import java.io.InputStream;
import java.util.Map;

@Path("/cloudinary")
public class CloudinaryResource {
    final FileUploadService fileUploadService = new FileUploadService();
    final CloudinaryService cloudinaryService = new CloudinaryService();

    @POST
    @Path("/uploadImage")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadImage(@FormDataParam("file") InputStream fileInputStream,
                                @FormDataParam("file") FormDataContentDisposition fileMetadata) {
        System.out.println("fileInputStream");

        if (fileInputStream == null || fileMetadata == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Invalid file upload request\"}")
                    .build();
        }

        String filePath = fileUploadService.uploadFile(fileInputStream, fileMetadata);
        return cloudinaryService.uploadImage(filePath);
    }

    @POST
    @Path("/uploadVideo")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadVideo(@FormDataParam("file") InputStream fileInputStream,
                                @FormDataParam("file") FormDataContentDisposition fileMetadata) {
        System.out.println("fileInputStream");

        if (fileInputStream == null || fileMetadata == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Invalid file upload request\"}")
                    .build();
        }

        String filePath = fileUploadService.uploadFile(fileInputStream, fileMetadata);
        return cloudinaryService.uploadVideo(filePath);
    }

//    @DELETE
//    @Path("/deleteItem")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response deleteItem(@QueryParam("id") String id, @QueryParam("resourceType") String resourceType) {
//        try {
//            Map destroyResult = cloudinary.uploader().destroy(id, ObjectUtils.asMap(
//                    "resource_type", resourceType
//            ));
//            return Response.ok(destroyResult).build();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
//        }
//    }
}
