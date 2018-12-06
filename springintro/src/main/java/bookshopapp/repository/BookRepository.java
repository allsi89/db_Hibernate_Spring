package bookshopapp.repository;

import bookshopapp.domain.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findAllByReleaseDateAfter(LocalDate date);

    List<Book> findAllByReleaseDateBefore(LocalDate date);

    List<Book> findAllByAuthor_Id(int id);

    List<Book> findAllByAuthor_IdOrderByReleaseDateDescTitleAsc(int id);

    //List<Book> getDistinctByAuthor();

}
