package com.softix.app_back.appointment;

import com.softix.app_back.client.Client;
import com.softix.app_back.professional.Professional;
import com.softix.app_back.service_offering.ServiceOffering;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import utils.model.TenantEntity;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "appointment")
public class Appointment extends TenantEntity {

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "client_id", length = 38, insertable = false, updatable = false)
    private String clientId;

    @ManyToOne
    @JoinColumn(name = "professional_id")
    private Professional professional;

    @Column(name = "professional_id", length = 38, insertable = false, updatable = false)
    private String professionalId;

    @ManyToOne
    @JoinColumn(name = "service_offering_id", insertable = false, updatable = false)
    private ServiceOffering serviceOffering;

    @Column(name = "service_offering_id", length = 38, insertable = false, updatable = false)
    private String serviceOfferingId;

    @Column(name = "start_at")
    private LocalDateTime startAt;

    @Column(name = "ent_at")
    private LocalDateTime endAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AppointmentStatus status;

}

