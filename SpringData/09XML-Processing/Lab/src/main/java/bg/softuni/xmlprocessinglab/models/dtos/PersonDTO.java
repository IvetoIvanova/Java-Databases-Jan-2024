package bg.softuni.xmlprocessinglab.models.dtos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "person")
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonDTO {
    @XmlElement(name = "firstName")
    private String firstName;
    @XmlElement(name = "lastName")
    private String lastName;
    @XmlElement(name = "age")
    private int age;

    private AddressDTO addressDTO;

    public PersonDTO() {
    }

    public PersonDTO(String firstName, String lastName, int age, AddressDTO addressDTO) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.addressDTO = addressDTO;
    }
}
