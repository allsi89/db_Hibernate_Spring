package cardealer.repository;

import cardealer.domain.dtos.views.localsuppliers.SupplierViewDto;
import cardealer.domain.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {


    @Query("SELECT count(s) FROM cardealer.domain.entities.Supplier AS s " +
            "JOIN cardealer.domain.entities.Part p on s.id = p.supplier.id " +
            "WHERE s.id = :id")
    Long getCountOfParts(@Param(value = "id") int id);

}
