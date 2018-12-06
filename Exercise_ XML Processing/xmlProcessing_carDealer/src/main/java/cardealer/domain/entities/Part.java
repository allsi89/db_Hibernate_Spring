package cardealer.domain.entities;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "parts")
public class Part extends BaseEntity {
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private Supplier supplier;
    private List<Car> cars;

    public Part() {
        this.cars = new ArrayList<>();
    }

    @Column
    public String getName() {
        return name;
    }

    @Column
    public BigDecimal getPrice() {
        return price;
    }

    @Column
    public Integer getQuantity() {
        return quantity;
    }

    @ManyToOne
    public Supplier getSupplier() {
        return supplier;
    }


    @ManyToMany(targetEntity = Car.class, mappedBy = "parts")
    public List<Car> getCars() {
        return cars;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
