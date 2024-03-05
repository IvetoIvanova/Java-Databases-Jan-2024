package org.example.bookshopsystem.data.repositories;

import org.example.bookshopsystem.data.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Set<Author> findAllByBooksReleaseDateBefore(LocalDate releaseDate);

    @Query("FROM Author a ORDER BY SIZE(a.books) DESC")
    Set<Author> findAllByOrderByBooksSizeDesc();

    List<Author> findAllByFirstNameEndsWith(String end);

    @Query("SELECT SUM(b.copies) " +
            "FROM Author a JOIN a.books b " +
            "WHERE a.firstName = :firstName AND a.lastName = :lastName")
    int countBookCopiesByAuthorName(String firstName, String lastName);
}
