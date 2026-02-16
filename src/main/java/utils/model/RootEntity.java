package utils.model;

import jakarta.persistence.*;
import lombok.Data;
import utils.security.SecurityUtils;

import java.util.Date;
import java.util.UUID;

@Data
@MappedSuperclass
public class RootEntity {

    @Id
    @Column(name = "id", length = 38, nullable = false)
    private String id;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "created_by_user_id", length = 38) //TODO After implement the User table, increase (nullable = false) here
    private String createdByUserId;

    @Column(name = "updated_by_id", length = 38)
    private String updatedById;

    @Version
    @Column(name = "version")
    private Integer version;

    @PrePersist
    protected void prePersistRoot() {

        if (id == null) {
            id = String.valueOf(UUID.randomUUID());
        }

        if (getCreatedAt() == null) {
            setCreatedAt(new Date());
        }

        if (SecurityUtils.currentUser() != null) {
            createdByUserId = SecurityUtils.userId();
        }

    }

}