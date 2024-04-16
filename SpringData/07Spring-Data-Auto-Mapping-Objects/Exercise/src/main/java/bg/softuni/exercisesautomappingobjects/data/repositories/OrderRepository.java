package bg.softuni.exercisesautomappingobjects.data.repositories;

import bg.softuni.exercisesautomappingobjects.data.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
