package bookshopapp.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<String> getAllBookTitlesByAgeRestriction(String ageRestriction);

    List<String> getAllGoldenEditionBooksWithCopiesLessThan();

    List<String> getAllBooksWithPriceLowerThanAndPriceHigherThan();

    List<String> getAllBooksNotReleasedInYear(int year);

    List<String> getAllBooksReleasedBefore(String date);

    List<String> getAllBooksContainingSearchString(String searchStr);

    List<String> getAllBooksByAuthorLastNameStartsWith(String startsWith);

    int getCountOfBooksWithTitleLongerThan(int length);

    int getCountOfCopiesByAuthor(String author);

    String getBookByTitle(String title);

    Integer increaseCopiesForBooksReleasedAfterDate(LocalDate startDate, Integer copiesToAdd);

    Integer removeBooksWithCopiesLessThan(Integer minCopies);

}
