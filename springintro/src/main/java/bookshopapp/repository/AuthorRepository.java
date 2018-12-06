package bookshopapp.repository;

import bookshopapp.domain.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Author findById(int id);

    Author findByFirstNameAndLastName(String firstName, String lastName);

}
