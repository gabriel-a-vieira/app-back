package com.softix.app_back.company;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class CompanyOpeningHourRequest {

    private DayOfWeek dayWeek;
    private LocalTime startTime;
    private LocalTime endTime;

}