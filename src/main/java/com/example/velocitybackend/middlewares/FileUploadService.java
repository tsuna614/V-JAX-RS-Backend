package com.example.velocitybackend.middlewares;

import jakarta.servlet.http.Part;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FileUploadService {
    private static final String UPLOAD_DIR = "assets";

//    @POST
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response uploadFile(@FormDataParam("file") InputStream fileInputStream,
//                               @FormDataParam("file") FormDataContentDisposition fileMetadata) {
//        if (fileInputStream == null || fileMetadata == null) {
//            return Response.status(Response.Status.BAD_REQUEST)
//                    .entity("{\"error\":\"Invalid file upload request\"}")
//                    .build();
//        }
//
//        try {
//            // Ensure the upload directory exists
//            File uploadDir = new File(UPLOAD_DIR);
//            if (!uploadDir.exists()) {
//                uploadDir.mkdirs();
//            }
//
//            // Extract file name and set custom name
//            String originalFileName = fileMetadata.getFileName();
//            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
//            String newFileName = "placeholder" + extension;
//
//            // Save the file
//            File file = new File(uploadDir, newFileName);
//            Files.copy(fileInputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
//
//            return Response.ok().entity("{\"message\": \"File uploaded successfully\", \"filePath\": \"" + file.getAbsolutePath() + "\"}").build();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"File upload failed\"}").build();
//        }
//    }

    public String uploadFile(InputStream fileInputStream, FormDataContentDisposition fileMetadata) {
        try {
            // Ensure the upload directory exists
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Extract file name and set custom name
            String originalFileName = fileMetadata.getFileName();
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String newFileName = "placeholder" + extension;

            // Save the file
            File file = new File(uploadDir, newFileName);
            Files.copy(fileInputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);

//            return Response.ok().entity("{\"message\": \"File uploaded successfully\", \"filePath\": \"" + file.getAbsolutePath() + "\"}").build();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
