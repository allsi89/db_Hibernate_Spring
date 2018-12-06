package productshop.domain.dtos;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UsersAndProductsDto implements Serializable {
    @Expose
    private Integer usersCount;
    @Expose
    private List<UserWithProductsDto> users;

    public UsersAndProductsDto() {
        users = new ArrayList<>();
    }

    public UsersAndProductsDto(Integer usersCount, List<UserWithProductsDto> users) {
        this.usersCount = usersCount;
        this.users = users;
    }

    public Integer getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(Integer usersCount) {
        this.usersCount = usersCount;
    }

    public List<UserWithProductsDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserWithProductsDto> users) {
        this.users = users;
    }
}
