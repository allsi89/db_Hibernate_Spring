package entities.salesDatabase;

import javax.persistence.*;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseEntity {
    private int id;

    public BaseEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
