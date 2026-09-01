package com.softix.app_back.company;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class CompanyOpeningHour {

    @Enumerated(EnumType.STRING)
    @Column(name = "day_week", nullable = false)
    private DayOfWeek dayWeek;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

}