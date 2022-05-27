package ch.bzz.bookshelf.data;

import ch.bzz.bookshelf.model.Book;
import ch.bzz.bookshelf.model.Publisher;
import ch.bzz.bookshelf.service.Config;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.inject.Singleton;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * reads and writes the data in the JSON-files
 */
@Singleton
public class DataHandler {

    private static List<Book> bookList;
    private static List<Publisher> publisherList;

    /**
     * private constructor defeats instantiation
     */
    private DataHandler() {

    }

    /**
     * reads the JSON-file with the book-data
     */
    public static void insertBook(Book book) {
        getBookList().add(book);
        writeBookJSON();
    }

    /**
     * deletes a book identified by the bookUUID
     * @param bookUUID  the key
     * @return  success=true/false
     */
    public static boolean deleteBook(String bookUUID) {
        Book book = readBookByUUID(bookUUID);
        if (book != null) {
            getBookList().remove(book);
            writeBookJSON();
            return true;
        } else {
            return false;
        }
    }

    /**
     * reads the JSON-file with the book-data
     */
    private static void writeBookJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String bookPath = Config.getProperty("bookJSON");
        try {
            fileOutputStream = new FileOutputStream(bookPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getBookList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * reads all books
     * @return list of books
     */
    public static List<Book> readAllBooks() {
        return getBookList();
    }

    /**
     * reads a book by its uuid
     * @param bookUUID
     * @return the Book (null=not found)
     */
    public static Book readBookByUUID(String bookUUID) {
        Book book = null;
        for (Book entry : getBookList()) {
            if (entry.getBookUUID().equals(bookUUID)) {
                book = entry;
            }
        }
        return book;
    }

    /**
     * reads all Publishers
     * @return list of publishers
     */
    public static List<Publisher> readAllPublishers() {
        return getPublisherList();
    }

    /**
     * reads a publisher by its uuid
     * @param publisherUUID
     * @return the Publisher (null=not found)
     */
    public static Publisher readPublisherByUUID(String publisherUUID) {
        Publisher publisher = null;
        for (Publisher entry : getPublisherList()) {
            if (entry.getPublisherUUID().equals(publisherUUID)) {
                publisher = entry;
            }
        }
        return publisher;
    }

    /**
     * reads the books from the JSON-file
     */
    private static void readBookJSON() {
        try {
            String path = Config.getProperty("bookJSON");
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(path)
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Book[] books = objectMapper.readValue(jsonData, Book[].class);
            for (Book book : books) {
                getBookList().add(book);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads the publishers from the JSON-file
     */
    private static void readPublisherJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(
                            Config.getProperty("publisherJSON")
                    )
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Publisher[] publishers = objectMapper.readValue(jsonData, Publisher[].class);
            for (Publisher publisher : publishers) {
                getPublisherList().add(publisher);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * gets bookList
     *
     * @return value of bookList
     */
    private static List<Book> getBookList() {
        if (bookList == null) {
            bookList = new ArrayList<>();
            readBookJSON();
        }
        return bookList;
    }

    /**
     * sets bookList
     *
     * @param bookList the value to set
     */
    private void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    /**
     * gets publisherList
     *
     * @return value of publisherList
     */
    private static List<Publisher> getPublisherList() {
        if (publisherList == null) { // lazy initialization
            publisherList = new ArrayList<>();
            readPublisherJSON();
        }

        return publisherList;
    }

    /**
     * sets publisherList
     *
     * @param publisherList the value to set
     */
    private void setPublisherList(List<Publisher> publisherList) {
        this.publisherList = publisherList;
    }


}