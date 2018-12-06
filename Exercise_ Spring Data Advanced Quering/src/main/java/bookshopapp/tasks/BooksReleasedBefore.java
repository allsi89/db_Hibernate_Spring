package bookshopapp.tasks;

import bookshopapp.service.BookService;

import java.io.BufferedReader;
import java.io.IOException;

import static bookshopapp.constants.Constants.RELEASE_DATE_STR;

public class BooksReleasedBefore {

    public static String execute(BufferedReader reader, BookService bookService) throws IOException {
        System.out.println(RELEASE_DATE_STR);
        String date = reader.readLine();
        StringBuilder sb = new StringBuilder();
        bookService.getAllBooksReleasedBefore(date)
                .forEach(b->sb.append(b).append(System.lineSeparator()));
        return sb.toString().trim();
    }
}
