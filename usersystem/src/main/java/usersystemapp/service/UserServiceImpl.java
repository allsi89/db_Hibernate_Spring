package usersystemapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usersystemapp.models.User;
import usersystemapp.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsersByEmailProvider(final String provider) {
        return this.userRepository.findAllByEmailEndingWith(provider);
    }

    @Override
    public void deleteInactiveUsers(final LocalDateTime date) {
        this.userRepository
                .findAllByLastTimeLoggedInBefore(date)
                .forEach(user -> user.setIsDeleted(true));
    }

    @Override
    public void save(final User user) {
        this.userRepository.save(user);
    }

    @Override
    public long getUsersCount() {
        return this.userRepository.count();
    }
}
