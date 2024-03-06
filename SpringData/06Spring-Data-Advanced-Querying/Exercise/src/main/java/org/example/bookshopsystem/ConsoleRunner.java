package org.example.bookshopsystem;

import org.example.bookshopsystem.data.entities.Author;
import org.example.bookshopsystem.data.entities.Book;
import org.example.bookshopsystem.data.entities.enums.AgeRestriction;
import org.example.bookshopsystem.data.entities.enums.EditionType;
import org.example.bookshopsystem.data.repositories.BookInfo;
import org.example.bookshopsystem.service.AuthorService;
import org.example.bookshopsystem.service.BookService;
import org.example.bookshopsystem.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

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

//        Exercise1
//        printBooksByAgeRestriction();

//        Exercise2
//        printGoldenBooksWithLessThan5000Copies();

//        Exercise3
//        printBooksWithPriceOutOfRange();

//        Exercise4
//        printBooksNotIssuedAt();

//        Exercise5
//        printBookInfoForBooksReleasedBefore();

//        Exercise6
//        printAuthorsEndingIn();

//        Exercise7
//        printBookTitlesContaining();

//        Exercise8
//        printTitlesByAuthorsLastNameStartsWith();

//        Exercise9
//        findStatsForTitleLength();

//        Exercise10
//        printTotalBookCopiesByAuthorOrderedByTotalCopies();

//        Exercise11
//        printBookProjection();

    }

    private void printBookProjection() {
        Scanner scanner = new Scanner(System.in);
        String title = scanner.nextLine();

        BookInfo info = bookService.findInfoByTitle(title);

        System.out.println(info);
    }

    private void printTotalBookCopiesByAuthorOrderedByTotalCopies() {
        authorService.findAllAuthorsAndTheirTotalCopies()
                .forEach(System.out::println);
    }

    private void findStatsForTitleLength() {
        Scanner scanner = new Scanner(System.in);
        int minLength = Integer.parseInt(scanner.nextLine());

        int count = bookService.findTitleCountLongerThan(minLength);

        System.out.printf("There are %d books with longer titles than %d symbols.", count, minLength);
    }

    private void printTitlesByAuthorsLastNameStartsWith() {
        Scanner scanner = new Scanner(System.in);
        String lastNameStartsWith = scanner.nextLine();

        List<String> titles = bookService.findTitlesForAuthorNameStartingWith(lastNameStartsWith);
        titles.forEach(System.out::println);
    }

    private void printBookTitlesContaining() {
        Scanner scanner = new Scanner(System.in);
        String needle = scanner.nextLine();

        List<String> titles = bookService.findTitlesContaining(needle);
        titles.forEach(System.out::println);
    }

    private void printAuthorsEndingIn() {
        Scanner scanner = new Scanner(System.in);
        String ending = scanner.nextLine();

        List<String> names = authorService.findAllNamesEndingIn(ending);
        names.forEach(System.out::println);
    }

    private void printBookInfoForBooksReleasedBefore() {
        Scanner scanner = new Scanner(System.in);
        String beforeDate = scanner.nextLine();
        LocalDate parseDate = LocalDate.parse(beforeDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        List<Book> books = bookService.findAllReleasedBefore(parseDate);

        books.forEach(b -> System.out.printf("%s %s $%.2f%n", b.getTitle(), b.getEditionType(), b.getPrice()));
    }

    private void printBooksNotIssuedAt() {
        List<String> titles = bookService.findTitlesForBooksNotPublishedIn(2000);
        titles.forEach(System.out::println);
    }

    private void printBooksWithPriceOutOfRange() {
        List<Book> books = bookService.findAllBooksWithPriceOutsideOf(5, 40);
        books.forEach(b -> System.out.printf("%s - $%.2f%n", b.getTitle(), b.getPrice()));
    }

    private void printGoldenBooksWithLessThan5000Copies() {
        List<String> goldenBooks = bookService.findTitlesByEditionAndCopies(EditionType.GOLD, 5000);
        goldenBooks.forEach(System.out::println);
    }

    private void printBooksByAgeRestriction() {
        Scanner scanner = new Scanner(System.in);
        String restriction = scanner.nextLine();

        try {
            AgeRestriction ageRestriction = AgeRestriction.valueOf(restriction.toUpperCase());

            List<String> titles = bookService.findTitlesByAgeRestriction(ageRestriction);
            titles.forEach(t -> System.out.println(t));
        } catch (IllegalArgumentException ex) {
            System.out.println("Wrong age category");
            return;
        }
    }

    private void seedData() throws IOException {
        this.categoryService.seedCategories();
        this.authorService.seedAuthors();
        this.bookService.seedBooks();
    }
}
