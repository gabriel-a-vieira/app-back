package com.softix.app_back.service_offering;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import utils.model.tenant.TenantEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "service_offering")
public class ServiceOffering extends TenantEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    @Column(name = "price", nullable = false)
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ServiceOfferingStatus status = ServiceOfferingStatus.ACTIVE;

}