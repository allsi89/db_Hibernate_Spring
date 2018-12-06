package cardealer.domain.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity(name = "customers")
public class Customer extends BaseEntity {
    private String name;
    private Date birthDate;
    private Boolean isYoungDriver;
    private List<Sale> purchases;

    public Customer() {
    }

    @Column
    public String getName() {
        return name;
    }

    @Column(name = "birth_date")
    public Date getBirthDate() {
        return birthDate;
    }

    @Column(name = "is_young_driver")
    public Boolean getYoungDriver() {
        return isYoungDriver;
    }

    @OneToMany(mappedBy = "customer")
    public List<Sale> getPurchases() {
        return purchases;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setYoungDriver(Boolean youngDriver) {
        isYoungDriver = youngDriver;
    }

    public void setPurchases(List<Sale> purchases) {
        this.purchases = purchases;
    }
}
