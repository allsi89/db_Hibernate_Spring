package productshop.service;

import productshop.domain.dtos.SuccessfulSellerDto;
import productshop.domain.dtos.UserSeedDto;
import productshop.domain.dtos.UserWithProductsDto;

import java.util.List;

public interface UserService {

    void seedUsers(UserSeedDto[] userSeedDtos);

    List<SuccessfulSellerDto> getAll();

    List<UserWithProductsDto> getAllUsersWithProducts();

}
