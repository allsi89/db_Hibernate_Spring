package cardealer.domain.dtos;

public class SaleViewDto {
    private Double discount;
    private CarViewDto car;

    public SaleViewDto() {
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public CarViewDto getCar() {
        return car;
    }

    public void setCar(CarViewDto car) {
        this.car = car;
    }
}
