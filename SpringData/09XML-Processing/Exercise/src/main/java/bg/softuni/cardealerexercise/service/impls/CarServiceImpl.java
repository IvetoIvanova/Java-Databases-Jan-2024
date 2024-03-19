package bg.softuni.cardealerexercise.service.impls;

import bg.softuni.cardealerexercise.data.entities.Car;
import bg.softuni.cardealerexercise.data.entities.Part;
import bg.softuni.cardealerexercise.data.repositories.CarRepository;
import bg.softuni.cardealerexercise.data.repositories.PartRepository;
import bg.softuni.cardealerexercise.service.CarService;
import bg.softuni.cardealerexercise.service.dtos.exports.*;
import bg.softuni.cardealerexercise.service.dtos.imports.CarSeedDTO;
import bg.softuni.cardealerexercise.service.dtos.imports.CarSeedRootDTO;
import bg.softuni.cardealerexercise.util.XmlParser;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    private static final String FILE_IMPORT_PATH = "src/main/resources/xml/imports/cars.xml";
    private static final String FILE_EXPORT_TOYOTA_PATH = "src/main/resources/xml/exports/toyota-cars.xml";
    private static final String FILE_EXPORT_CARS_AND_PARTS_PATH = "src/main/resources/xml/exports/cars-parts.xml";
    private final CarRepository carRepository;
    private final PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    public CarServiceImpl(CarRepository carRepository, PartRepository partRepository, ModelMapper modelMapper, XmlParser xmlParser) {
        this.carRepository = carRepository;
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public void seedCars() throws JAXBException {
        if (this.carRepository.count() == 0) {
            CarSeedRootDTO carSeedRootDTO = this.xmlParser.parse(CarSeedRootDTO.class, FILE_IMPORT_PATH);
            for (CarSeedDTO carSeedDTO : carSeedRootDTO.getCarSeedDTOList()) {
                Car car = this.modelMapper.map(carSeedDTO, Car.class);
                car.setParts(getRandomParts());

                carRepository.saveAndFlush(car);
            }
        }
    }

    @Override
    public void exportToyotaCars() throws JAXBException {
        List<CarToyotaDTO> toyotaDTOS = this.carRepository.findAllByMakeOrderByModelAscTravelledDistanceDesc("Toyota")
                .stream()
                .map(c -> this.modelMapper.map(c, CarToyotaDTO.class))
                .collect(Collectors.toList());

        CarToyotaRootDTO carToyotaRootDTO = new CarToyotaRootDTO();
        carToyotaRootDTO.setCarToyotaDTOList(toyotaDTOS);

        this.xmlParser.exportToFile(CarToyotaRootDTO.class, carToyotaRootDTO, FILE_EXPORT_TOYOTA_PATH);
    }

    @Override
    public void exportCarsAndParts() throws JAXBException {
        List<CarAndPartsDTO> carAndPartsDTOS = this.carRepository.findAll()
                .stream()
                .map(c -> {
                    CarAndPartsDTO carAndPartsDTO = this.modelMapper.map(c, CarAndPartsDTO.class);
                    PartRootDTO partRootDTO = new PartRootDTO();
                    List<PartDTO> partDTOS = c.getParts()
                            .stream()
                            .map(p -> this.modelMapper.map(p, PartDTO.class))
                            .collect(Collectors.toList());
                    partRootDTO.setPartDTOList(partDTOS);

                    carAndPartsDTO.setPartRootDTO(partRootDTO);
                    return carAndPartsDTO;

                })
                .collect(Collectors.toList());

        CarAndPartsRootDTO carAndPartsRootDTO = new CarAndPartsRootDTO();
        carAndPartsRootDTO.setCarAndPartsDTOList(carAndPartsDTOS);

        this.xmlParser.exportToFile(CarAndPartsRootDTO.class, carAndPartsRootDTO, FILE_EXPORT_CARS_AND_PARTS_PATH);
    }

    private Set<Part> getRandomParts() {
        Set<Part> parts = new HashSet<>();

        int count = ThreadLocalRandom.current().nextInt(2, 4);

        for (int i = 0; i < count; i++) {
            parts.add(
                    this.partRepository.
                            findById(ThreadLocalRandom.current().nextLong(1, this.partRepository.count() + 1))
                            .get());
        }

        return parts;
    }
}