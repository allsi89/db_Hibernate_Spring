package cardealer.domain.dtos;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class SaleDetailsViewDto {
    private CarViewDto car;

    private String customerName;

    @SerializedName("Discount")
    private Double discount;

    private BigDecimal price;

    private BigDecimal priceWithDiscount;

    public SaleDetailsViewDto() {
    }

    public CarViewDto getCar() {
        return car;
    }

    public void setCar(CarViewDto car) {
        this.car = car;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public void setPriceWithDiscount(BigDecimal priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }
}
