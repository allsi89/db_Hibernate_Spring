package bookshopapp.tasks;

import bookshopapp.service.BookService;

import static bookshopapp.constants.Constants.BOOKS_BY_PRICE;

public class BooksByPrice {

    public static String execute(BookService bookService) {
        StringBuilder sb = new StringBuilder();
        bookService.getAllBooksWithPriceLowerThanAndPriceHigherThan()
                .forEach(b->sb.append(b).append(System.lineSeparator()));
        System.out.println(BOOKS_BY_PRICE);
        return sb.toString().trim();
    }
}
