package bookshopapp.tasks;

import bookshopapp.service.BookService;

import java.io.BufferedReader;
import java.io.IOException;

import static bookshopapp.constants.Constants.RELEASE_YEAR_STR;

public class NotReleasedBooks {

    public static String execute(BufferedReader reader, BookService bookService) throws IOException {
        System.out.println(RELEASE_YEAR_STR.toUpperCase());
        int year = Integer.parseInt(reader.readLine());
        StringBuilder sb = new StringBuilder();
        bookService.getAllBooksNotReleasedInYear(year).forEach(t->sb.append(t).append(System.lineSeparator()));
        return sb.toString().trim();
    }
}
