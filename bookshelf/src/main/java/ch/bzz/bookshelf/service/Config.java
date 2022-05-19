package ch.bzz.bookshelf.service;

import ch.bzz.bookshelf.model.Book;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import static java.lang.System.setProperties;

// Annotation -> erster Teil der URL
@ApplicationPath("/resource")

public class Config extends Application {
    private static final String PROPERTIES_PATH = "C:\\github\\modul133-nadina-bookshelf\\bookshelf\\src\\main\\resources\\bookList.properties";
    private static Properties properties = null;

    public static String getProperty(String property) {
        if (Config.properties == null) {
            setProperties(new Properties());
            readProperties();
        }
        String value = Config.properties.getProperty(property);
        if (value == null) return "";
        return value;
    }

    private static void readProperties() {

        InputStream inputStream;
        try {
            inputStream = new FileInputStream(PROPERTIES_PATH);
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }

    private static void setProperties(Properties properties) {
        Config.properties = properties;
    }
}
