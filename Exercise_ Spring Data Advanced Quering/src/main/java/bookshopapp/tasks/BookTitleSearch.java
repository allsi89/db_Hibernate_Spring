package bookshopapp.tasks;

import bookshopapp.service.BookService;

import java.io.BufferedReader;
import java.io.IOException;

import static bookshopapp.constants.Constants.BOOKS_BY_AUTHORS;
import static bookshopapp.constants.Constants.SEARCH_STR;

public class BookTitleSearch {
    public static String execute(BufferedReader reader, BookService bookService) throws IOException {
        System.out.println(SEARCH_STR);
        String searchStr = reader.readLine();
        StringBuilder sb = new StringBuilder();
        bookService.getAllBooksByAuthorLastNameStartsWith(searchStr)
                .forEach(t->sb.append(t).append(System.lineSeparator()));
        System.out.println(BOOKS_BY_AUTHORS);
        return sb.toString().trim();
    }
}
