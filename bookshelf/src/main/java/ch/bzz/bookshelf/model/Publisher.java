package ch.bzz.bookshelf.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Publisher {
    @JsonIgnore
    private List<Book> bookList;

    private String publisherUUID;
    private String publisher;

    public Publisher(){
        setBookList(new ArrayList<>());
    }
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
