package com.softix.app_back.professional;

import com.softix.app_back.person.Person;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import utils.model.tenant.TenantEntity;

import static com.softix.app_back.professional.ProfessionalStatus.ACTIVE;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "professional")
public class Professional extends TenantEntity {

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @Column(name = "person_id", insertable = false, updatable = false)
    private String personId;

    @Column(name = "status")
    private ProfessionalStatus status = ACTIVE;

    @Column(name = "user_id", length = 38)
    private String userId;

}

