package bookshopapp.tasks;

import bookshopapp.service.BookService;

import java.io.BufferedReader;
import java.io.IOException;

import static bookshopapp.constants.Constants.COPIES_AMOUNT;
import static bookshopapp.constants.Constants.REMOVED_STR;

public class RemoveBooks {
    public static String execute(BufferedReader reader, BookService bookService) throws IOException {
        System.out.println(COPIES_AMOUNT);
        int minCopies = Integer.parseInt(reader.readLine());
        return bookService.removeBooksWithCopiesLessThan(minCopies) + REMOVED_STR;

    }
}
