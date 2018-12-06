package cardealer.domain.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "cars")
public class Car extends BaseEntity {
    private String make;
    private String model;
    private Long travelledDistance;
    private List<Part> parts;

    public Car() {
        this.parts = new ArrayList<>();
    }

    @Column
    public String getMake() {
        return make;
    }

    @Column
    public String getModel() {
        return model;
    }

    @Column(name = "travelled_distance")
    public Long getTravelledDistance() {
        return travelledDistance;
    }

    @ManyToMany()
    @JoinTable(name = "parts_cars",
            joinColumns = {@JoinColumn(name = "car_id")},
            inverseJoinColumns = {@JoinColumn(name = "part_id")})
    public List<Part> getParts() {
        return parts;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setTravelledDistance(Long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }
}
