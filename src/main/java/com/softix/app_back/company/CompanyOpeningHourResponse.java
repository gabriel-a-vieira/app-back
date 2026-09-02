package com.softix.app_back.company;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record CompanyOpeningHourResponse(
        DayOfWeek dayWeek,
        LocalTime startTime,
        LocalTime endTime
) {

    public static CompanyOpeningHourResponse fromEntity(CompanyOpeningHour openingHour) {

        return new CompanyOpeningHourResponse(
                openingHour.getDayWeek(),
                openingHour.getStartTime(),
                openingHour.getEndTime()
        );

    }

}