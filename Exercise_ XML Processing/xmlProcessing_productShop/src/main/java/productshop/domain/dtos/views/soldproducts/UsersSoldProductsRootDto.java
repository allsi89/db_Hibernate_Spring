package productshop.domain.dtos.views.soldproducts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersSoldProductsRootDto {
    @XmlElement(name = "user")
    private List<SuccessfulSellerDto> sellers;

    public UsersSoldProductsRootDto() {
        this.sellers = new ArrayList<>();
    }

    public List<SuccessfulSellerDto> getSellers() {
        return sellers;
    }

    public void setSellers(List<SuccessfulSellerDto> sellers) {
        this.sellers = sellers;
    }
}
