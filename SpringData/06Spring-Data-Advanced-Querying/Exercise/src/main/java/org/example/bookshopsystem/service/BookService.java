package org.example.bookshopsystem.service;

import org.example.bookshopsystem.data.entities.Book;
import org.example.bookshopsystem.data.entities.enums.AgeRestriction;
import org.example.bookshopsystem.data.entities.enums.EditionType;
import org.example.bookshopsystem.data.repositories.BookInfo;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface BookService {

    void seedBooks() throws IOException;

    List<String> findAllBooksAfterYear2000();

    List<String> findAllBooksByGeorgePowellOrdered();

    List<String> findTitlesByAgeRestriction(AgeRestriction ageRestriction);

    List<String> findTitlesByEditionAndCopies(EditionType type, int copies);

    List<Book> findAllBooksWithPriceOutsideOf(int lowerBound, int upperBound);

    List<String> findTitlesForBooksNotPublishedIn(int year);

    List<Book> findAllReleasedBefore(LocalDate date);

    List<String> findTitlesContaining(String needle);

    List<String> findTitlesForAuthorNameStartingWith(String lastNameStartsWith);

    int findTitleCountLongerThan(int minLength);

    BookInfo findInfoByTitle(String title);
}
