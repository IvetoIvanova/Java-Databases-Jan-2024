package bg.softuni.exercisesautomappingobjects.service;

import bg.softuni.exercisesautomappingobjects.service.dtos.GameAddDTO;
import bg.softuni.exercisesautomappingobjects.service.dtos.GameDetailDTO;
import bg.softuni.exercisesautomappingobjects.service.dtos.GamesAllDTO;
import bg.softuni.exercisesautomappingobjects.service.dtos.GamesOwnedDTO;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface GameService {
    String addGame(GameAddDTO gameAddDTO);

    String editGame(long id, Map<String, String> map);

    String deleteGame(long id);

    Set<GamesAllDTO> getAllGames();

    String allGamesReadyToPrint();

    Optional<GameDetailDTO> findGameByTitle(String gameTitle);

    String printGameDetails(String game);

    Set<GamesOwnedDTO> getOwnedGames();

    String printOwnedGames();
}
