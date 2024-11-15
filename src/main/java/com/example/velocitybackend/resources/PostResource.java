package com.example.velocitybackend.resources;

import com.example.velocitybackend.models.PostModel;
import com.example.velocitybackend.services.PostService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/post")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {
    final PostService postService = new PostService();

    @GET
    public Response getAllPosts() {
        return postService.getAllPosts();
    }

    @GET
    @Path("/getAllNormalPosts")
    public Response getAllNormalPosts() {
        return postService.getAllNormalPosts();
    }

    @GET
    @Path("/getAllRatingPosts/{travelId}")
    public Response getAllRatingPosts(@PathParam("travelId") String travelId) {
        return postService.getAllRatingPosts(travelId);
    }

    @GET
    @Path("/getAllCommentPosts/{postId}")
    public Response getAllCommentPosts(@PathParam("postId") String postId) {
        return postService.getAllCommentPosts(postId);
    }

    @GET
    @Path("/{id}")
    public Response getTravelById(@PathParam("id") String id) {
        return postService.getPostById(id);
    }

    @POST
    public  Response createPost(PostModel post) {
        return postService.createPost(post);
    }

    @PUT
    @Path("/{id}")
    public Response updatePostById(@PathParam("id") String id, PostModel post) {
        return postService.updatePost(id, post);
    }

    @DELETE
    @Path("/{id}")
    public Response deletePostById(@PathParam("id") String id) {
        return postService.deletePost(id);
    }
}
