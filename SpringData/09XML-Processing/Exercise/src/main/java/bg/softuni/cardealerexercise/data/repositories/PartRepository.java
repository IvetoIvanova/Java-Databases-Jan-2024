package bg.softuni.cardealerexercise.data.repositories;

import bg.softuni.cardealerexercise.data.entities.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
}
