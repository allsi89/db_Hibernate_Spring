package bookshopapp.controller;

import bookshopapp.domain.entities.Author;
import bookshopapp.domain.entities.Book;
import bookshopapp.service.AuthorService;
import bookshopapp.service.BookService;
import bookshopapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

@Controller
public class BookshopController implements CommandLineRunner {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String SEPARATOR_STRING = "---------------------------------------------";
    public static final String INPUT_MSG = "Select a task number from 1 to 4 inclusive:";

    private final BufferedReader reader;
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;

    @Autowired
    public BookshopController(AuthorService authorService, CategoryService categoryService, BookService bookService) {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... strings) throws Exception {
        this.authorService.seedAuthors();
        this.categoryService.seedCategories();
        this.bookService.seedBooks();
        System.out.println(ANSI_RED + INPUT_MSG + ANSI_RESET);

        int input = Integer.parseInt(this.reader.readLine());
        switch (input){
            case 1:
                this.bookTitles();
                break;
            case 2:
                this.authorNames();
                break;
            case 3:
                this.authorsNamesAndBookCount();
                break;
            case 4:
                this.BooksByGeorge();
        }
    }

    private void bookTitles() {
        List<String> bookTitles = this.bookService.getAllBookTitlesAfter();
        System.out.println(ANSI_GREEN+ SEPARATOR_STRING + ANSI_RESET);
        StringBuilder sb = new StringBuilder(String.join("\r\n", bookTitles));
        System.out.println(ANSI_BLUE + sb.toString().trim() + ANSI_RESET);
        System.out.println(ANSI_GREEN+ SEPARATOR_STRING + ANSI_RESET);
    }

    private void authorNames(){
        System.out.println(ANSI_GREEN+ SEPARATOR_STRING + ANSI_RESET);
        this.bookService.getAllAuthorsWithBookBefore().stream()
                .forEach(b-> System.out.println(ANSI_BLUE + b.trim() + ANSI_RESET));
        System.out.println(ANSI_GREEN+ SEPARATOR_STRING + ANSI_RESET);
    }

    private void authorsNamesAndBookCount(){

        Map<String, Integer> authorsBooks = new LinkedHashMap<>();

        Iterable<Author> authors = this.authorService.getAllAuthors();
        for (Author author : authors) {
            authorsBooks
                    .put(String.format("%s %s",
                            author.getFirstName(),
                            author.getLastName()),
                            this.bookService.getBooksCountByAuthor(author.getId()));
        }

        System.out.println(ANSI_GREEN+ SEPARATOR_STRING + ANSI_RESET);

        authorsBooks
                .entrySet()
                .stream()
                .sorted((kvp1, kvp2) -> Integer.compare(kvp2.getValue(), kvp1.getValue()))
                .forEach(kvp -> System.out.println(String.format("%s%s %d%s", ANSI_BLUE,
                        kvp.getKey(), kvp.getValue(), ANSI_RESET)));

        System.out.println(ANSI_GREEN+ SEPARATOR_STRING + ANSI_RESET);

    }

    private void BooksByGeorge(){
        List<Book> books = this
                .bookService
                .getAllBooksByAuthor(this.authorService.findIdByFirstAndLastName());

        StringBuilder sb = new StringBuilder();
        for (Book book : books) {
            sb.append(String.format("%s %s %d", book.getTitle(), book.getReleaseDate(), book.getCopies()))
                    .append(System.lineSeparator());
        }

        System.out.println(ANSI_GREEN+ SEPARATOR_STRING + ANSI_RESET);
        System.out.println((ANSI_BLUE + sb.toString().trim() + ANSI_RESET));
        System.out.println(ANSI_GREEN+ SEPARATOR_STRING + ANSI_RESET);
    }
}
