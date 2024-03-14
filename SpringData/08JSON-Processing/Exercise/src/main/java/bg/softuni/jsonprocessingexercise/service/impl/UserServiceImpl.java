package bg.softuni.jsonprocessingexercise.service.impl;

import bg.softuni.jsonprocessingexercise.data.entities.User;
import bg.softuni.jsonprocessingexercise.data.repositories.UserRepository;
import bg.softuni.jsonprocessingexercise.service.UserService;
import bg.softuni.jsonprocessingexercise.service.dtos.export.*;
import bg.softuni.jsonprocessingexercise.service.dtos.imports.UserSeedDTO;
import bg.softuni.jsonprocessingexercise.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final String FILE_PATH = "src/main/resources/json/users.json";
    private final UserRepository userRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedUsers() throws FileNotFoundException {
        if (this.userRepository.count() == 0) {
            UserSeedDTO[] userSeedDTOS = this.gson.fromJson(new FileReader(FILE_PATH), UserSeedDTO[].class);
            for (UserSeedDTO userSeedDTO : userSeedDTOS) {
                if (!this.validationUtil.isValid(userSeedDTO)) {
                    this.validationUtil.getViolations(userSeedDTO)
                            .forEach(v -> System.out.println(v.getMessage()));
                    continue;
                }

                this.userRepository.saveAndFlush(this.modelMapper.map(userSeedDTO, User.class));
            }
        }
    }

    @Override
    public List<UserSoldProductsDTO> getAllUsersAndSoldItems() {
        List<UserSoldProductsDTO> allUsers = this.userRepository.findAll()
                .stream()
                .filter(u ->
                        u.getSold().stream().anyMatch(p -> p.getBuyer() != null))
                .map(u -> {
                    UserSoldProductsDTO userDTO = this.modelMapper.map(u, UserSoldProductsDTO.class);

                    List<ProductSoldDTO> soldProductsDTO = u.getSold()
                            .stream()
                            .filter(p -> p.getBuyer() != null)
                            .map(p -> this.modelMapper.map(p, ProductSoldDTO.class))
                            .collect(Collectors.toList());
                    userDTO.setSoldProducts(soldProductsDTO);
                    return userDTO;
                })
                .sorted(Comparator.comparing(UserSoldProductsDTO::getLastName).thenComparing(UserSoldProductsDTO::getFirstName))
                .toList();
        return allUsers;
    }

    @Override
    public void printAllUsersAndSoldItems() {
        String json = this.gson.toJson(this.getAllUsersAndSoldItems());
        System.out.println(json);
    }

    @Override
    public UserAndProductDTO getUserAndProductDTO() {
        UserAndProductDTO userAndProductDTO = new UserAndProductDTO();
        List<UserSoldDTO> userSoldDTOs = this.userRepository.findAll()
                .stream()
                .filter(u -> !u.getSold().isEmpty())
                .map(u -> {
                    UserSoldDTO userSoldDTO = this.modelMapper.map(u, UserSoldDTO.class);
                    ProductSoldByUserDTO productSoldByUserDTO = new ProductSoldByUserDTO();

                    List<ProductInfoDTO> productInfoDTOs = u.getSold()
                            .stream()
                            .map(p -> this.modelMapper.map(p, ProductInfoDTO.class))
                            .collect(Collectors.toList());
                    productSoldByUserDTO.setProducts(productInfoDTOs);
                    productSoldByUserDTO.setCount(productInfoDTOs.size());

                    userSoldDTO.setSoldProducts(productSoldByUserDTO);

                    return userSoldDTO;
                })
                .sorted((a, b) -> {
                    int countA = a.getSoldProducts().getCount();
                    int countB = b.getSoldProducts().getCount();
                    return Integer.compare(countB, countA);
                })
                .sorted(Comparator.comparing(UserSoldDTO::getLastName))
                .collect(Collectors.toList());

        userAndProductDTO.setUsers(userSoldDTOs);
        userAndProductDTO.setUsersCount(userSoldDTOs.size());
        return userAndProductDTO;
    }

    @Override
    public void printGetUserAndProductDTO() {
        String json = this.gson.toJson(this.getUserAndProductDTO());
        System.out.println(json);
    }
}
