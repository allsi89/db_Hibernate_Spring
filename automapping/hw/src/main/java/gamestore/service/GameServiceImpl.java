package gamestore.service;

import gamestore.constants.Constants;
import gamestore.domain.dtos.*;
import gamestore.domain.entities.Game;
import gamestore.repository.GameRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, ModelMapper modelMapper) {
        this.gameRepository = gameRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public String addGame(GameAddDto gameAddDto) {

        Validator validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();

        StringBuilder sb = new StringBuilder();

        if (validator.validate(gameAddDto).size() > 0){

            validator
                    .validate(gameAddDto)
                    .forEach(violation->sb.append(violation.getMessage())
                    .append(System.lineSeparator()));
            return sb.toString().trim();
        } else {

            Game game = this.gameRepository.findByTitle(gameAddDto.getTitle()).orElse(null);

            if (game != null) {
                return sb.append(Constants.GAME_ALREADY_EXISTS).toString();
            }
            game = modelMapper.map(gameAddDto, Game.class);
            this.gameRepository.saveAndFlush(game);
            return sb.append(String.format(Constants.ADDED_S, game.getTitle())).toString();
        }
    }

    @Override
    public String editGame(String[] params) {

        Validator validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();

        Game game = this.gameRepository.findById(params[1]).orElse(null);

        StringBuilder sb = new StringBuilder();
        if (game == null){
           return sb.append(Constants.THERE_IS_NO_SUCH_GAME).toString();
        }

        GameEditDto gameEditDto = modelMapper.map(game, GameEditDto.class);

        for (int i = 2; i < params.length; i++) {
            String[] values = params[i].split("=");

            switch (values[0]){

                case "title":
                    gameEditDto.setTitle(values[1]);
                break;
                case "price":
                    gameEditDto.setPrice(new BigDecimal(values[1]));
                    break;
                case "size":
                    gameEditDto.setSize(Double.parseDouble(values[1]));
                    break;
                case "trailer":
                    gameEditDto.setTrailer(values[1]);
                    break;
                case "imageThumbnail":
                    gameEditDto.setImageThumbnail(values[1]);
                        break;
                case "description":
                    gameEditDto.setDescription(values[1]);
                    break;
                case "releaseDate":
                    gameEditDto.setReleaseDate(LocalDate.parse(values[1], DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                    break;
            }


        }
        if (validator.validate(gameEditDto).size()>0){
            validator.validate(gameEditDto)
                    .forEach(v->sb.append(v.getMessage()).append(System.lineSeparator()));
            return sb.toString().trim();
        }

        game = modelMapper.map(gameEditDto, Game.class);
        this.gameRepository.saveAndFlush(game);
        return sb.append(String.format("Edited %s", gameEditDto.getTitle())).toString();
    }

    @Override
    public String deleteGame(String id) {
        Game game = this.gameRepository.findById(id).orElse(null);

        StringBuilder sb = new StringBuilder();
        if (game == null){
            return sb.append(Constants.THERE_IS_NO_SUCH_GAME).toString();
        }
        GameDeleteDto gameDeleteDto = modelMapper.map(game, GameDeleteDto.class);
        this.gameRepository.delete(game);
        return sb.append(String.format("Deleted %s", gameDeleteDto.getTitle())).toString();

    }

    @Override
    public String listGames() {
        List<GameViewDto> gameViewsDtos = new ArrayList<>();
        this.gameRepository.findAll().forEach(g->gameViewsDtos.add(new GameViewDto(g.getTitle(), g.getPrice())));
        StringBuilder sb = new StringBuilder();
        gameViewsDtos.forEach(g->sb.append(g.toString()).append(System.lineSeparator()));
        return sb.toString().trim();
    }

    @Override
    public String getGameDetails(String title) {
        Game game = this.gameRepository.findByTitle(title).orElse(null);
        if (game == null){
            return Constants.GAME_DOESN_T_EXIST;
        }
        GameDetailsDto gameDetailsDto = modelMapper.map(game, GameDetailsDto.class);
        return gameDetailsDto.toString();
    }
}
