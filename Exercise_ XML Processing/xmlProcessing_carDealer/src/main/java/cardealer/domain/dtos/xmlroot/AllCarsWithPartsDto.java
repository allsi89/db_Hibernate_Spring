package cardealer.domain.dtos.xmlroot;

import java.util.ArrayList;
import java.util.List;

public class AllCarsWithPartsDto<T> {
    private List<T> cars;

    public AllCarsWithPartsDto() {
        this.cars = new ArrayList<>();
    }

    public List<T> getCars() {
        return cars;
    }

    public void setCars(List<T> cars) {
        this.cars = cars;
    }
}
