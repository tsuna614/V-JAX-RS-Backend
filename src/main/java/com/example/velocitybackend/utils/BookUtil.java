package com.example.velocitybackend.utils;

import com.example.velocitybackend.models.BookModel;
import org.bson.Document;

import java.time.LocalDateTime;

public class BookUtil {
    public static BookModel fromDocument(Document doc) {
        BookModel book = new BookModel();
        book.setId(doc.getObjectId("_id").toString());
        book.setTravelId(doc.getString("travelId"));
        book.setUserId(doc.getString("userId"));
        book.setBookedDate(doc.getString("bookedDate"));
        book.setAmount(doc.getInteger("amount"));
        book.setCreatedAt(doc.getString("createdAt"));
        return book;
    }

    public static Document toDocument(BookModel book) {
        Document doc = new Document();
        doc.append("travelId", book.getTravelId());
        doc.append("userId", book.getUserId());
        doc.append("amount", book.getAmount());
//        doc.append("bookedDate", book.getBookedDate());
//        doc.append("createdAt", LocalDateTime.now().toString());
        doc.append("bookedDate", book.getBookedDate() == null ? LocalDateTime.now().toString() : book.getBookedDate());
        doc.append("createdAt", book.getCreatedAt() == null ? LocalDateTime.now().toString() : book.getCreatedAt());
        return doc;
    }

    public static String validateBook(BookModel book) {
        if (book.getTravelId() == null || book.getTravelId().isEmpty()) {
            return "Travel id cannot be empty";
        }
        if (book.getUserId() == null || book.getUserId().isEmpty()) {
            return "User id cannot be empty";
        }
        return null;
    }

    public static Document filterBook(BookModel book) {
        Document doc = new Document();
        if (book.getTravelId() != null) {
            doc.append("travelId", book.getTravelId());
        }
        if (book.getUserId() != null) {
            doc.append("userId", book.getUserId());
        }
        if (book.getBookedDate() != null) {
            doc.append("bookedDate", book.getBookedDate());
        }
        if (book.getCreatedAt() != null) {
            doc.append("createdAt", book.getCreatedAt());
        }
        return doc;
    }
}
