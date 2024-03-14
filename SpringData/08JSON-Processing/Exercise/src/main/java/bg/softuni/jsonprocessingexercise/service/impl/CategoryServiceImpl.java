package bg.softuni.jsonprocessingexercise.service.impl;

import bg.softuni.jsonprocessingexercise.data.entities.Category;
import bg.softuni.jsonprocessingexercise.data.entities.Product;
import bg.softuni.jsonprocessingexercise.data.repositories.CategoryRepository;
import bg.softuni.jsonprocessingexercise.service.CategoryService;
import bg.softuni.jsonprocessingexercise.service.dtos.export.CategoryByProductsDTO;
import bg.softuni.jsonprocessingexercise.service.dtos.imports.CategorySeedDTO;
import bg.softuni.jsonprocessingexercise.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final String FILE_PATH = "src/main/resources/json/categories.json";

    private final CategoryRepository categoryRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedCategories() throws IOException {
        if (this.categoryRepository.count() == 0) {
            String jsonContent = new String(Files.readAllBytes(Path.of(FILE_PATH)));

            CategorySeedDTO[] categorySeedDTOs = this.gson.fromJson(jsonContent, CategorySeedDTO[].class);
            for (CategorySeedDTO categorySeedDTO : categorySeedDTOs) {
                if (!this.validationUtil.isValid(categorySeedDTO)) {
                    this.validationUtil.getViolations(categorySeedDTO)
                            .forEach(v -> System.out.println(v.getMessage()));
                    continue;
                }

                Category category = this.modelMapper.map(categorySeedDTO, Category.class);
                this.categoryRepository.saveAndFlush(category);
            }
        }
    }

    @Override
    public List<CategoryByProductsDTO> getAllCategoriesByProducts() {
        return this.categoryRepository.findAllCategoriesByProducts()
                .stream()
                .map(c -> {
                    CategoryByProductsDTO dto = this.modelMapper.map(c, CategoryByProductsDTO.class);
                    dto.setProductCount(c.getProducts().size());
                    BigDecimal sum = c.getProducts().stream().map(Product::getPrice).reduce(BigDecimal::add).get();
                    dto.setTotalRevenue(sum);
                    dto.setAveragePrice(sum.divide(BigDecimal.valueOf(c.getProducts().size()), MathContext.DECIMAL64));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void printAllCategoriesByProducts() {
        String json = this.gson.toJson(this.getAllCategoriesByProducts());
        System.out.println(json);
    }
}
