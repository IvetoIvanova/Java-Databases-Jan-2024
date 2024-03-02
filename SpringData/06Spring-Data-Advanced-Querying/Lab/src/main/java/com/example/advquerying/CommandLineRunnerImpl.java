package com.example.advquerying;

import com.example.advquerying.service.IngredientService;
import com.example.advquerying.service.ShampooService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final ShampooService shampooService;

    private final IngredientService ingredientService;

    public CommandLineRunnerImpl(ShampooService shampooService, IngredientService ingredientService) {
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
    }

    @Override
    public void run(String... args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//      Exercise1
//        this.shampooService.getAllShampoosByGivenSize(reader.readLine())
//                .forEach(System.out::println);

//      Exercise2
//        this.shampooService.getAllShampoosByGivenSizeOrLabel("medium", 10)
//                .forEach(System.out::println);

//      Exercise3
//        this.shampooService.getAllShampoosWithPriceHigherThan(BigDecimal.valueOf(5))
//                .forEach(System.out::println);

//      Exercise4
//        this.ingredientService.getAllIngredientWithStartingName("M")
//                .forEach(System.out::println);

//      Exercise5
//        this.ingredientService.getAllIngredientsContainingInOrderByPrice(List.of("Lavender", "Herbs", "Apple"))
//                .forEach(ingredient -> System.out.println(ingredient.getName()));

//      Exercise6
//        System.out.println(this.shampooService.countOfShampoosWithPriceLesserThan(BigDecimal.valueOf(8.5)));

//      Exercise7
//        this.shampooService.getAllShampoosContainingIngredient(List.of(reader.readLine().split(" ")))
//                .forEach(System.out::println);

//      Exercise8
//        this.shampooService.getAllShampoosWithCountOfIngredientsBelowNumber(2)
//                .forEach(shampoo -> System.out.println(shampoo.getBrand()));

//      Exercise9
//        System.out.println(this.ingredientService.deleteIngredientByName("Active-Caffeine"));

//      Exercise10
//        this.ingredientService.updatedIngredientPrices();

//      Exercise11
        System.out.println(this.ingredientService.updatePricesOfIngredientsForGivenNames());

    }
}
