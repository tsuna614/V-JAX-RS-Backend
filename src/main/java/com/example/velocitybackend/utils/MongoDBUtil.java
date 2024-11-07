package com.example.velocitybackend.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

public class MongoDBUtil {
    private static final String URI = "mongodb+srv://thedarkspiritaway:woHGvPrRvfUQCNBT@cluster0.hdmia.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
//    private static final String URI = "mongodb+srv://new-user:quockhanh300803@cluster0.4stwd.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
    private static final String DB_NAME = "test";
    private static final String COLLECTION_NAME = "users";
    private static final MongoClient mongoClient = MongoClients.create(URI);

    public static MongoCollection<Document> getUserCollection() {
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        return database.getCollection(COLLECTION_NAME);
    }
}
