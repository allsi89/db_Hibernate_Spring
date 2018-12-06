package entities.hospitalDatabase;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "diagnoses")
public class Diagnose extends BaseEntity {
    private String name;
    private String comments;
    private Set<Patient> patients;
    private Set<Medicament> medicaments;

    public Diagnose() {
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @ManyToMany
    @JoinTable(name = "patients_diagnoses",
            joinColumns = @JoinColumn(name = "diagnose_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"))
    public Set<Patient> getPatients() {
        return patients;
    }

    public void setPatients(Set<Patient> patients) {
        this.patients = patients;
    }

    @ManyToMany
    @JoinTable(name = "diagnoses_medications",
    joinColumns = @JoinColumn(name = "diagnose_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "medicament_id", referencedColumnName = "id"))
    public Set<Medicament> getMedicaments() {
        return medicaments;
    }

    public void setMedicaments(Set<Medicament> medicaments) {
        this.medicaments = medicaments;
    }
}
