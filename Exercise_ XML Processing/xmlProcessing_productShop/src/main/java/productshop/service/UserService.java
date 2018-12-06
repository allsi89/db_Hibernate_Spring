package productshop.service;

import productshop.domain.dtos.views.soldproducts.SuccessfulSellerDto;
import productshop.domain.dtos.binding.UserSeedDto;
import productshop.domain.dtos.views.usersandproducts.UserWithProductsDto;

import java.util.List;

public interface UserService {

    void seedUsers(List<UserSeedDto> userSeedDtos);

    List<SuccessfulSellerDto> getAll();

    List<UserWithProductsDto> getAllUsersWithProducts();

}
