package usersystemapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import usersystemapp.models.Town;

@Repository
public interface TownRepository extends JpaRepository<Town, Long> {
}
