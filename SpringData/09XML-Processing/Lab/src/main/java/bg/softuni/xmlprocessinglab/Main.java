package bg.softuni.xmlprocessinglab;

import bg.softuni.xmlprocessinglab.models.dtos.AddressDTO;
import bg.softuni.xmlprocessinglab.models.dtos.PersonDTO;
import bg.softuni.xmlprocessinglab.models.PhoneBook;
import bg.softuni.xmlprocessinglab.models.PhoneNumber;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Main implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        JAXBContext personContext = JAXBContext.newInstance(PersonDTO.class);
        Marshaller personMarshaller = personContext.createMarshaller();
        personMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        AddressDTO addressDTO = new AddressDTO("BG", "Plovdiv");
        PersonDTO person = new PersonDTO("Georgi", "Dimitrov", 18, addressDTO);
        personMarshaller.marshal(person, System.out);

        Unmarshaller personUnmarshaller = personContext.createUnmarshaller();
        PersonDTO parsed = (PersonDTO) personUnmarshaller.unmarshal(System.in);

        JAXBContext bookContext = JAXBContext.newInstance(PhoneBook.class);
        Marshaller bookMarshaller = bookContext.createMarshaller();
        bookMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        PhoneNumber number1 = new PhoneNumber("Denis", "032822365");
        PhoneNumber number2 = new PhoneNumber("Pavel", "032823365");
        PhoneNumber number3 = new PhoneNumber("Marin", "032822665");
        PhoneBook book = new PhoneBook(
                "Martin",
                List.of("First", "Second", "Third"),
                List.of(number1, number2, number3));

        bookMarshaller.marshal(book, System.out);

        Unmarshaller bookUnmarshaller = bookContext.createUnmarshaller();
        PhoneBook parseBook = (PhoneBook) bookUnmarshaller.unmarshal(System.in);
        System.out.println();
    }
}
