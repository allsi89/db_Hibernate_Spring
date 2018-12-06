package cardealer.domain.dtos.views.localsuppliers;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "supplier")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierViewDto {
    @XmlAttribute
    private Integer id;
    @XmlAttribute
    private String name;
    @XmlTransient
    private Boolean isImporter;
    @XmlAttribute(name = "parts-count")
    private Long partsCount;

    public SupplierViewDto() {
    }

    public SupplierViewDto(Integer id, String name, Boolean isImporter, Long partsCount) {
        this.id = id;
        this.name = name;
        this.isImporter = isImporter;
        this.partsCount = partsCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPartsCount() {
        return partsCount;
    }

    public void setPartsCount(Long partsCount) {
        this.partsCount = partsCount;
    }

    public Boolean isImporter() {
        return isImporter;
    }

    public void setImporter(boolean importer) {
        isImporter = importer;
    }
}
