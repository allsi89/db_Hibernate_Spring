package usersystemapp.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "countries")
public class Country extends BaseEntity {
    private String name;
    private Set<Town> towns;

    public Country() {
        this.towns = new HashSet<>();
    }

    @Column(unique = true, nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @OneToMany()
    @JoinColumn(referencedColumnName = "id")
    public Set<Town> getTowns() {
        return this.towns;
    }

    public void setTowns(final Set<Town> towns) {
        this.towns = towns;
    }
}
