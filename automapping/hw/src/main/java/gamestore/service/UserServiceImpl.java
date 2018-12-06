package gamestore.service;

import gamestore.constants.Constants;
import gamestore.domain.dtos.UserLoginDto;
import gamestore.domain.dtos.UserLogoutDto;
import gamestore.domain.dtos.UserRegisterDto;
import gamestore.domain.entities.Role;
import gamestore.domain.entities.User;
import gamestore.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validation;
import javax.validation.Validator;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public String registerUser(UserRegisterDto userRegisterDto) {

        Validator validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();

        StringBuilder sb = new StringBuilder();

        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())) {
            sb.append(Constants.PASSWORDS_DON_T_MATCH);
        } else if (validator.validate(userRegisterDto).size() > 0) {
            validator.validate(userRegisterDto).forEach(violation ->
                    sb.append(violation.getMessage()).append(System.lineSeparator()));
        } else {
            User user = this.userRepository.findByEmail(userRegisterDto.getEmail()).orElse(null);
            if (user != null) {
                return sb.append(Constants.USER_ALREADY_EXISTS).toString();
            }

            user = modelMapper.map(userRegisterDto, User.class);
            if (this.userRepository.count() == 0) {
                user.setRole(Role.ADMIN);
            } else {
                user.setRole(Role.USER);
            }
            this.userRepository.saveAndFlush(user);
            sb.append(userRegisterDto.getFullName()).append(Constants.WAS_REGISTERED);
        }
        return sb.toString().trim();
    }

    @Override
    public String loginUser(UserLoginDto userLoginDto) {
        Validator validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();

        StringBuilder sb = new StringBuilder();

        if (validator.validate(userLoginDto).size() > 0) {
            validator.validate(userLoginDto).forEach(violation ->
                    sb.append(violation.getMessage()).append(System.lineSeparator()));
        } else {
            User user = this.userRepository.findByEmail(userLoginDto.getEmail()).orElse(null);

            if (user == null) {
                return sb.append(Constants.USER_DOES_NOT_EXIST).toString();
            } else if (!user.getPassword().equals(userLoginDto.getPassword())) {
                return sb.append(Constants.INCORRECT_USERNAME_PASSWORD).toString();
            }

            return sb.append(Constants.LOGGED_IN).append(user.getFullName()).toString();
        }

        return null;
    }

    @Override
    public String logoutUser(UserLogoutDto userLogoutDto) {
        User user = this.userRepository.findByEmail(userLogoutDto.getEmail()).orElse(null);
        StringBuilder sb = new StringBuilder();
        if (user != null) {
            return sb.append(String.format(Constants.USER_S_SUCCESSFULLY_LOGGED_OUT, user.getFullName())).toString();
        } else {
            return Constants.CANNOT_LOG_OUT_NO_USER_WAS_LOGGED_IN;
        }
    }

    @Override
    public boolean checkRole(String loggedInUser) {
        User user = this.userRepository.findByEmail(loggedInUser).orElse(null);
        return user != null && user.getRole().equals(Role.ADMIN);
    }
}
