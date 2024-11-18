package com.example.velocitybackend.services;

import com.example.velocitybackend.models.PostModel;
import com.example.velocitybackend.utils.GeneralUtil;
import com.example.velocitybackend.utils.MongoDBUtil;
import com.example.velocitybackend.utils.PostUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import jakarta.ws.rs.core.Response;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class PostService {
    final private MongoCollection<Document> collection = MongoDBUtil.getPostCollection();

    public Response getAllPosts() {
        try {
            List<PostModel> posts = new ArrayList<>();
            for (Document post : collection.find()) {
                posts.add(PostUtil.fromDocument(post));
            }
            return Response.ok(posts).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    public Response getPostById(String id) {
        try {
            Document doc = collection.find(Filters.eq("_id", new ObjectId(id))).first();

            if (doc == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(PostUtil.fromDocument(doc)).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    public Response getAllNormalPosts() {
        try {
            List<PostModel> normalPosts = new ArrayList<>();
            for (Document post : collection.find(
                    Filters.and(
                            Filters.eq("rating", null),
                            Filters.eq("postId", null)
                    )
            )) {
                normalPosts.add(PostUtil.fromDocument(post));
            }
            return Response.ok(normalPosts).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    public Response getAllRatingPosts(String travelId) {
        try {
            List<PostModel> ratingPosts = new ArrayList<>();
            for (Document post : collection.find(
                    Filters.and(
                            Filters.exists("rating", true),
                            Filters.eq("travelId", travelId)
                    )
            )) {
                ratingPosts.add(PostUtil.fromDocument(post));
            }
            return Response.ok(ratingPosts).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    public Response getAllCommentPosts(String postId) {
        try {
            List<PostModel> commentPosts = new ArrayList<>();
            for (Document post : collection.find(Filters.eq("postId", postId))) {
                commentPosts.add(PostUtil.fromDocument(post));
            }
            return Response.ok(commentPosts).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    public Response createPost(PostModel post) {
        try {
            String validateMessage = PostUtil.validatePost(post);
            if (validateMessage != null) {
                return Response.serverError().entity(validateMessage).build();
            }
            Document doc = PostUtil.toDocument(post);
            collection.insertOne(doc);
            post.setId(doc.getObjectId("_id").toString());
            return Response.ok(post).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    public Response updatePost(String id, PostModel post) {
        try {
            Document reqBody = PostUtil.toDocument(post);

            collection.updateOne(Filters.eq("_id", new ObjectId(id)), reqBody);
            return Response.ok(GeneralUtil.getMessage("User updated successfully")).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    public Response deletePost(String id) {
        try {
            Document response = collection.findOneAndDelete(Filters.eq("_id", new ObjectId(id)));

            if (response == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(GeneralUtil.getError("Post not found")).build();
            }
            return Response.ok(GeneralUtil.getMessage("Post deleted successfully")).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
