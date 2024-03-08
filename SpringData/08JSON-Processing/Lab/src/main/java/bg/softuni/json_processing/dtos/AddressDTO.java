package bg.softuni.json_processing.dtos;

public class AddressDTO {
    private String country;
    private String city;

    public AddressDTO(String country, String city) {
        this.country = country;
        this.city = city;
    }
}
