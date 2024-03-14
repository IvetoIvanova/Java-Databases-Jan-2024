package bg.softuni.jsonprocessingexercise.service;

import bg.softuni.jsonprocessingexercise.service.dtos.export.ProductInRangeDTO;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    void seedProducts() throws FileNotFoundException;

    List<ProductInRangeDTO> getAllProductsInRange(BigDecimal from, BigDecimal to);

    void printAllProductsInRange(BigDecimal from, BigDecimal to);
}
