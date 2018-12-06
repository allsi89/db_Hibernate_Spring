package cardealer.domain.dtos.xmlroot;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderedCustomersDto<T> {
    @XmlElement(name = "customer")
    private List<T> customers;

    public OrderedCustomersDto() {
        this.customers = new ArrayList<>();
    }

    public List<T> getCustomers() {
        return customers;
    }

    public void setCustomers(List<T> customers) {
        this.customers = customers;
    }
}
