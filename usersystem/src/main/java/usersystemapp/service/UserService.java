package usersystemapp.service;

import usersystemapp.models.User;

import java.time.LocalDateTime;
import java.util.List;

public interface UserService {

    List<User> getAllUsersByEmailProvider(String provider);

    void deleteInactiveUsers(LocalDateTime date);

    void save(User user);

    long getUsersCount();
}
