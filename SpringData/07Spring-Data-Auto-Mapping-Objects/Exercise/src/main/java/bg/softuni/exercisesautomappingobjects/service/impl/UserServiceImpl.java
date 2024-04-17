package bg.softuni.exercisesautomappingobjects.service.impl;

import bg.softuni.exercisesautomappingobjects.data.entities.User;
import bg.softuni.exercisesautomappingobjects.data.repositories.UserRepository;
import bg.softuni.exercisesautomappingobjects.service.UserService;
import bg.softuni.exercisesautomappingobjects.service.dtos.UserLoginDTO;
import bg.softuni.exercisesautomappingobjects.service.dtos.UserRegisterDTO;
import bg.softuni.exercisesautomappingobjects.util.ValidatorService;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private User loggedIn;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final ValidatorService validatorService;

    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository, ValidatorService validatorService) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.validatorService = validatorService;
    }

    @Override
    public String registerUser(UserRegisterDTO userRegisterDTO) {
        if (!this.validatorService.isValid(userRegisterDTO)) {
            Set<ConstraintViolation<UserRegisterDTO>> set = this.validatorService.validate(userRegisterDTO);
            return set.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining("\n"));
        }

        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())) {
            return "Passwords don't match.";
        }

        Optional<User> optional = this.userRepository.findUserByEmail(userRegisterDTO.getEmail());
        if (optional.isPresent()) {
            return "User with that email already exists.";
        }

        User user = this.modelMapper.map(userRegisterDTO, User.class);
        if (this.userRepository.count() == 0) {
            user.setAdmin(true);
        }

        this.userRepository.saveAndFlush(user);

        return String.format("%s was registered.", user.getFullName());
    }

    @Override
    public String loginUser(UserLoginDTO userLoginDTO) {
        Optional<User> optional = this.userRepository.findByEmailAndPassword(userLoginDTO.getEmail(), userLoginDTO.getPassword());

        if (optional.isEmpty()) {
            return "Incorrect username / password";
        }

        setLoggedIn(optional.get());
        return String.format("Successfully logged in %s", optional.get().getFullName());
    }

    @Override
    public String logout() {

        if (this.loggedIn == null) {
            return "Cannot log out. No user was logged in.";
        }

        String output = String.format("User %s successfully logged out", this.loggedIn.getFullName());
        setLoggedIn(null);
        return output;
    }

    @Override
    public User getLoggedInUser() {
        return this.loggedIn;
    }

    private void setLoggedIn(User loggedIn) {
        this.loggedIn = loggedIn;
    }
}