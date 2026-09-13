package com.softix.app_back.appointment;

import com.softix.app_back.availability.Availability;
import com.softix.app_back.availability.AvailabilityRepository;
import com.softix.app_back.professional.Professional;
import com.softix.app_back.professional.ProfessionalRepository;
import com.softix.app_back.service_offering.ServiceOffering;
import com.softix.app_back.service_offering.ServiceOfferingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * Covers AppointmentService#findAvailableSlots, the slot-computation logic
 * behind booking (availability window minus already-booked appointments,
 * sliced into 15-minute candidates that fit the requested services'
 * combined duration). This is exactly the kind of date/time arithmetic
 * that tends to break silently, and the codebase had no test coverage for
 * it yet.
 */
@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private ProfessionalRepository professionalRepository;

    @Mock
    private ServiceOfferingRepository serviceOfferingRepository;

    @Mock
    private AvailabilityRepository availabilityRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    private static final String PROFESSIONAL_ID = "prof-1";
    private static final String SERVICE_ID = "svc-1";
    private static final String COMPANY_ID = "company-1";

    @Test
    void returnsQuarterHourSlotsThatFitWithinTheAvailabilityWindow() {
        LocalDate date = LocalDate.now().plusDays(30);
        DayOfWeek dayOfWeek = date.getDayOfWeek();

        Professional professional = new Professional();
        professional.setId(PROFESSIONAL_ID);

        ServiceOffering service = new ServiceOffering();
        service.setId(SERVICE_ID);
        service.setDurationMinutes(30);

        Availability availability = new Availability();
        availability.setProfessionalId(PROFESSIONAL_ID);
        availability.setDayWeek(dayOfWeek);
        availability.setStartTime(LocalTime.of(9, 0));
        availability.setEndTime(LocalTime.of(10, 0));

        when(professionalRepository.findByIdAndCompanyId(PROFESSIONAL_ID, COMPANY_ID))
                .thenReturn(Optional.of(professional));
        when(serviceOfferingRepository.findByIdInAndCompanyId(List.of(SERVICE_ID), COMPANY_ID))
                .thenReturn(List.of(service));
        when(availabilityRepository.findByProfessionalIdAndDayWeekAndCompanyIdOrderByStartTimeAsc(
                PROFESSIONAL_ID, dayOfWeek, COMPANY_ID))
                .thenReturn(List.of(availability));
        when(appointmentRepository.findByCompanyIdAndProfessionalIdAndStatusInAndStartAtLessThanAndEndAtGreaterThan(
                eq(COMPANY_ID), eq(PROFESSIONAL_ID), any(), any(), any()))
                .thenReturn(List.of());

        List<String> slots = appointmentService.findAvailableSlots(
                PROFESSIONAL_ID, date, List.of(SERVICE_ID), COMPANY_ID, null);

        assertThat(slots).containsExactly("09:00", "09:15", "09:30");
    }
}
