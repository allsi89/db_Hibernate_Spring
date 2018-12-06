package productshop.domain.dtos;

import com.google.gson.annotations.Expose;

import java.util.List;

public class SuccessfulSellerDto {
    private Integer id;
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private List<SoldProductDto> soldProducts;

    public SuccessfulSellerDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<SoldProductDto> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProductDtos(List<SoldProductDto> soldProductDtos) {
        this.soldProducts = soldProductDtos;
    }
}
