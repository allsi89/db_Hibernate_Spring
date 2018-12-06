package bookshopapp.service;

import bookshopapp.domain.entities.Book;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface BookService {
    void seedBooks() throws IOException;



    List<String> getAllBookTitlesAfter();

    Set<String> getAllAuthorsWithBookBefore();

    int getBooksCountByAuthor(int id);

    List<Book> getAllBooksByAuthor(int id);

}
