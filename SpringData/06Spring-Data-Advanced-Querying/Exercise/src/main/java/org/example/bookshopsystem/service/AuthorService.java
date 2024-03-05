package org.example.bookshopsystem.service;

import org.example.bookshopsystem.data.entities.Author;

import java.io.IOException;
import java.util.List;

public interface AuthorService {

    void seedAuthors() throws IOException;

    Author getRandomAuthor();

    List<String> getAllAuthorsFirstAndLastNameForBooksBeforeYear1990();

    List<String> getAllAuthorsByNumOfTheirBooksDesc();

    List<String> findAllNamesEndingIn(String ending);

    int getTotalCopiesCountFor(String firstName, String secondName);
}
