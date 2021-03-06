package productshop.domain.dtos.views.usersandproducts;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
public class UserWithProductsDto implements Serializable {
    @XmlTransient
    private Integer id;

    @XmlAttribute(name = "first-name")
    private String firstName;
    @XmlAttribute(name = "last-name")
    private String lastName;
    @XmlAttribute
    private Integer age;
    @XmlElement(name = "sold-products")
    private ProductsSaleDto soldProducts;

    public UserWithProductsDto() {
    }

    public UserWithProductsDto(Integer id, String firstName, String lastName, Integer age, ProductsSaleDto ProductsSaleDto) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.soldProducts = ProductsSaleDto;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public ProductsSaleDto getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(ProductsSaleDto ProductsSaleDto) {
        this.soldProducts = ProductsSaleDto;
    }
}
