package productshop.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import productshop.domain.dtos.*;
import productshop.domain.entities.User;
import productshop.repository.UserRepository;
import productshop.util.ValidatorUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ValidatorUtil validatorUtil, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedUsers(UserSeedDto[] userSeedDtos) {
        for (UserSeedDto userSeedDto : userSeedDtos) {
            if (!this.validatorUtil.isValid(userSeedDto)) {
                this.validatorUtil.violations(userSeedDto)
                        .forEach(v -> System.out.println(v.getMessage()));
                continue;
            }

            User entity = this.modelMapper.map(userSeedDto, User.class);
            this.userRepository.saveAndFlush(entity);
        }
    }

    @Override
    public List<SuccessfulSellerDto> getAll() {
        return this.userRepository.findAll()
                .stream()
                .map(u -> {
                    SuccessfulSellerDto successfulSellerDto = this.modelMapper.map(u, SuccessfulSellerDto.class);
                    return successfulSellerDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<UserWithProductsDto> getAllUsersWithProducts() {
        return this.userRepository
                .findAllOrderByProducts_Count()
                .stream().map(u -> this.modelMapper.map(u, UserWithProductsDto.class))
                .collect(Collectors.toList());
    }


}
