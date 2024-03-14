package bg.softuni.jsonprocessingexercise.service;

import bg.softuni.jsonprocessingexercise.service.dtos.export.CategoryByProductsDTO;

import java.io.IOException;
import java.util.List;

public interface CategoryService {

    void seedCategories() throws IOException;

    List<CategoryByProductsDTO> getAllCategoriesByProducts();

    void printAllCategoriesByProducts();
}
