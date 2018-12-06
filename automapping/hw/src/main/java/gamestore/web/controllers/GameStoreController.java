package gamestore.web.controllers;

import gamestore.constants.Constants;
import gamestore.domain.dtos.GameAddDto;
import gamestore.domain.dtos.UserLoginDto;
import gamestore.domain.dtos.UserLogoutDto;
import gamestore.domain.dtos.UserRegisterDto;
import gamestore.service.GameService;
import gamestore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static gamestore.constants.Constants.LOGGED_IN;

@Controller
public class GameStoreController implements CommandLineRunner {

    private final UserService userService;
    private final GameService gameService;
    private String loggedInUser;

    @Autowired
    public GameStoreController(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @Override
    public void run(String... args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println(Constants.APP_START_STR);
        String input;
        while (!Constants.STOP.equalsIgnoreCase(input = reader.readLine())) {

            String[] params = input.split("\\|");
            switch (params[0]) {
                case Constants.REGISTER_USER:
                    System.out.println(registerUser(params));
                    break;
                case Constants.LOGIN_USER:
                    System.out.println(loginUser(params));
                    break;
                case Constants.LOGOUT:
                    logoutUser();
                    break;
                case Constants.ADD_GAME:
                    addGame(params);
                    break;
                case Constants.EDIT_GAME:
                    editGame(params);
                    break;
                case Constants.DELETE_GAME:
                    deleteGame(params[1]);
                    break;
                case Constants.ALL_GAME:
                    System.out.println(this.gameService.listGames());
                    ;
                    break;
                case Constants.DETAIL_GAME:
                    System.out.println(this.gameService.getGameDetails(params[1]));
                    break;
                default:
                    System.out.println(Constants.NO_VALID_INPUT);

            }
        }
    }

    private void getOwnedGames() {
    }

    private String loginUser(String[] params) {
        if (this.loggedInUser == null) {
            UserLoginDto userLoginDto = new UserLoginDto(params[1], params[2]);
            String longinResult = this.userService.loginUser(userLoginDto);
            if (longinResult.contains(LOGGED_IN)) {
                this.loggedInUser = userLoginDto.getEmail();
            }
            return longinResult;

        } else {
            return Constants.SESSION_IS_TAKEN;
        }
    }

    private String registerUser(String[] params) {
        UserRegisterDto userRegisterDto = new UserRegisterDto(
                params[1],
                params[2],
                params[3],
                params[4]);
        return this.userService.registerUser(userRegisterDto);
    }

    private void logoutUser() {
        if (this.loggedInUser == null) {
            System.out.println(Constants.CANNOT_LOG_OUT_NO_USER_WAS_LOGGED_IN);
        } else {
            System.out.println(this.userService.logoutUser(new UserLogoutDto(loggedInUser)));
            this.loggedInUser = null;
        }
    }

    private void addGame(String[] params) {
        if (this.loggedInUser == null) {
            System.out.println(Constants.NO_LOGGED_USER);
        } else {
            if (checkUser()) {
                GameAddDto gameAddDto;
                try {
                    gameAddDto = new GameAddDto(
                            params[1],
                            new BigDecimal(params[2]),
                            Double.parseDouble(params[3]),
                            params[4],
                            params[5],
                            params[6],
                            LocalDate.parse(params[7], DateTimeFormatter.ofPattern("dd-MM-yyyy")));

                    System.out.println(this.gameService.addGame(gameAddDto));

                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("You must be ADMIN to add a game.");

            }
        }
    }

    private void editGame(String[] params) {
        if (this.loggedInUser == null) {
            System.out.println(Constants.NO_LOGGED_USER);
        } else {
            if (checkUser()) {
                System.out.println(this.gameService.editGame(params));
            } else {
                System.out.println("You must be ADMIN to edit a game.");

            }
        }
    }

    private void deleteGame(String id) {
        if (this.loggedInUser == null) {
            System.out.println(Constants.NO_LOGGED_USER);
        } else {
            if (checkUser()) {
                System.out.println(this.gameService.deleteGame(id));
            } else {
                System.out.println(Constants.YOU_MUST_BE_ADMIN_TO_DELETE_A_GAME);
            }
        }
    }

    private boolean checkUser() {
        return this.userService.checkRole(loggedInUser);
    }
}
