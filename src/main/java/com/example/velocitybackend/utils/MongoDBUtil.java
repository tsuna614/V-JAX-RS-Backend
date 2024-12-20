package com.example.velocitybackend.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDBUtil {
    private static final String URI = Config.get("MONGODB_URI");
    private static final String DB_NAME = "test";
//    private static final String COLLECTION_NAME = "users";
    private static final MongoClient mongoClient = MongoClients.create(URI);

    public static MongoCollection<Document> getUserCollection() {
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        return database.getCollection("users");
    }

    public static MongoCollection<Document> getTravelCollection() {
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        return database.getCollection("travels");
    }

    public static MongoCollection<Document> getPostCollection() {
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        return database.getCollection("posts");
    }

    public static MongoCollection<Document> getBookCollection() {
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        return database.getCollection("books");
    }

    public static MongoCollection<Document> getMessageCollection() {
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        return database.getCollection("messages");
    }

    public static MongoCollection<Document> getNotificationCollection() {
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        return database.getCollection("notifications");
    }
}
