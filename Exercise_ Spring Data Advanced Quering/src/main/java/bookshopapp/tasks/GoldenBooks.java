package bookshopapp.tasks;

import bookshopapp.service.BookService;

import static bookshopapp.constants.Constants.GOLDEN_BOOK_OUTPUT;

public class GoldenBooks {
    public static String execute(BookService bookService) {
        StringBuilder sb = new StringBuilder();
        bookService.getAllGoldenEditionBooksWithCopiesLessThan()
                .forEach(t-> sb.append(t).append(System.lineSeparator()));
        System.out.println(GOLDEN_BOOK_OUTPUT);
       return sb.toString().trim();
    }
}
