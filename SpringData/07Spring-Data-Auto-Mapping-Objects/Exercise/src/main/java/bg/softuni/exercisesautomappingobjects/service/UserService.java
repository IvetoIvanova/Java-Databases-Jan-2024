package bg.softuni.exercisesautomappingobjects.service;

import bg.softuni.exercisesautomappingobjects.data.entities.User;
import bg.softuni.exercisesautomappingobjects.service.dtos.UserLoginDTO;
import bg.softuni.exercisesautomappingobjects.service.dtos.UserRegisterDTO;

public interface UserService {
    String registerUser(UserRegisterDTO userRegisterDTO);

    String loginUser(UserLoginDTO userLoginDTO);

    String logout();

    User getLoggedInUser();
}
