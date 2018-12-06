package bookshopapp.service;

import bookshopapp.domain.entities.Author;

import java.io.IOException;
import java.util.List;

public interface AuthorService {

    void seedAuthors() throws IOException;

    List<String> getAuthorNamesFirstNameEndingWith(String nameEnd);

    List<String> getAuthorsByBookCopiesCount();


}
