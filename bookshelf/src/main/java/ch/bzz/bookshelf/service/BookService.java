package ch.bzz.bookshelf.service;

import ch.bzz.bookshelf.data.DataHandler;
import ch.bzz.bookshelf.model.Book;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Path("book")
public class BookService {
    @Path("list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listBooks(){
        List<Book> bookList = DataHandler.readAllBooks();
        return Response
                .status(200)
                .entity(bookList)
                .build();
    }

    @Path("read")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response readBook(
            @QueryParam("uuid") String bookUUID)
    {
        Book book = DataHandler.readBookByUUID(bookUUID);
        return Response
                .status(200)
                .entity(book)
                .build();
    }

    /**
     *  creates a new book
     * @param title title of the book
     * @param author author of the book
     * @param publisherUUID uuid of the publisher
     * @param isbn isbn of the book
     * @param price price of the book
     * @return Response
     */
    @Path("create")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertBook(
            @FormParam("title") String title,
            @FormParam("author") String author,
            @FormParam("publisherUUID") String publisherUUID,
            @FormParam("price") BigDecimal price,
            @FormParam("isbn") String isbn
    ) {
        Book book = new Book();
        book.setBookUUID(UUID.randomUUID().toString());
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublisherUUID(publisherUUID);
        book.setPrice(price);
        book.setIsbn(isbn);

        DataHandler.insertBook(book);

        int httpStatus = 200;
        return Response
                .status(httpStatus)
                .entity("Book erfolgreich angelegt")
                .build();
    }

    @Path("delete")
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteBook(
            @QueryParam("uuid") String bookUUID
    ){
        int httpStatus = 200;
        if (DataHandler.deleteBook(bookUUID)) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("Book erfolgreich gelöscht")
                .build();
    }

    @Path("update")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateBook(
            @FormParam("uuid") String bookUUID,
            @FormParam("title") String title,
            @FormParam("author") String author,
            @FormParam("publisherUUID") String publisherUUID,
            @FormParam("price") BigDecimal price,
            @FormParam("isbn") String isbn
    ){
        int httpStatus = 200;
        Book book = DataHandler.readBookByUUID(bookUUID);
        if (book != null) {
            book.setTitle(title);
            book.setAuthor(author);
            book.setPublisherUUID(publisherUUID);
            book.setPrice(price);
            book.setIsbn(isbn);

            DataHandler.updateBook();
        }else{
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("Book erfolgreich aktualisiert")
                .build();
    }

}
