package ch.bzz.bookshelf.model;

import java.util.List;

public class Publisher {

    private String publisherUUID;
    private String publisher;
    public List<Book> booksList;

    public String getPublisherUUID() {
        return publisherUUID;
    }

    public void setPublisherUUID(String publisherUUID) {
        this.publisherUUID = publisherUUID;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public List<Book> getBooksList() {
        return booksList;
    }

    public void setBooksList(List<Book> booksList) {
        this.booksList = booksList;
    }
}
