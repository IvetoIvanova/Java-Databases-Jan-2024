package bg.softuni.cardealerexercise.service.impls;

import bg.softuni.cardealerexercise.data.entities.Customer;
import bg.softuni.cardealerexercise.data.repositories.CustomerRepository;
import bg.softuni.cardealerexercise.service.CustomerService;
import bg.softuni.cardealerexercise.service.dtos.exports.CustomerOrderedDTO;
import bg.softuni.cardealerexercise.service.dtos.exports.CustomerOrderedRootDTO;
import bg.softuni.cardealerexercise.service.dtos.exports.CustomerTotalSalesDTO;
import bg.softuni.cardealerexercise.service.dtos.exports.CustomerTotalSalesRootDTO;
import bg.softuni.cardealerexercise.service.dtos.imports.CustomerSeedRootDTO;
import bg.softuni.cardealerexercise.util.XmlParser;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final String FILE_IMPORT_PATH = "src/main/resources/xml/imports/customers.xml";
    private static final String FILE_EXPORT_CUSTOMERS_PATH = "src/main/resources/xml/exports/ordered-customers.xml";
    private static final String FILE_EXPORT_CUSTOMERS_BOUGHT_PATH = "src/main/resources/xml/exports/customer-with-cars.xml";
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;


    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper, XmlParser xmlParser) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public void seedCustomers() throws JAXBException {
        if (this.customerRepository.count() == 0) {
            CustomerSeedRootDTO customerSeedRootDTO = this.xmlParser.parse(CustomerSeedRootDTO.class, FILE_IMPORT_PATH);
            customerSeedRootDTO.getCustomerSeedDTOList().forEach(c ->
                    this.customerRepository.saveAndFlush(this.modelMapper.map(c, Customer.class)));
        }
    }

    @Override
    public void exportOrderedCustomers() throws JAXBException {
        List<CustomerOrderedDTO> customerOrderedDTOS = this.customerRepository.findAllByOrderByBirthDateAscIsYoungDriverAsc()
                .stream()
                .map(c -> this.modelMapper.map(c, CustomerOrderedDTO.class))
                .collect(Collectors.toList());

        CustomerOrderedRootDTO customerOrderedRootDTO = new CustomerOrderedRootDTO();
        customerOrderedRootDTO.setCustomerOrderedDTOList(customerOrderedDTOS);

        this.xmlParser.exportToFile(CustomerOrderedRootDTO.class, customerOrderedRootDTO, FILE_EXPORT_CUSTOMERS_PATH);
    }

    @Override
    public void exportCustomersWithBoughtCars() throws JAXBException {
        List<CustomerTotalSalesDTO> collect = this.customerRepository.findAllWithBoughtCars()
                .stream()
                .map(c -> {
                    CustomerTotalSalesDTO customerTotalSalesDTO = new CustomerTotalSalesDTO();
                    customerTotalSalesDTO.setFullName(c.getName());
                    customerTotalSalesDTO.setBoughtCars(c.getSales().size());
                    double spentMoney = c.getSales()
                            .stream()
                            .mapToDouble(s -> s.getCar().getParts().stream().mapToDouble(p -> p.getPrice().doubleValue()).sum() * s.getDiscount())
                            .sum();
                    customerTotalSalesDTO.setSpentMoney(BigDecimal.valueOf(spentMoney));

                    return customerTotalSalesDTO;
                })
                .sorted(Comparator.comparing(CustomerTotalSalesDTO::getSpentMoney)
                        .reversed()
                        .thenComparing(Comparator.comparing(CustomerTotalSalesDTO::getBoughtCars).reversed()))
                .collect(Collectors.toList());

        CustomerTotalSalesRootDTO customerTotalSalesRootDTO = new CustomerTotalSalesRootDTO();
        customerTotalSalesRootDTO.setCustomerTotalSalesDTOList(collect);

        this.xmlParser.exportToFile(CustomerTotalSalesRootDTO.class, customerTotalSalesRootDTO, FILE_EXPORT_CUSTOMERS_BOUGHT_PATH);
    }
}
