package usersystemapp.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "albums")
public class Album extends BaseEntity {
    private String name;
    private String backgroundColor;
    private Boolean isPublic;
    private Set<Picture> pictures;
    private User owner;

    public Album() {
        this.pictures = new HashSet<>();
    }

    @Column
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public String getBackgroundColor() {
        return this.backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Column
    public Boolean isPublic() {
        return this.isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    @ManyToMany(mappedBy = "albums")
    public Set<Picture> getPictures() {
        return this.pictures;
    }

    public void setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
    }

    @ManyToOne(optional = false)
    public User getOwner() {
        return this.owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
