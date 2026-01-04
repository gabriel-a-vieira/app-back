package com.softix.app_back.service_offering;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import utils.model.TenantEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "service_offering")
public class ServiceOffering extends TenantEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @Column(name = "price")
    private Double price;

}

