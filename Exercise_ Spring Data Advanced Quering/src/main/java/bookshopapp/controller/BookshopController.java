package bookshopapp.controller;

import bookshopapp.tasks.*;
import bookshopapp.service.AuthorService;
import bookshopapp.service.BookService;
import bookshopapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;

import static bookshopapp.constants.Constants.*;

@Controller
public class BookshopController implements CommandLineRunner {

    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;
    private final BufferedReader reader;

    @Autowired
    public BookshopController(AuthorService authorService, CategoryService categoryService, BookService bookService, BufferedReader reader) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
        this.reader = reader;
    }

    @Override
    public void run(String... strings) throws Exception {
        this.authorService.seedAuthors();
        this.categoryService.seedCategories();
        this.bookService.seedBooks();
        System.out.println(ANSI_RED + INPUT_MSG.toUpperCase() + ANSI_RESET);

        int input = Integer.parseInt(reader.readLine());
        switch (input) {
            case 1: //DONE
                print(BookTitlesByAge.execute(reader, bookService));
                break;
            case 2: //DONE
                print(GoldenBooks.execute(bookService));
                break;
            case 3: //DONE
                print(BooksByPrice.execute(bookService));
                break;
            case 4: //DONE
                print(NotReleasedBooks.execute(reader, bookService));
                break;
            case 5: //DONE
                print(BooksReleasedBefore.execute(reader, bookService));
                break;
            case 6: //DONE
                print(AuthorSearch.execute(reader, authorService));
                break;
            case 7: //DONE
                print(BookSearch.execute(reader, bookService));
                break;
            case 8: //DONE
                print(BookTitleSearch.execute(reader, bookService));
                break;
            case 9://DONE
                CountBooks.execute(reader, bookService);
                break;
            case 10: //DONE
                print(TotalBookCopies.execute(authorService));
                break;
            case 11: //DONE
                print(ReducedBook.execute(reader, bookService));
                break;
            case 12: //DONE
                print(IncreaseBookCopies.execute(reader, bookService));
                break;
            case 13: //DONE
                print(RemoveBooks.execute(reader, bookService));
                break;


        }
    }

    private void print(String toPrint){
        System.out.println(ANSI_GREEN + SEPARATOR_STRING + ANSI_RESET);
        System.out.println(ANSI_BLUE + toPrint + ANSI_RESET);
        System.out.println(ANSI_GREEN + SEPARATOR_STRING + ANSI_RESET);
    }

}
