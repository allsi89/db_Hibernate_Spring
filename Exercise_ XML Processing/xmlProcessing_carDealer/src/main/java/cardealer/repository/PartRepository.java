package cardealer.repository;

import cardealer.domain.dtos.views.carswithpartslist.PartViewDto;
import cardealer.domain.entities.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PartRepository extends JpaRepository<Part, Integer> {

    Part findById(int id);

//    @Query("SELECT c.parts FROM cardealer.domain.entities.Car AS c " +
//            "JOIN cardealer.domain.entities.Part AS p " +
//            "ON c.id = p.car.id " +
//            "WHERE c.id = :id")
//    List<Part> findPartsByCar_Id(@Param(value = "id") int id);
}
