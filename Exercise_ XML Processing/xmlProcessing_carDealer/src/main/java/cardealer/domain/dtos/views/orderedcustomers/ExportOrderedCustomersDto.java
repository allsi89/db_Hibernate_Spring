package cardealer.domain.dtos.views.orderedcustomers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExportOrderedCustomersDto {
    @XmlElement(name = "customer")
    private List<CustomerByBirthdayDto> customers;

    public ExportOrderedCustomersDto() {
        this.customers = new ArrayList<>();
    }

    public List<CustomerByBirthdayDto> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerByBirthdayDto> customers) {
        this.customers = customers;
    }
}
