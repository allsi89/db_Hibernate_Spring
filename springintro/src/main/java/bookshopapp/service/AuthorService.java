package bookshopapp.service;

import bookshopapp.domain.entities.Author;

import java.io.IOException;

public interface AuthorService {

    void seedAuthors() throws IOException;

   // Author getAuthor(int id);

    Iterable<Author> getAllAuthors();

    int findIdByFirstAndLastName();

}
