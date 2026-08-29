package com.softix.app_back.appointment;

import com.softix.app_back.service_offering.ServiceOffering;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import utils.model.tenant.TenantEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "appointment_service_item")
public class AppointmentServiceItem extends TenantEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @Column(name = "appointment_id", insertable = false, updatable = false)
    private String appointmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_offering_id")
    private ServiceOffering serviceOffering;

    @Column(name = "service_offering_id", insertable = false, updatable = false    )
    private String serviceOfferingId;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @Column(name = "price")
    private Double price;

    @Column(name = "execution_order")
    private Integer executionOrder;

}