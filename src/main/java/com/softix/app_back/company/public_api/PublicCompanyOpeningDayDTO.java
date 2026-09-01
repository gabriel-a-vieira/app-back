package com.softix.app_back.company.public_api;

import java.time.DayOfWeek;
import java.util.List;

public record PublicCompanyOpeningDayDTO(

        DayOfWeek dayWeek,
        List<PublicCompanyTimeRangeDTO> intervals

) {
}