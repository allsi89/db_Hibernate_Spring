package bookshopapp.tasks;

import bookshopapp.service.BookService;

import java.io.BufferedReader;
import java.io.IOException;

import static bookshopapp.constants.Constants.*;

public class CountBooks {
    public static void execute(BufferedReader reader, BookService bookService) throws IOException {

        System.out.println(SELECT_LENGTH_OF_TITLE);
        int length = Integer.parseInt(reader.readLine());
        System.out.println(COUNT_OF_BOOKS + bookService.getCountOfBooksWithTitleLongerThan(length) + ANSI_RESET);
    }
}
