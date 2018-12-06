package usersystemapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import usersystemapp.models.User;
import usersystemapp.service.UserService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

import static usersystemapp.constants.Constants.*;

@SpringBootApplication
@Controller
public class UserController implements CommandLineRunner {
    private final UserService userService;
    private BufferedReader reader;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }


    @Override
    public void run(String... args) throws Exception {

        if (this.userService.getUsersCount() == 0L) {
            addUsers(100);
        }
        allUsersByEmailProvider("abv.bg");
        System.out.println(ANSI_RED+ SELECT_MSG + ANSI_RESET);
        this.reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println(ANSI_GREEN+ SEPARATOR_STRING + ANSI_RESET);
        LocalDate date = LocalDate.parse(reader.readLine(), DateTimeFormatter.ofPattern("d MMM yyyy"));
        System.out.println(ANSI_GREEN+ SEPARATOR_STRING + ANSI_RESET);
        deleteUsersInactiveSinceDate(LocalDateTime.of(date,
                LocalTime.parse("00:00:00",
                DateTimeFormatter.ofPattern("HH:mm:ss"))));
        System.out.println(ANSI_GREEN+ SEPARATOR_STRING + ANSI_RESET);
        System.out.println(ANSI_RED + DELETED_MSG + ANSI_RESET);
    }

    private void allUsersByEmailProvider(String provider) {
        for (User user : this.userService.getAllUsersByEmailProvider(provider)) {
            System.out.println(ANSI_BLUE + user.getUserName() + " " + user.getEmail() + ANSI_RESET);
        }
    }

    private void deleteUsersInactiveSinceDate(LocalDateTime date) {
        this.userService.deleteInactiveUsers(date);
    }


    private void addUsers(final int count) {
        for (int i = 1; i <= count; i++) {
            User user = new User();
            user.setUserName("username" + i);
            user.setPassword("pasSword%" + i);
            user.setEmail("student" + i + "@abv.bg");
            if (i % 2 == 0){
                user.setEmail("student" + i + "@soft-uni.bg");
            }
            user.setAge(i % 120 + 1);
            user.setFirstName("First" + i);
            user.setLastName("Last" + i);
            long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
            long maxDay = LocalDate.of(2015, 12, 31).toEpochDay();
            long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
            LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
            user.setRegisteredOn(LocalDateTime.of(randomDate,
                    LocalTime.parse("00:00:00",
                            DateTimeFormatter.ofPattern("HH:mm:ss"))));
            user.setLastTimeLoggedIn(LocalDateTime.of(randomDate,
                    LocalTime.parse("00:00:00",
                            DateTimeFormatter.ofPattern("HH:mm:ss"))));
            user.setIsDeleted(false);
            this.userService.save(user);
        }
    }
}
