package bg.softuni.exercisesautomappingobjects.service;

import bg.softuni.exercisesautomappingobjects.service.dtos.CartItemDTO;

public interface ShoppingCartService {

    String addItem(CartItemDTO item);

    String deleteItem(CartItemDTO item);

    String buyItem();
}
