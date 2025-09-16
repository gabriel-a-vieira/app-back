package utils.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@MappedSuperclass
public class RootEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 38, nullable = false)
    private UUID id;

    @Column(name = "company_id", length = 38)
    private UUID companyId;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "created_by", length = 38) //TODO After implement the User table, increase (nullable = false) here
    private UUID createdBy;

    @Column(name = "updated_by", length = 38)
    private UUID updatedBy;

    @Version
    @Column(name = "version")
    private Integer version;

    @PrePersist
    public void prePersist() {

        if (id == null) {
            id = UUID.randomUUID();
        }

        if (getCreatedAt() == null) {
            setCreatedAt(new Date());
        }

        //TODO Implement user creation field logic when the table is created

    }
}