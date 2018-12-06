package entities.hospitalDatabase;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "medicaments")
public class Medicament extends BaseEntity {
    private String name;
    private Set<Diagnose> diagnoses;

    public Medicament() {
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany
    @JoinTable(name = "diagnoses_medications",
            joinColumns = @JoinColumn(name = "medicament_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "diagnose_id", referencedColumnName = "id"))
    public Set<Diagnose> getDiagnoses() {
        return diagnoses;
    }

    public void setDiagnoses(Set<Diagnose> diagnoses) {
        this.diagnoses = diagnoses;
    }
}
