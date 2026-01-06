package com.softix.app_back.availability;

import com.softix.app_back.professional.Professional;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import utils.model.TenantEntity;

import java.time.DayOfWeek;
import java.time.LocalTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "availability")
public class Availability extends TenantEntity {

    @ManyToOne
    @JoinColumn(name = "professional_id", insertable = false, updatable = false)
    private Professional professional;

    @Column(name = "professional_id", length = 38)
    private String professionalId;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_week")
    private DayOfWeek dayWeek;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;
}

