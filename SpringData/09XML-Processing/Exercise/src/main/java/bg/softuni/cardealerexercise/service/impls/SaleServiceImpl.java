package bg.softuni.cardealerexercise.service.impls;

import bg.softuni.cardealerexercise.data.entities.Car;
import bg.softuni.cardealerexercise.data.entities.Customer;
import bg.softuni.cardealerexercise.data.entities.Part;
import bg.softuni.cardealerexercise.data.entities.Sale;
import bg.softuni.cardealerexercise.data.repositories.CarRepository;
import bg.softuni.cardealerexercise.data.repositories.CustomerRepository;
import bg.softuni.cardealerexercise.data.repositories.SaleRepository;
import bg.softuni.cardealerexercise.service.SaleService;
import bg.softuni.cardealerexercise.service.dtos.exports.CarDTO;
import bg.softuni.cardealerexercise.service.dtos.exports.SaleDiscountDTO;
import bg.softuni.cardealerexercise.service.dtos.exports.SaleDiscountRootDTO;
import bg.softuni.cardealerexercise.util.XmlParser;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {

    private final List<Double> discounts = List.of(1.0, 0.95, 0.9, 0.85, 0.8, 0.7, 0.6, 0.5);
    private final SaleRepository saleRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    public SaleServiceImpl(SaleRepository saleRepository, CarRepository carRepository, CustomerRepository customerRepository, ModelMapper modelMapper, XmlParser xmlParser) {
        this.saleRepository = saleRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public void seedSales() {
        if (this.saleRepository.count() == 0) {
            for (int i = 0; i < 50; i++) {
                Sale sale = new Sale();
                sale.setCar(getRandomCar());
                sale.setCustomer(getRandomCustomer());
                sale.setDiscount(getRandomDiscount());
                this.saleRepository.saveAndFlush(sale);
            }
        }
    }

    @Override
    public void exportSales() throws JAXBException {
        List<SaleDiscountDTO> saleDiscountDTOS = this.saleRepository
                .findAll()
                .stream()
                .map(s -> {
                    SaleDiscountDTO saleDiscountDTO = this.modelMapper.map(s, SaleDiscountDTO.class);
                    CarDTO car = this.modelMapper.map(s.getCar(), CarDTO.class);

                    saleDiscountDTO.setCarDto(car);
                    saleDiscountDTO.setCustomerName(s.getCustomer().getName());
                    saleDiscountDTO.setPrice(s.getCar().getParts().stream().map(Part::getPrice).reduce(BigDecimal::add).get());
                    saleDiscountDTO.setPriceWithDiscount(saleDiscountDTO.getPrice().multiply(BigDecimal.valueOf(s.getDiscount())).add(saleDiscountDTO.getPrice()));
                    return saleDiscountDTO;
                })
                .collect(Collectors.toList());

        SaleDiscountRootDTO saleDiscountRootDTO = new SaleDiscountRootDTO();
        saleDiscountRootDTO.setSaleDiscountDTOList(saleDiscountDTOS);

        this.xmlParser.exportToFile(SaleDiscountRootDTO.class, saleDiscountRootDTO, "src/main/resources/xml/exports/sales-discount.xml");
    }

    private double getRandomDiscount() {
        return discounts.get(ThreadLocalRandom.current().nextInt(1, discounts.size()));
    }

    private Customer getRandomCustomer() {
        return this.customerRepository
                .findById(ThreadLocalRandom.current().nextLong(1, this.customerRepository.count() + 1))
                .get();
    }

    private Car getRandomCar() {
        return this.carRepository
                .findById(ThreadLocalRandom.current().nextLong(1, this.carRepository.count() + 1))
                .get();
    }
}
