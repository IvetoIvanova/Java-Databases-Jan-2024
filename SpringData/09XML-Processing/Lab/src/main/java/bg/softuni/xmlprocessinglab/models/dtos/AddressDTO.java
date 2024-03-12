package bg.softuni.xmlprocessinglab.models.dtos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

//@XmlRootElement(name = "address")
@XmlAccessorType(XmlAccessType.FIELD)
public class AddressDTO {

    //    @XmlElement
    private String country;

    //    @XmlElement
    private String city;

    public AddressDTO() {
    }

    public AddressDTO(String country, String city) {
        this.country = country;
        this.city = city;
    }
}
