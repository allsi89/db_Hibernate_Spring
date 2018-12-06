package entities.billsPaymentSystem;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {
    private String id;

    public BaseEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
