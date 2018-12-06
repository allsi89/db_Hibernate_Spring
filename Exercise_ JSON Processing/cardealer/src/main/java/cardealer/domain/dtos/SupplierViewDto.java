package cardealer.domain.dtos;

import com.google.gson.annotations.SerializedName;

public class SupplierViewDto {
    @SerializedName("Id")
    private Long id;

    @SerializedName("Name")
    private String name;

    private Long partsCount;

    public SupplierViewDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
