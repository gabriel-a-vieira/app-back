package com.softix.app_back.professional;

import com.softix.app_back.service_offering.ServiceOffering;
import jakarta.persistence.*;
import lombok.Data;
import utils.model.tenant.TenantEntity;

@Data
@Entity
@Table(name = "professional_service_offering")
public class ProfessionalServiceOffering extends TenantEntity {

    @ManyToOne
    @JoinColumn(name = "professinal_id")
    private Professional professional;

    @Column(name = "professinal_id", insertable = false, updatable = false)
    private String professionalId;

    @ManyToOne
    @JoinColumn(name = "service_offering_id")
    private ServiceOffering serviceOffering;

    @Column(name = "service_offering_id", insertable = false, updatable = false)
    private String serviceOfferingId;

}

