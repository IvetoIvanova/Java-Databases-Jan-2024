package org.example.bookshopsystem;

import org.example.bookshopsystem.service.AuthorService;
import org.example.bookshopsystem.service.BookService;
import org.example.bookshopsystem.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;

    public ConsoleRunner(AuthorService authorService, CategoryService categoryService, BookService bookService) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
//        getAllBooksAfterYear2000();
//        getAuthorsOfBooksBefore1990FirstAndLastName();
//        getAllAuthorsByTheNumberOfTheirBooksDesc();
        getBooksByGeorgePowellOrdered();
    }

    private void getBooksByGeorgePowellOrdered() {
        this.bookService.findAllBooksByGeorgePowellOrdered()
                .forEach(System.out::println);
    }

    private void getAllAuthorsByTheNumberOfTheirBooksDesc() {
        this.authorService.getAllAuthorsByNumOfTheirBooksDesc()
                .forEach(System.out::println);
    }

    private void getAuthorsOfBooksBefore1990FirstAndLastName() {
        this.authorService.getAllAuthorsFirstAndLastNameForBooksBeforeYear1990()
                .forEach(System.out::println);
    }

    private void getAllBooksAfterYear2000() {
        this.bookService.findAllBooksAfterYear2000()
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        this.categoryService.seedCategories();
        this.authorService.seedAuthors();
        this.bookService.seedBooks();
    }
}
