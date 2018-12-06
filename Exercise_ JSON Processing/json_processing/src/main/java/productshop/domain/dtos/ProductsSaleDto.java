package productshop.domain.dtos;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ProductsSaleDto implements Serializable {
    @Expose
    private Integer count;
    @Expose
    private Set<ProductNameAndPriceDto> products;

    public ProductsSaleDto() {
        this.products = new HashSet<>();
    }

    public ProductsSaleDto(Integer count, Set<ProductNameAndPriceDto> soldProducts) {
        this.count = count;
        this.products = soldProducts;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Set<ProductNameAndPriceDto> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductNameAndPriceDto> products) {
        this.products = products;
    }
}
