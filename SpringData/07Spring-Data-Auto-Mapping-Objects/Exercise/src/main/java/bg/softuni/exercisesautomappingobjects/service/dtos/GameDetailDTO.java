package bg.softuni.exercisesautomappingobjects.service.dtos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GameDetailDTO {
    private String title;
    private double price;
    private String description;
    private LocalDate releaseDate;

    public GameDetailDTO() {
    }

    public GameDetailDTO(String title, double price, String description, LocalDate releaseDate) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = releaseDate.format(formatter);
        return String.format("""
                Title: %s
                Price: %.2f
                Description: %s
                Release date: %s""", title, price, description, formattedDate);
    }
}
