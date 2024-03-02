package com.example.advquerying.service;

import com.example.advquerying.entities.Ingredient;

import java.util.List;

public interface IngredientService {

    List<String> getAllIngredientWithStartingName(String symbol);

    List<Ingredient> getAllIngredientsContainingInOrderByPrice(List<String> names);

    int deleteIngredientByName(String name);

    int updatedIngredientPrices();

    int updatePricesOfIngredientsForGivenNames();
}
