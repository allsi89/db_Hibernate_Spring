package usersystemapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import usersystemapp.models.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
}
