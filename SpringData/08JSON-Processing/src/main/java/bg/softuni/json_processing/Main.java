package bg.softuni.json_processing;

import bg.softuni.json_processing.dtos.AddressDTO;
import bg.softuni.json_processing.dtos.PersonDTO;
import bg.softuni.json_processing.services.PersonService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Main implements CommandLineRunner {

    private Gson gson;

    private PersonService personService;

    public Main(@Qualifier("withoutNulls") Gson gson, PersonService personService) {
        this.gson = gson;
        this.personService = personService;
    }

    @Override
    public void run(String... args) throws Exception {

//        Gson gson = new GsonBuilder()
//                .create();

//        Gson gson = new GsonBuilder()
//                .excludeFieldsWithoutExposeAnnotation()
//                .create();

//        Gson gson = new GsonBuilder()
//                .excludeFieldsWithoutExposeAnnotation()
//                .setPrettyPrinting()
//                .create();

//        Gson gson = new GsonBuilder()
//                .serializeNulls()
//                .setPrettyPrinting()
//                .create();

        printJson(gson);
//        readJson(gson);
    }

    private void readJson(Gson gson) {
        String json = """
                [
                  {
                    "firstName": "Georgi",
                    "lastName": "Dimitrov",
                    "age": 32,
                    "isMarried": true,
                    "lotteryNumbers": [
                      7,
                      9,
                      37,
                      17,
                      14
                    ],
                    "address": {
                      "country": "BG",
                      "city": "Sofia"
                    }
                  },
                  {
                    "firstName": "Ivan",
                    "lastName": null,
                    "age": 33,
                    "isMarried": true,
                    "lotteryNumbers": [
                      4,
                      16,
                      5,
                      22,
                      1
                    ],
                    "address": {
                      "country": "BG",
                      "city": "Sofia"
                    }
                  }
                ]
                """;

        PersonDTO[] personDTO = gson.fromJson(json, PersonDTO[].class);
        for (PersonDTO dto : personDTO) {
            System.out.println(dto);
        }
    }

    private void printJson(Gson gson) {
        PersonDTO personDTO = new PersonDTO(
                "Georgi",
                "Dimitrov",
                32,
                true,
                List.of(7, 9, 37, 17, 14),
                new AddressDTO("BG", "Sofia"));

        PersonDTO personDTO2 = new PersonDTO("Ivan",
                null,
                33,
                true,
                List.of(4, 16, 5, 22, 1),
                new AddressDTO("BG", "Plovdiv"));

//        String json = gson.toJson(personDTO);
        String json = gson.toJson(List.of(personDTO, personDTO2));

        System.out.println(json);
    }
}