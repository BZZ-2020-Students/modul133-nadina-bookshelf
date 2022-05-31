package ch.bzz.bookshelf.model;

import ch.bzz.bookshelf.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @JsonIgnore
    private Publisher publisher;

    private String bookUUID;
    private String title;
    private String author;
    private BigDecimal price;
    private String isbn;

    public String getPublisherUUID() {
        if (getPublisher() == null) return null;
        return getPublisher().getPublisherUUID();
    }

    public void setPublisherUUID(String publisherUUID) {
        setPublisher(new Publisher());
        Publisher publisher = DataHandler.readPublisherByUUID(publisherUUID);
        getPublisher().setPublisherUUID(publisherUUID);
        getPublisher().setPublisher(publisher.getPublisher());
        //return null;
    }


}
