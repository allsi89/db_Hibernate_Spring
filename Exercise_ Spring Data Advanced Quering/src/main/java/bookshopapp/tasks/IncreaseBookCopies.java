package bookshopapp.tasks;

import bookshopapp.service.BookService;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static bookshopapp.constants.Constants.COPIES_AMOUNT;
import static bookshopapp.constants.Constants.RELEASE_DATE_AFTER;
import static bookshopapp.constants.Constants.TOTAL_BOOK_COPIES;


public class IncreaseBookCopies {
    public static String execute(BufferedReader reader, BookService bookService) throws IOException {
        System.out.println(RELEASE_DATE_AFTER);
        LocalDate date = LocalDate.parse(reader.readLine(), DateTimeFormatter.ofPattern("dd MMM yyyy"));
        System.out.println(COPIES_AMOUNT);
        int copies = Integer.parseInt(reader.readLine());
        System.out.println(TOTAL_BOOK_COPIES);
        int booksCopies = bookService.increaseCopiesForBooksReleasedAfterDate(date, copies) * copies;
        return String.valueOf(booksCopies);
    }
}
