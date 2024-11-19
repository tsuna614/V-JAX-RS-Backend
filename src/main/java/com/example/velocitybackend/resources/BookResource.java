package com.example.velocitybackend.resources;

import com.example.velocitybackend.models.BookModel;
import com.example.velocitybackend.services.BookService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/book")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {
    final BookService bookService = new BookService();

    @GET
    public Response getAllBooks() {
        return bookService.getAllBooks();
    }

    @GET
    @Path("/{id}")
    public Response getBookById(@PathParam("id") String id) {
        return bookService.getBookById(id);
    }

    @POST
    public  Response createBook(BookModel book) {
        return bookService.createBook(book);
    }

    @PUT
    @Path("/{id}")
    public Response updateBookById(@PathParam("id") String id, BookModel book) {
        return bookService.updateBook(id, book);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBookById(@PathParam("id") String id) {
        return bookService.deleteBook(id);
    }
}
