package gamestore.service;

import gamestore.domain.dtos.GameAddDto;
import gamestore.domain.dtos.GameOwnedDto;

public interface GameService {
    String addGame(GameAddDto gameAddDto);

    String editGame(String[] params);

    String deleteGame(String id);

    String listGames();

    String getGameDetails(String title);

  //  List<GameOwnedDto> games()


}
