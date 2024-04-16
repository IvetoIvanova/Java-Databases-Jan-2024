package bg.softuni.exercisesautomappingobjects.service.impl;

import bg.softuni.exercisesautomappingobjects.data.entities.Game;
import bg.softuni.exercisesautomappingobjects.data.entities.User;
import bg.softuni.exercisesautomappingobjects.data.repositories.GameRepository;
import bg.softuni.exercisesautomappingobjects.data.repositories.OrderRepository;
import bg.softuni.exercisesautomappingobjects.data.repositories.UserRepository;
import bg.softuni.exercisesautomappingobjects.service.ShoppingCartService;
import bg.softuni.exercisesautomappingobjects.service.UserService;
import bg.softuni.exercisesautomappingobjects.service.dtos.CartItemDTO;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final OrderRepository orderRepository;
    private Set<Game> cart = new HashSet<>();

    public ShoppingCartServiceImpl(UserService userService, UserRepository userRepository, GameRepository gameRepository, OrderRepository orderRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public String addItem(CartItemDTO item) {
        if (this.userService.getLoggedInUser() != null) {
            Optional<Game> optional = this.gameRepository.findByTitle(item.getTitle());

            if (optional.isEmpty()) {
                return "That game doesn't exist.";
            }

            this.cart.add(optional.get());
            return String.format("%s added to cart.", optional.get().getTitle());
        }

        return "No user is logged in";
    }

    @Override
    public String deleteItem(CartItemDTO item) {
        if (this.userService.getLoggedInUser() != null) {
            Optional<Game> optional = this.gameRepository.findByTitle(item.getTitle());

            if (optional.isEmpty()) {
                return "That game doesn't exist.";
            }

            this.cart.remove(optional.get());
            return String.format("%s removed from cart.", optional.get().getTitle());
        }

        return "No user is logged in";
    }

    @Override
    public String buyItem() {
        User loggedInUser = this.userService.getLoggedInUser();

        if (loggedInUser != null) {
            loggedInUser.getGames().addAll(this.cart);
            this.userRepository.saveAndFlush(loggedInUser);

            String output = "Successfully bought games:\n" +
                    this.cart.stream()
                            .map(g -> " -" + g.getTitle())
                            .collect(Collectors.joining("\n"));
            this.cart = new HashSet<>();
            return output;
        }

        return "No user is logged in";
    }
}
