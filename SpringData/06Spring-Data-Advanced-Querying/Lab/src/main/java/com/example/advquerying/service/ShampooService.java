package com.example.advquerying.service;

import com.example.advquerying.entities.Shampoo;

import java.math.BigDecimal;
import java.util.List;

public interface ShampooService {

    List<String> getAllShampoosByGivenSize(String size);

    List<String> getAllShampoosByGivenSizeOrLabel(String size, long id);

    List<String> getAllShampoosContainingIngredient(List<String> names);

    List<String> getAllShampoosWithPriceHigherThan(BigDecimal price);

    int countOfShampoosWithPriceLesserThan(BigDecimal price);

    List<Shampoo> getAllShampoosWithCountOfIngredientsBelowNumber(int count);
}
