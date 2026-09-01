package com.softix.app_back.company.public_api;

import java.time.LocalTime;

public record PublicCompanyTimeRangeDTO(

        LocalTime startTime,
        LocalTime endTime

) {
}