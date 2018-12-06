package productshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import productshop.domain.entities.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u " +
            "FROM productshop.domain.entities.User AS u " +
            "JOIN productshop.domain.entities.Product AS p " +
            "ON u.id = p.seller.id " +
            "WHERE p.buyer IS NOT NULL " +
            "GROUP BY u.id " +
            "ORDER BY COUNT(p.id) DESC ")
    List<User> findAllOrderByProducts_Count();

}
