package bg.softuni.cardealerexercise.service;

import jakarta.xml.bind.JAXBException;

public interface SaleService {

    void seedSales();

    void exportSales() throws JAXBException;
}
