package com.softix.app_back.availability;

import com.softix.app_back.professional.ProfessionalRepository;
import com.softix.app_back.shared.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Covers the invariants AppointmentService's slot calculation depends on:
 * an availability window must have start strictly before end, and two
 * windows for the same professional/day can't overlap. If either check
 * regresses, bogus availabilities silently corrupt every downstream
 * booking/slot calculation.
 */
@ExtendWith(MockitoExtension.class)
class AvailabilityServiceTest {

    @Mock
    private AvailabilityRepository availabilityRepository;

    @Mock
    private ProfessionalRepository professionalRepository;

    @InjectMocks
    private AvailabilityService availabilityService;

    private static final String PROFESSIONAL_ID = "prof-1";
    private static final String COMPANY_ID = "company-1";
    private static final String AVAILABILITY_ID = "avail-1";

    private AvailabilityDTO buildDto(LocalTime start, LocalTime end) {

        AvailabilityDTO dto = new AvailabilityDTO();
        dto.setCompanyId(COMPANY_ID);
        dto.setProfessionalId(PROFESSIONAL_ID);
        dto.setDayWeek(DayOfWeek.MONDAY);
        dto.setStartTime(start);
        dto.setEndTime(end);

        return dto;

    }

    @Test
    void save_throwsWhenStartTimeIsNotBeforeEndTime() {

        AvailabilityDTO dto = buildDto(LocalTime.of(10, 0), LocalTime.of(10, 0));

        when(professionalRepository.existsByIdAndCompanyId(PROFESSIONAL_ID, COMPANY_ID)).thenReturn(true);

        assertThatThrownBy(() -> availabilityService.save(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("anterior");
    }

    @Test
    void save_throwsWhenStartTimeIsAfterEndTime() {

        AvailabilityDTO dto = buildDto(LocalTime.of(18, 0), LocalTime.of(9, 0));

        when(professionalRepository.existsByIdAndCompanyId(PROFESSIONAL_ID, COMPANY_ID)).thenReturn(true);

        assertThatThrownBy(() -> availabilityService.save(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("anterior");
    }

    @Test
    void save_throwsWhenOverlappingAvailabilityExists() {

        AvailabilityDTO dto = buildDto(LocalTime.of(9, 0), LocalTime.of(10, 0));

        when(professionalRepository.existsByIdAndCompanyId(PROFESSIONAL_ID, COMPANY_ID)).thenReturn(true);
        when(availabilityRepository.existsOverlappingAvailability(eq(COMPANY_ID), eq(PROFESSIONAL_ID), eq(DayOfWeek.MONDAY), eq(LocalTime.of(9, 0)), eq(LocalTime.of(10, 0)), isNull()))
                .thenReturn(true);

        assertThatThrownBy(() -> availabilityService.save(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("conflita");
    }

    @Test
    void save_persistsWhenValid() {

        AvailabilityDTO dto = buildDto(LocalTime.of(9, 0), LocalTime.of(10, 0));

        when(professionalRepository.existsByIdAndCompanyId(PROFESSIONAL_ID, COMPANY_ID)).thenReturn(true);
        when(availabilityRepository.existsOverlappingAvailability(any(), any(), any(), any(), any(), any())).thenReturn(false);

        AvailabilityDTO result = availabilityService.save(dto);

        assertThat(result.getStartTime()).isEqualTo(LocalTime.of(9, 0));
        assertThat(result.getEndTime()).isEqualTo(LocalTime.of(10, 0));

        ArgumentCaptor<Availability> captor = ArgumentCaptor.forClass(Availability.class);
        verify(availabilityRepository).save(captor.capture());
        assertThat(captor.getValue().getCompanyId()).isEqualTo(COMPANY_ID);
    }

    @Test
    void update_ignoresItsOwnRecordWhenCheckingOverlap() {

        AvailabilityDTO dto = buildDto(LocalTime.of(9, 0), LocalTime.of(10, 0));

        Availability existing = new Availability();
        existing.setId(AVAILABILITY_ID);

        when(availabilityRepository.findById(AVAILABILITY_ID)).thenReturn(java.util.Optional.of(existing));
        when(professionalRepository.existsByIdAndCompanyId(PROFESSIONAL_ID, COMPANY_ID)).thenReturn(true);
        when(availabilityRepository.existsOverlappingAvailability(any(), any(), any(), any(), any(), any())).thenReturn(false);

        availabilityService.update(AVAILABILITY_ID, dto);

        verify(availabilityRepository).existsOverlappingAvailability(COMPANY_ID, PROFESSIONAL_ID, DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 0), AVAILABILITY_ID);
    }

}
