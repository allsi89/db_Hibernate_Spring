package cardealer.domain.entities;


import javax.persistence.*;

@Entity(name = "sales")
public class Sale extends BaseEntity {
    private Double discount;
    private Car car;
    private Customer customer;

    public Sale() {
    }

    @Column
    public Double getDiscount() {
        return discount;
    }

    @OneToOne
    public Car getCar() {
        return car;
    }

    @ManyToOne
    public Customer getCustomer() {
        return customer;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
