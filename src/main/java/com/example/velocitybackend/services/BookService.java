package com.example.velocitybackend.services;

import com.example.velocitybackend.models.BookModel;
import com.example.velocitybackend.utils.BookUtil;
import com.example.velocitybackend.utils.GeneralUtil;
import com.example.velocitybackend.utils.MongoDBUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import jakarta.ws.rs.core.Response;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class BookService {
    final private MongoCollection<Document> bookCollection = MongoDBUtil.getBookCollection();
    final private UserService userService = new UserService();
    final private TravelService travelService = new TravelService();

    public Response getAllBooks() {
        try {
            final List<BookModel> books = new ArrayList<>();
            for (Document document : bookCollection.find()) {
                books.add(BookUtil.fromDocument(document));
            }
            return Response.ok(books).build();
        } catch (Exception error) {
            return Response.status(500).entity(error.getMessage()).build();
        }
    }

    public Response getBookById(String id) {
        try {
            final Document response = bookCollection.find(Filters.eq("_id", new ObjectId(id))).first();
            if (response == null) {
                return Response.status(404).entity("No book with the id " + id + " was found.").build();
            }
            return Response.ok(BookUtil.fromDocument(response)).build();
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

    public Response getAllBooksByUserId(String id) {
        try {
            final List<BookModel> books = new ArrayList<>();
            for (Document document : bookCollection.find(
                    Filters.eq("userId", id)
            )) {
                books.add(BookUtil.fromDocument(document));
            }
            return Response.ok(books).build();
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

    public Response createBook(BookModel book) {
        try {
            String validateMessage = BookUtil.validateBook(book);
            if (validateMessage != null) {
                return Response.status(500).entity(validateMessage).build();
            }
            Document doc = BookUtil.toDocument(book);
            bookCollection.insertOne(doc);
            book.setId(doc.getObjectId("_id").toString());

            Double travelPrice = travelService.getTravelPriceByTravelId(book.getTravelId());
            userService.increaseUserProgression(book.getUserId(), travelPrice * book.getAmount());

            return Response.ok(book).build();
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

    public Response updateBook(String id, BookModel book) {
        try {
            Document reqBody = BookUtil.filterBook(book);

            bookCollection.updateOne(Filters.eq("_id", new ObjectId(id)), reqBody);
            return Response.ok(GeneralUtil.getMessage("Book updated successfully")).build();
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

    public Response deleteBook(String id) {
        try {
            Document response = bookCollection.findOneAndDelete(Filters.eq("_id", new ObjectId(id)));

            if (response == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(GeneralUtil.getError("Book not found")).build();
            }
            return Response.ok(GeneralUtil.getMessage("Book deleted successfully")).build();
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }
}
