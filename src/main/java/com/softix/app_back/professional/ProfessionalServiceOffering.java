package com.softix.app_back.professional;

import com.softix.app_back.service_offering.ServiceOffering;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import utils.model.tenant.TenantEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "professional_service_offering")
public class ProfessionalServiceOffering extends TenantEntity {

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "professinal_id")
    private Professional professional;

    @Column(name = "professinal_id", insertable = false, updatable = false)
    private String professionalId;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "service_offering_id")
    private ServiceOffering serviceOffering;

    @Column(name = "service_offering_id", insertable = false, updatable = false)
    private String serviceOfferingId;

}

