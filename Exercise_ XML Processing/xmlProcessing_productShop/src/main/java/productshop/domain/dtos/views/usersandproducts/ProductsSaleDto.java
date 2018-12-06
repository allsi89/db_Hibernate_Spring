package productshop.domain.dtos.views.usersandproducts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@XmlAccessorType(XmlAccessType.FIELD)
public class ProductsSaleDto implements Serializable {
    @XmlAttribute(name = "count")
    private Integer count;

    @XmlElement(name = "product")
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
