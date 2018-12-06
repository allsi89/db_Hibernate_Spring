package cardealer.domain.dtos;

import java.util.List;

public class CarPartsViewDto {
    private CarViewDto car;
    private List<PartViewDto> parts;

    public CarPartsViewDto() {
    }

    public CarViewDto getCar() {
        return car;
    }

    public void setCar(CarViewDto car) {
        this.car = car;
    }

    public List<PartViewDto> getParts() {
        return parts;
    }

    public void setParts(List<PartViewDto> parts) {
        this.parts = parts;
    }
}
