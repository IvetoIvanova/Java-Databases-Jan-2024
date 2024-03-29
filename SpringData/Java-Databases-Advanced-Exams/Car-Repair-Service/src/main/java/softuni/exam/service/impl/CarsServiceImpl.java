package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xmls.CarRootDTO;
import softuni.exam.models.dto.xmls.CarSeedDTO;
import softuni.exam.models.entity.Car;
import softuni.exam.repository.CarsRepository;
import softuni.exam.service.CarsService;
import softuni.exam.util.ValidationUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class CarsServiceImpl implements CarsService {
    private static String CARS_FILE_PATH = "src/main/resources/files/xml/cars.xml";
    private final CarsRepository carRepository;

    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public CarsServiceImpl(CarsRepository carRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsFromFile() throws IOException {
        return new String(Files.readAllBytes(Path.of(CARS_FILE_PATH)));
    }

    @Override
    public String importCars() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext jaxbContext = JAXBContext.newInstance(CarRootDTO.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        CarRootDTO carRootDTO = (CarRootDTO) unmarshaller.unmarshal(new File(CARS_FILE_PATH));

        for (CarSeedDTO carSeedDTO : carRootDTO.getCarSeedDTOs()) {
            Optional<Car> optional = this.carRepository.findByPlateNumber(carSeedDTO.getPlateNumber());

            if (!this.validationUtil.isValid(carSeedDTO) || optional.isPresent()) {
                sb.append("Invalid car\n");
                continue;
            }

            Car car = this.modelMapper.map(carSeedDTO, Car.class);
            this.carRepository.saveAndFlush(car);

            sb.append(String.format("Successfully imported car %s - %s\n",
                    car.getCarMake(), car.getCarModel()));
        }

        return sb.toString();
    }
}
