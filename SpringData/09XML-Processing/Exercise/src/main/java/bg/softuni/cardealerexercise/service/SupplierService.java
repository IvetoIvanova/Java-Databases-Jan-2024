package bg.softuni.cardealerexercise.service;

import jakarta.xml.bind.JAXBException;

public interface SupplierService {

    void seedSupplier() throws JAXBException;

    void exportLocalSuppliers() throws JAXBException;
}
