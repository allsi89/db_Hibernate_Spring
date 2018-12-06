package cardealer.domain.entities;


import cardealer.domain.entities.BaseEntity;

import javax.persistence.*;

@Entity(name = "suppliers")
public class Supplier extends BaseEntity {
    private String name;
    private Boolean isImporter;

    public Supplier() {
    }


    @Column
    public String getName() {
        return name;
    }

    @Column(name = "is_importer")
    public Boolean getImporter() {
        return isImporter;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImporter(Boolean importer) {
        isImporter = importer;
    }
}
