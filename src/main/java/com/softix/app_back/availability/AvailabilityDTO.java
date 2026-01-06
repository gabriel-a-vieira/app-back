package com.softix.app_back.availability;

import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class AvailabilityDTO {

    private String id;
    private String professionalId;
    private String name;
    private DayOfWeek dayWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public AvailabilityDTO() {}

    public AvailabilityDTO(Availability availability) {
        BeanUtils.copyProperties(availability, this);
    }

}
