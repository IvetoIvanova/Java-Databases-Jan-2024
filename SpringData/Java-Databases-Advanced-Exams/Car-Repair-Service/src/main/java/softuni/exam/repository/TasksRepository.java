package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Task;

import java.util.Set;

@Repository
public interface TasksRepository extends JpaRepository<Task, Long> {

    @Query(value = "SELECT t FROM Task  t " +
            "JOIN Car c ON t.car.id = c.id " +
            "WHERE c.carType = 'coupe' " +
            "ORDER BY t.price DESC")
    Set<Task> findAllByCoupeCarTypeOOrderByPriceDesc();
}
