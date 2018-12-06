package cardealer.domain.dtos;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class PartViewDto {

    @SerializedName("Name")
    private String name;

    @SerializedName("Price")
    private BigDecimal price;

    public PartViewDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
