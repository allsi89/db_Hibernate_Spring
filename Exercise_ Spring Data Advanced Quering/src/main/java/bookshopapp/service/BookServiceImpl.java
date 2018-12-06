package bookshopapp.service;

import bookshopapp.domain.entities.Author;
import bookshopapp.domain.entities.Book;
import bookshopapp.domain.entities.Category;
import bookshopapp.domain.enums.AgeRestriction;
import bookshopapp.domain.enums.EditionType;
import bookshopapp.repository.AuthorRepository;
import bookshopapp.repository.BookRepository;
import bookshopapp.repository.CategoryRepository;
import bookshopapp.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static bookshopapp.constants.Constants.FIVE_THOUSAND;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    private final static String BOOKS_FILE_PATH =
            "D:\\javaPro\\springintro\\src\\main\\resources\\files\\books.txt";

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository, FileUtil fileUtil) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedBooks() throws IOException {
        if (this.bookRepository.count() != 0) {
            return;
        }

        String[] bookFileContent = this.fileUtil.getFileContent(BOOKS_FILE_PATH);

        for (String line : bookFileContent) {
            String[] lineParams = line.split("\\s+");

            Book book = new Book();

            book.setAuthor(this.getRandomAuthor());

            EditionType editionType = EditionType.values()[Integer.parseInt(lineParams[0])];
            book.setEditionType(editionType);

            LocalDate releaseDate = LocalDate.parse(lineParams[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
            book.setReleaseDate(releaseDate);

            int copies = Integer.parseInt(lineParams[2]);
            book.setCopies(copies);

            BigDecimal price = new BigDecimal(lineParams[3]);
            book.setPrice(price);

            AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(lineParams[4])];
            book.setAgeRestriction(ageRestriction);

            StringBuilder title = new StringBuilder();
            for (int i = 5; i < lineParams.length; i++) {
                title.append(lineParams[i]).append(" ");
            }
            book.setTitle(title.toString().trim());

            Set<Category> categories = this.getRandomCategories();
            book.setCategories(categories);

            this.bookRepository.saveAndFlush(book);
        }
    }

    @Override
    public List<String> getAllBookTitlesByAgeRestriction(String ageRestriction) {
        return this.bookRepository
                .findAllByAgeRestriction(AgeRestriction.valueOf(ageRestriction.toUpperCase()))
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllGoldenEditionBooksWithCopiesLessThan() {
        return this.bookRepository.findAllByEditionTypeAndCopiesIsLessThan(EditionType.GOLD, FIVE_THOUSAND)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllBooksWithPriceLowerThanAndPriceHigherThan() {
        return this.bookRepository
                .findAllByPriceIsLessThanOrPriceIsGreaterThan(BigDecimal.valueOf(5), BigDecimal.valueOf(40))
                .stream()
                .map(book -> String.format("%s - $%.2f", book.getTitle(), book.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllBooksNotReleasedInYear(int year) {
        LocalDate before = LocalDate.of(year, 1, 1);
        LocalDate after = LocalDate.of(year, 12, 31);
        return this.bookRepository.findAllByReleaseDateBeforeOrReleaseDateAfterOrderById(before, after)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllBooksReleasedBefore(String date) {
        LocalDate before = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return this.bookRepository.findAllByReleaseDateBefore(before)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllBooksContainingSearchString(String searchStr) {
        return this.bookRepository.findAllByTitleContains(searchStr)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllBooksByAuthorLastNameStartsWith(String startsWith) {
        return this.bookRepository.findAllByAuthorLastNameStartsWith(startsWith)
                .stream()
                .map(b -> String.format("%s (%s %s)", b.getTitle(), b.getAuthor().getFirstName(), b.getAuthor().getLastName()))
                .collect(Collectors.toList());
    }

    @Override
    public int getCountOfBooksWithTitleLongerThan(int length) {
        return this.bookRepository.findCountOfBooksWithTitleLongerThan(length).size();
    }

    @Override
    public int getCountOfCopiesByAuthor(String author) {
        String[] names = author.split("\\s+");
        List<Book> books = this.bookRepository.findAllByAuthorFirstNameAndAuthorLastName(names[0], names[1]);
        int count = 0;
        for (Book book : books) {
            count += book.getCopies();
        }
        return count;
    }

    @Override
    public String getBookByTitle(String title) {
        Book book = this.bookRepository.findBookByTitle(title);

        return String.format("%s %s %s %.2f",
                book.getTitle(),
                book.getEditionType(),
                book.getAgeRestriction(),
                book.getPrice());
    }

    @Override
    public Integer increaseCopiesForBooksReleasedAfterDate(LocalDate startDate, Integer copiesToAdd) {
        return this.bookRepository.increaseCopiesForBooksReleasedAfterDate(startDate, copiesToAdd);
    }

    @Override
    public Integer removeBooksWithCopiesLessThan(Integer minCopies) {
        return this.bookRepository.removeBooksWithCopiesLessThan(minCopies);
    }


    private Author getRandomAuthor() {
        Random random = new Random();
        int randomId = random.nextInt((int) this.authorRepository.count() - 1) + 1;
        return this.authorRepository.findById(randomId);
    }

    private Category getRandomCategory() {
        Random random = new Random();
        int randomId = random.nextInt((int) this.categoryRepository.count() - 1) + 1;
        return this.categoryRepository.getOne(randomId);
    }

    private Set<Category> getRandomCategories() {
        Set<Category> categories = new HashSet<>();
        Random random = new Random();
        int length = random.nextInt(4) + 1;
        for (int i = 0; i <= length; i++) {
            Category category = this.getRandomCategory();
            if (category != null) {
                categories.add(category);
            }
        }
        return categories;
    }
}
