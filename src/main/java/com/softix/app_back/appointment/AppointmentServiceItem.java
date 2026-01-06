package com.softix.app_back.appointment;

import com.softix.app_back.service_offering.ServiceOffering;
import jakarta.persistence.*;
import lombok.Data;
import utils.model.TenantEntity;

@Data
@Entity
@Table(name = "appointment_service_item")
public class AppointmentServiceItem extends TenantEntity {

    @ManyToOne
    @JoinColumn(name = "appointment_id", insertable = false, updatable = false)
    private Appointment appointment;

    @Column(name = "appointment_id")
    private String appointmentId;

    @ManyToOne
    @JoinColumn(name = "service_offering_id", insertable = false, updatable = false)
    private ServiceOffering serviceOffering;

    @Column(name = "service_offering_id")
    private String serviceOfferingId;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @Column(name = "price")
    private Double price;

    @Column(name = "execution_order")
    private Integer executionOrder;
}

