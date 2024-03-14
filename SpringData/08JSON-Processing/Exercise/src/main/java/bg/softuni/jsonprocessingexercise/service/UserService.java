package bg.softuni.jsonprocessingexercise.service;

import bg.softuni.jsonprocessingexercise.service.dtos.export.UserAndProductDTO;
import bg.softuni.jsonprocessingexercise.service.dtos.export.UserSoldProductsDTO;

import java.io.FileNotFoundException;
import java.util.List;

public interface UserService {

    void seedUsers() throws FileNotFoundException;

    List<UserSoldProductsDTO> getAllUsersAndSoldItems();

    void printAllUsersAndSoldItems();

    UserAndProductDTO getUserAndProductDTO();

    void printGetUserAndProductDTO();
}
