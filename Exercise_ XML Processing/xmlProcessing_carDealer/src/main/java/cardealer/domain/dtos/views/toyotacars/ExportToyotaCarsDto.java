package cardealer.domain.dtos.views.toyotacars;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExportToyotaCarsDto<T> {
    @XmlElement(name = "car")
    private List<CarViewDetailedDto> cars;

    public ExportToyotaCarsDto() {
        this.cars = new ArrayList<>();
    }

    public List<CarViewDetailedDto> getCars() {
        return cars;
    }

    public void setCars(List<CarViewDetailedDto> cars) {
        this.cars = cars;
    }

}
