package bookshopapp.repository;

import bookshopapp.domain.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Author findById(int id);

   // Author findByFirstNameAndLastName(String firstName, String lastName);

    List<Author> findAllByFirstNameEndingWith(String nameEnd);


    @Query("SELECT CONCAT(a.firstName, ' ', a.lastName, ' - ', SUM(b.copies)) " +
            "FROM bookshopapp.domain.entities.Author AS a " +
            "JOIN bookshopapp.domain.entities.Book AS b " +
            "ON b.author = a " +
            "GROUP BY a.id " +
            "ORDER BY SUM(b.copies) DESC ")
    List<Object> getAuthorsByBooksCopies();


}
