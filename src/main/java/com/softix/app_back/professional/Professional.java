package com.softix.app_back.professional;

import com.softix.app_back.person.Person;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import utils.model.TenantEntity;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "professional")
public class Professional extends TenantEntity {

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @Column(name = "person_id", insertable = false, updatable = false)
    private UUID personId;

    @Column(name = "status")
    private ProfessionalStatus status;

}

