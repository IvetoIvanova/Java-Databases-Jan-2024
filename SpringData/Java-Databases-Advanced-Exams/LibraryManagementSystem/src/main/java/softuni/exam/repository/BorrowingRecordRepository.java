package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.BorrowingRecord;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {

    @Query(value = "SELECT br FROM BorrowingRecord br " +
            "JOIN Book b ON br.book.id = b.id " +
            "WHERE b.genre = 'SCIENCE_FICTION' AND br.borrowDate < '2021-09-10' " +
            "ORDER BY br.borrowDate DESC")
    Set<BorrowingRecord> findAllByBookGenreAndOrderByBorrowDateDesc();
}
