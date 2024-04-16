package bg.softuni.exercisesautomappingobjects.service.impl;

import bg.softuni.exercisesautomappingobjects.data.entities.Game;
import bg.softuni.exercisesautomappingobjects.data.repositories.GameRepository;
import bg.softuni.exercisesautomappingobjects.service.GameService;
import bg.softuni.exercisesautomappingobjects.service.UserService;
import bg.softuni.exercisesautomappingobjects.service.dtos.GameAddDTO;
import bg.softuni.exercisesautomappingobjects.service.dtos.GameDetailDTO;
import bg.softuni.exercisesautomappingobjects.service.dtos.GamesAllDTO;
import bg.softuni.exercisesautomappingobjects.service.dtos.GamesOwnedDTO;
import bg.softuni.exercisesautomappingobjects.util.ValidatorService;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final ValidatorService validatorService;
    private UserService userService;
    private final ModelMapper modelMapper;

    public GameServiceImpl(GameRepository gameRepository, ValidatorService validatorService, ModelMapper modelMapper) {
        this.gameRepository = gameRepository;
        this.validatorService = validatorService;
        this.modelMapper = modelMapper;
    }

    @Override
    public String addGame(GameAddDTO gameAddDTO) {
        if (!this.validatorService.isValid(gameAddDTO)) {
            return this.validatorService.validate(gameAddDTO)
                    .stream().map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("\n"));
        }

        Game game = this.modelMapper.map(gameAddDTO, Game.class);
        this.gameRepository.saveAndFlush(game);

        return String.format("Added %s", game.getTitle());
    }

    @Override
    public String editGame(long id, Map<String, String> map) {
        Optional<Game> optionalGame = this.gameRepository.findById(id);
        if (optionalGame.isEmpty()) {
            return "No such game exists with the given ID";
        }

        Game game = optionalGame.get();
        String output = String.format("Edited %s", game.getTitle());

        for (Map.Entry<String, String> entry : map.entrySet()) {
            switch (entry.getKey()) {
                case "title":
                    game.setTitle(entry.getValue());
                    break;
                case "price":
                    game.setPrice(Double.parseDouble(entry.getValue()));
                    break;
                case "size":
                    game.setSize(Double.parseDouble(entry.getValue()));
                    break;
                case "trailer":
                    game.setTrailer(entry.getValue());
                    break;
                case "thumbnail":
                    game.setThumbnail(entry.getValue());
                    break;
                case "description":
                    game.setDescription(entry.getValue());
                    break;
                case "releaseDate":
                    game.setReleaseDate(LocalDate.parse(entry.getValue()));
                    break;
            }
        }

        this.gameRepository.saveAndFlush(game);
        return output;
    }

    @Override
    public String deleteGame(long id) {
        Optional<Game> optionalGame = this.gameRepository.findById(id);

        if (optionalGame.isEmpty()) {
            return "No such game with the given ID";
        }

        String output = String.format("Deleted %s", optionalGame.get().getTitle());
        this.gameRepository.delete(optionalGame.get());
        return output;
    }

    @Override
    public Set<GamesAllDTO> getAllGames() {
        return this.gameRepository.findAll()
                .stream()
                .map(g -> this.modelMapper.map(g, GamesAllDTO.class))
                .collect(Collectors.toSet());
    }

    @Override
    public String allGamesReadyToPrint() {
        return this.getAllGames()
                .stream()
                .map(GamesAllDTO::toString)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public Optional<GameDetailDTO> findGameByTitle(String gameTitle) {
        return this.gameRepository.findByTitle(gameTitle)
                .stream()
                .map(g -> this.modelMapper.map(g, GameDetailDTO.class))
                .findFirst();
    }

    @Override
    public String printGameDetails(String game) {
        Optional<GameDetailDTO> gameOptional = findGameByTitle(game);
        return gameOptional
                .map(GameDetailDTO::toString)
                .orElse("Game not found.");
    }

    @Override
    public Set<GamesOwnedDTO> getOwnedGames() {
        return this.gameRepository.findAll()
                .stream()
                .map(g -> this.modelMapper.map(g, GamesOwnedDTO.class))
                .collect(Collectors.toSet());
    }

    @Override
    public String printOwnedGames() {
        return this.getOwnedGames()
                .stream()
                .map(GamesOwnedDTO::toString)
                .collect(Collectors.joining("\n"));
    }
}