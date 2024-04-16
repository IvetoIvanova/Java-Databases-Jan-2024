package bg.softuni.exercisesautomappingobjects.service.dtos;

public class GamesOwnedDTO {

    private String title;

    public GamesOwnedDTO(String title) {
        this.title = title;
    }

    public GamesOwnedDTO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
