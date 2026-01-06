package com.softix.app_back.availability;

import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

@Data
public class AvailabilityDTO {

    private UUID id;
    private UUID professionalId;
    private String name;
    private DayOfWeek dayWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public AvailabilityDTO() {}

    public AvailabilityDTO(Availability availability) {
        BeanUtils.copyProperties(availability, this);
    }

}
