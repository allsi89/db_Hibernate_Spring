package bookshopapp.repository;

import bookshopapp.domain.entities.Book;
import bookshopapp.domain.enums.AgeRestriction;
import bookshopapp.domain.enums.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

   List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

   List<Book> findAllByEditionTypeAndCopiesIsLessThan(EditionType editionType, int copies);

   List<Book> findAllByPriceIsLessThanOrPriceIsGreaterThan(BigDecimal lowerBound, BigDecimal upperBound);

   List<Book> findAllByReleaseDateBeforeOrReleaseDateAfterOrderById(LocalDate before, LocalDate after);

   List<Book> findAllByReleaseDateBefore(LocalDate before);

   List<Book> findAllByTitleContains(String searchStr);

   List<Book> findAllByAuthorLastNameStartsWith(String start);

   @Query(value = "SELECT b FROM bookshopapp.domain.entities.Book AS b WHERE length(b.title) >= :length ")
   List<Book> findCountOfBooksWithTitleLongerThan(@Param(value = "length") final int length);

   List<Book> findAllByAuthorFirstNameAndAuthorLastName(String first, String last);

   Book findBookByTitle(String title);

   @Modifying
   @Query("UPDATE bookshopapp.domain.entities.Book AS b SET b.copies = b.copies + :cnt WHERE b.releaseDate > :date")
   Integer increaseCopiesForBooksReleasedAfterDate(@Param("date") LocalDate startDate, @Param("cnt") int copiesToAdd);

   @Modifying
   @Query("DELETE FROM bookshopapp.domain.entities.Book AS b WHERE b.copies < :copies")
   Integer removeBooksWithCopiesLessThan(@Param("copies") int minCopies);


}
