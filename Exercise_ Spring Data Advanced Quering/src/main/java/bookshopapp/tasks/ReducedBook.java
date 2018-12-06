package bookshopapp.tasks;

import bookshopapp.service.BookService;

import java.io.BufferedReader;
import java.io.IOException;

import static bookshopapp.constants.Constants.INPUT_BOOK_TITLE;

public class ReducedBook {

    public static String execute(BufferedReader reader, BookService bookService) throws IOException {
        System.out.println(INPUT_BOOK_TITLE);
        String title = reader.readLine();
        return bookService.getBookByTitle(title.trim());
    }
}
