package org.example.bookshopsystem.service.impl;

import org.example.bookshopsystem.data.entities.Author;
import org.example.bookshopsystem.data.repositories.AuthorRepository;
import org.example.bookshopsystem.service.AuthorService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private static final String FILE_PATH = "src/main/resources/files/authors.txt";

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (this.authorRepository.count() == 0) {
            Files.readAllLines(Path.of(FILE_PATH))
                    .stream()
                    .filter(row -> !row.isEmpty())
                    .forEach(row -> {
                        String[] tokens = row.split("\\s+");
                        this.authorRepository.saveAndFlush(new Author(tokens[0], tokens[1]));
                    });
        }
    }

    @Override
    public Author getRandomAuthor() {
        int randomId = ThreadLocalRandom
                .current()
                .nextInt(1, (int) this.authorRepository.count() + 1);
        return this.authorRepository.findById(randomId).get();
    }

    @Override
    public List<String> getAllAuthorsFirstAndLastNameForBooksBeforeYear1990() {
        return this.authorRepository.findAllByBooksReleaseDateBefore(LocalDate.of(1990, 1, 1))
                .stream()
                .map(author -> String.format("%s %s", author.getFirstName(), author.getLastName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllAuthorsByNumOfTheirBooksDesc() {
        return this.authorRepository.findAllByOrderByBooksSizeDesc()
                .stream()
                .map(a -> String.format("%s %s %d", a.getFirstName(), a.getLastName(), a.getBooks().size()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllNamesEndingIn(String ending) {
        return authorRepository.findAllByFirstNameEndsWith(ending)
                .stream()
                .map(a -> a.getFirstName() + " " + a.getLastName())
                .toList();
    }

    @Override
    public List<String> findAllAuthorsAndTheirTotalCopies() {
        return authorRepository
                .findAll()
                .stream()
                .map(author -> String.format("%s %s - %d", author.getFirstName(), author.getLastName(),
                        author
                                .getBooks()
                                .stream()
                                .map(Book::getCopies)
                                .reduce(Integer::sum)
                                .orElse(0)))
                .sorted(Comparator.comparingInt(s -> -Integer.parseInt(s.substring(s.lastIndexOf("-") + 2))))
                .collect(Collectors.toList());
    }
}
