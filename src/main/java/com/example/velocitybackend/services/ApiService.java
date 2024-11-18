//package com.example.velocitybackend.services;
//
//import com.example.velocitybackend.utils.MongoDBUtil;
//import com.mongodb.client.MongoCollection;
//import jakarta.ws.rs.core.Response;
//import org.bson.Document;
//
//import java.util.ArrayList;
//import java.util.List;
//
//enum ServiceType {
//    user,
//    travel,
//    post,
//    book
//}
//
//public class ApiService {
//    private MongoCollection<Document> collection;
//    private ServiceType type;
//
//    public ApiService(ServiceType type) {
//        switch (type) {
//            case user:
//                this.collection = MongoDBUtil.getUserCollection();
//                this.type = ServiceType.user;
//                break;
//            case travel:
//                this.collection = MongoDBUtil.getTravelCollection();
//                this.type = ServiceType.travel;
//                break;
//            case post:
//                this.collection = MongoDBUtil.getPostCollection();
//                this.type = ServiceType.post;
//                break;
//            case book:
//                this.collection = MongoDBUtil.getBookCollection();
//                this.type = ServiceType.book;
//                break;
//            default:
//        }
//    }
//
//    public Response getAll() {
//        try {
//            final List<Document> documents = collection.find().into(new ArrayList<>());
//            if (type == ServiceType.user) {
//                return
//            }
//        } catch (Exception e) {
//            return Response.serverError().entity(e.getMessage()).build();
//        }
//    }
//
//    public Response getById(String id) {
//        try {
//
//        } catch (Exception e) {
//            return Response.serverError().entity(e.getMessage()).build();
//        }
//    }
//}
