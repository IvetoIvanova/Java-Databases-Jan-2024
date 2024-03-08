package bg.softuni.json_processing.dtos;

import com.google.gson.annotations.Expose;

import java.util.List;

public class PersonDTO {
    @Expose
    private String firstName;
    private String lastName;
    private int age;
    @Expose
    private boolean isMarried;

    private List<Integer> lotteryNumbers;

    private AddressDTO address;

    public PersonDTO(String firstName, String lastName, int age, boolean isMarried, List<Integer> lotteryNumbers, AddressDTO address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.isMarried = isMarried;
        this.lotteryNumbers = lotteryNumbers;
        this.address = address;
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", isMarried=" + isMarried +
                ", lotteryNumbers=" + lotteryNumbers +
                ", address=" + address +
                '}';
    }
}
