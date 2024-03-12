package bg.softuni.xmlprocessinglab.models;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;

//@XmlAccessorType(XmlAccessType.FIELD)
public class PhoneNumber {

    @XmlTransient
    private String name;
    @XmlElement
    private String number;

    public PhoneNumber() {
    }

    public PhoneNumber(String name, String number) {
        this.name = name;
        this.number = number;
    }
}
