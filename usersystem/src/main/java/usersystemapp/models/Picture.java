package usersystemapp.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "pictures")
public class Picture extends BaseEntity{
    private String title;
    private String caption;
    private String pathOnFileSystem;
    private Set<Album> albums;

    public Picture() {
        this.albums = new HashSet<>();
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    @Column
    public String getCaption() {
        return this.caption;
    }

    public void setCaption(final String caption) {
        this.caption = caption;
    }

    @Column(name = "path_on_file_system")
    public String getPathOnFileSystem() {
        return this.pathOnFileSystem;
    }

    public void setPathOnFileSystem(final String pathOnFileSystem) {
        this.pathOnFileSystem = pathOnFileSystem;
    }

    @ManyToMany()
    public Set<Album> getAlbums() {
        return this.albums;
    }

    public void setAlbums(final Set<Album> albums) {
        this.albums = albums;
    }

}
