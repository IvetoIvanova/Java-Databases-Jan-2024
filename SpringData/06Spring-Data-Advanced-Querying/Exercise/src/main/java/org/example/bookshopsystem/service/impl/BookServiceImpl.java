package org.example.bookshopsystem.service.impl;

import org.example.bookshopsystem.data.entities.Author;
import org.example.bookshopsystem.data.entities.Book;
import org.example.bookshopsystem.data.entities.Category;
import org.example.bookshopsystem.data.entities.enums.AgeRestriction;
import org.example.bookshopsystem.data.entities.enums.EditionType;
import org.example.bookshopsystem.data.repositories.BookInfo;
import org.example.bookshopsystem.data.repositories.BookRepository;
import org.example.bookshopsystem.service.AuthorService;
import org.example.bookshopsystem.service.BookService;
import org.example.bookshopsystem.service.CategoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final String FILE_PATH = "src/main/resources/files/books.txt";

    private final AuthorService authorService;

    private final CategoryService categoryService;
    private final BookRepository bookRepository;

    public BookServiceImpl(AuthorService authorService, CategoryService categoryService, BookRepository bookRepository) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookRepository = bookRepository;
    }

    @Override
    public void seedBooks() throws IOException {
        if (this.bookRepository.count() == 0) {
            Files.readAllLines(Path.of(FILE_PATH))
                    .stream()
                    .filter(row -> !row.isEmpty())
                    .forEach(row -> {
                        String[] data = row.split("\\s+");

                        Author author = this.authorService.getRandomAuthor();
                        EditionType editionType = EditionType.values()[Integer.parseInt(data[0])];

                        LocalDate releaseDate = LocalDate.parse(data[1],
                                DateTimeFormatter.ofPattern("d/M/yyyy"));
                        int copies = Integer.parseInt(data[2]);
                        BigDecimal price = new BigDecimal(data[3]);
                        AgeRestriction ageRestriction = AgeRestriction
                                .values()[Integer.parseInt(data[4])];
                        String title = Arrays.stream(data)
                                .skip(5)
                                .collect(Collectors.joining(" "));
                        Set<Category> categories = this.categoryService.getRandomCategories();


                        Book book = new Book(title, editionType, price, copies, releaseDate,
                                ageRestriction, author, categories);

                        this.bookRepository.saveAndFlush(book);
                    });
        }
    }

    @Override
    public List<String> findAllBooksAfterYear2000() {
        return this.bookRepository.findAllByReleaseDateAfter(LocalDate.of(2000, 12, 31))
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByGeorgePowellOrdered() {
        return this.bookRepository.findAllByAuthorFirstNameAndAuthorLastNameOrderByTitleAscReleaseDateDesc("George", "Powell")
                .stream()
                .map(b -> String.format("%s %s %d", b.getTitle(), b.getReleaseDate(), b.getCopies()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findTitlesByAgeRestriction(AgeRestriction restriction) {
        return bookRepository.findAllByAgeRestriction(restriction)
                .stream()
                .map(b -> b.getTitle())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findTitlesByEditionAndCopies(EditionType type, int copies) {
        return bookRepository.findAllByEditionTypeAndCopiesLessThan(type, copies)
                .stream()
                .map(b -> b.getTitle())
                .toList();
    }

    @Override
    public List<Book> findAllBooksWithPriceOutsideOf(int lowerBound, int upperBound) {
        return bookRepository.findAllByPriceLessThanOrPriceGreaterThan(BigDecimal.valueOf(lowerBound), BigDecimal.valueOf(upperBound));
    }

    @Override
    public List<String> findTitlesForBooksNotPublishedIn(int year) {
        return bookRepository.findAllByReleaseDateLessThanOrReleaseDateGreaterThan(LocalDate.of(year, 1, 1),
                        LocalDate.of(year, 12, 31))
                .stream()
                .map(b -> b.getTitle())
                .toList();
    }

    @Override
    public List<Book> findAllReleasedBefore(LocalDate date) {
        return bookRepository.findAllByReleaseDateBefore(date);
    }

    @Override
    public List<String> findTitlesContaining(String needle) {
        return bookRepository.findAllByTitleContaining(needle)
                .stream()
                .map(Book::getTitle)
                .toList();
    }

    @Override
    public List<String> findTitlesForAuthorNameStartingWith(String lastNameStartsWith) {
        return bookRepository.findAllByAuthorLastNameStartsWith(lastNameStartsWith)
                .stream()
                .map(b -> String.format("%s (%s %s)",
                        b.getTitle(),
                        b.getAuthor().getFirstName(),
                        b.getAuthor().getLastName()))
                .toList();
    }

    @Override
    public int findTitleCountLongerThan(int minLength) {
        return bookRepository.countByTitleContains(minLength);
    }

    @Override
    public BookInfo findInfoByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

}
