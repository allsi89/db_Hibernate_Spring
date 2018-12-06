package entities.salesDatabase;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity(name = "products")
public class Product extends BaseEntity{
    private String name;
    private double quantity;
    private BigDecimal price;
    Set<Sale> sales;

    public Product() {

    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column()
    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    @Column()
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @OneToMany(targetEntity = Sale.class, mappedBy = "product")
    public Set<Sale> getSales() {
        return sales;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }
}
