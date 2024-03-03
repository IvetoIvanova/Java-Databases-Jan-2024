package org.example.bookshopsystem.service.impl;

import org.example.bookshopsystem.data.entities.Author;
import org.example.bookshopsystem.data.entities.Book;
import org.example.bookshopsystem.data.entities.Category;
import org.example.bookshopsystem.data.entities.enums.AgeRestriction;
import org.example.bookshopsystem.data.entities.enums.EditionType;
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

}
