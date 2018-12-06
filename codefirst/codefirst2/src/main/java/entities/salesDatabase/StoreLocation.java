package entities.salesDatabase;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "store_locations")
public class StoreLocation extends BaseEntity {
    private String locationName;
    private Set<Sale> sales;

    public StoreLocation() {
    }

    @Column(name = "location_name", nullable = false)
    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    @OneToMany(targetEntity = Sale.class, mappedBy = "storeLocation")
    public Set<Sale> getSales() {
        return sales;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }
}
