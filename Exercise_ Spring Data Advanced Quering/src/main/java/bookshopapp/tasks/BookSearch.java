package bookshopapp.tasks;

import bookshopapp.service.BookService;

import java.io.BufferedReader;
import java.io.IOException;

import static bookshopapp.constants.Constants.BOOK_TITLE_CONTAINS;
import static bookshopapp.constants.Constants.SEARCH_STR;

public class BookSearch {
    public static String execute(BufferedReader reader, BookService bookService) throws IOException {
        System.out.println(SEARCH_STR);
        String searchStr = reader.readLine();
        StringBuilder sb = new StringBuilder();
        bookService.getAllBooksContainingSearchString(searchStr)
                .forEach(n->sb.append(n).append(System.lineSeparator()));
        System.out.println(BOOK_TITLE_CONTAINS);
        return sb.toString().trim();
    }
}
