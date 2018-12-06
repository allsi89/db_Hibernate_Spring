package cardealer.domain.dtos.views.localsuppliers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExportLocalSuppliersDto {
    @XmlElement(name = "supplier")
    List<SupplierViewDto> locals;

    public ExportLocalSuppliersDto() {
        this.locals = new ArrayList<>();
    }

    public List<SupplierViewDto> getLocals() {
        return locals;
    }

    public void setLocals(List<SupplierViewDto> locals) {
        this.locals = locals;
    }
}
