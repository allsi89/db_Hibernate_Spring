package mostwanted.repository;

import mostwanted.domain.entities.Racer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RacerRepository extends JpaRepository<Racer, Integer> {
    // TODO : Export Racers with their racing cars

    Optional<Racer> findByName(String name);

    /*
    Export all racers which have any cars:
•	Export the racer’s name, age (but ONLY if it is NOT NULL), list of cars.
o	In case the racer’s age property is NULL, do NOT include it.
•	The cars should be strings in the following format: “{brand} {model} {yearOfProduction}”.
•	Order them descending, by count of cars they have, and then by racer name alphabetically.


     */

    @Query("" +
            "SELECT r FROM mostwanted.domain.entities.Racer r " +
            "JOIN r.cars " +
            "WHERE size(r.cars) > 0 " +
            "GROUP BY r " +
            "ORDER BY size(r.cars) DESC, r.name")
    List<Racer> extractRacersWithRacingCars();
}
