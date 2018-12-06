package bookshopapp.tasks;

import bookshopapp.service.BookService;

import java.io.BufferedReader;
import java.io.IOException;

import static bookshopapp.constants.Constants.*;

public class BookTitlesByAge {

    public static String execute(BufferedReader reader, BookService bookService) throws IOException {
        System.out.println(ANSI_RED + SELECT_AGE + ANSI_RESET);
        String ageRestriction = reader.readLine();
        StringBuilder sb = new StringBuilder();
        bookService.getAllBookTitlesByAgeRestriction(ageRestriction.trim())
        .forEach(t->sb.append(t).append(System.lineSeparator()));
        return sb.toString().trim();

    }
}
