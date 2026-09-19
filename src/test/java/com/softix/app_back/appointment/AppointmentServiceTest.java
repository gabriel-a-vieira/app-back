package com.softix.app_back.appointment;

import com.softix.app_back.availability.Availability;
import com.softix.app_back.availability.AvailabilityRepository;
import com.softix.app_back.client.Client;
import com.softix.app_back.client.ClientRepository;
import com.softix.app_back.company.CompanyRepository;
import com.softix.app_back.notification.NotificationEvent;
import com.softix.app_back.notification.NotificationEventType;
import com.softix.app_back.notification.NotificationService;
import com.softix.app_back.person.Person;
import com.softix.app_back.professional.Professional;
import com.softix.app_back.professional.ProfessionalRepository;
import com.softix.app_back.service_offering.ServiceOffering;
import com.softix.app_back.service_offering.ServiceOfferingRepository;
import com.softix.app_back.shared.exception.BusinessException;
import com.softix.app_back.user.User;
import com.softix.app_back.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import com.softix.app_back.config.JWTUserData;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Covers AppointmentService's booking invariants: slot computation
 * (findAvailableSlots), the validations that guard save/update (no
 * past-dated bookings, no crossing midnight, must fit the professional's
 * availability, no double-booking), and the company-scoping/status rules
 * in cancelMany. This is exactly the kind of date/time arithmetic and
 * multi-tenant scoping that tends to break silently, and the codebase had
 * little to no test coverage for it.
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

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private AppointmentServiceItemRepository appointmentServiceItemRepository;

    @Mock
    private AppointmentMapper appointmentMapper;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private AppointmentService appointmentService;

    private static final String PROFESSIONAL_ID = "prof-1";
    private static final String SERVICE_ID = "svc-1";
    private static final String CLIENT_ID = "client-1";
    private static final String COMPANY_ID = "company-1";
    private static final String OTHER_COMPANY_ID = "company-2";
    private static final String APPOINTMENT_ID = "appt-1";

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    private void authenticateAs(String role, String companyId) {
        JWTUserData user = JWTUserData.builder().userId("user-1").companyId(companyId).role(role).email("user@softix.com").build();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null));
    }

    /** Builds a valid, in-window request: 30-minute service, 09:00-17:00 availability, no conflicts. */
    private AppointmentDTO buildValidDto(LocalDateTime startAt) {
        Client client = new Client();
        client.setId(CLIENT_ID);
        return buildValidDto(startAt, client);
    }

    /** Same as above, but with a caller-supplied client (e.g. one linked to a User, for notification tests). */
    private AppointmentDTO buildValidDto(LocalDateTime startAt, Client client) {

        Professional professional = new Professional();
        professional.setId(PROFESSIONAL_ID);

        ServiceOffering service = new ServiceOffering();
        service.setId(SERVICE_ID);
        service.setDurationMinutes(30);

        Availability availability = new Availability();
        availability.setProfessionalId(PROFESSIONAL_ID);
        availability.setDayWeek(startAt.getDayOfWeek());
        availability.setStartTime(LocalTime.of(9, 0));
        availability.setEndTime(LocalTime.of(17, 0));

        when(professionalRepository.findByIdAndCompanyId(PROFESSIONAL_ID, COMPANY_ID)).thenReturn(Optional.of(professional));
        when(clientRepository.findByIdAndCompanyId(CLIENT_ID, COMPANY_ID)).thenReturn(Optional.of(client));
        when(serviceOfferingRepository.findByIdInAndCompanyId(List.of(SERVICE_ID), COMPANY_ID)).thenReturn(List.of(service));
        // lenient: not every scenario built from this helper reaches the availability check
        // (e.g. it throws earlier on a past date, or skips it entirely for non-blocking statuses)
        org.mockito.Mockito.lenient().when(availabilityRepository.findByProfessionalIdAndDayWeekAndCompanyIdOrderByStartTimeAsc(PROFESSIONAL_ID, startAt.getDayOfWeek(), COMPANY_ID))
                .thenReturn(List.of(availability));

        AppointmentDTO dto = new AppointmentDTO();
        dto.setCompanyId(COMPANY_ID);
        dto.setClientId(CLIENT_ID);
        dto.setProfessionalId(PROFESSIONAL_ID);
        dto.setServiceIds(List.of(SERVICE_ID));
        dto.setStartAt(startAt);

        return dto;

    }

    @Test
    void save_throwsWhenStartAtIsInThePast() {

        LocalDateTime pastStart = LocalDateTime.now().minusDays(1);
        AppointmentDTO dto = buildValidDto(pastStart);

        assertThatThrownBy(() -> appointmentService.save(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("passado");
    }

    @Test
    void save_throwsWhenAppointmentCrossesMidnight() {

        LocalDate date = LocalDate.now().plusDays(30);
        AppointmentDTO dto = buildValidDto(date.atTime(23, 50));

        assertThatThrownBy(() -> appointmentService.save(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("final do dia");
    }

    @Test
    void save_throwsWhenOutsideProfessionalAvailability() {

        LocalDate date = LocalDate.now().plusDays(30);
        AppointmentDTO dto = buildValidDto(date.atTime(8, 0));

        assertThatThrownBy(() -> appointmentService.save(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("disponibilidade");
    }

    @Test
    void save_throwsWhenConflictingAppointmentExists() {

        LocalDate date = LocalDate.now().plusDays(30);
        AppointmentDTO dto = buildValidDto(date.atTime(10, 0));

        when(appointmentRepository.existsConflict(eq(COMPANY_ID), eq(PROFESSIONAL_ID), any(), any(), any(), any())).thenReturn(true);

        assertThatThrownBy(() -> appointmentService.save(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("ocupado");
    }

    @Test
    void save_persistsScheduledAppointmentWhenValid() {

        LocalDate date = LocalDate.now().plusDays(30);
        LocalDateTime startAt = date.atTime(10, 0);
        AppointmentDTO dto = buildValidDto(startAt);

        when(appointmentRepository.existsConflict(eq(COMPANY_ID), eq(PROFESSIONAL_ID), any(), any(), any(), any())).thenReturn(false);
        when(appointmentMapper.toDTO(any())).thenReturn(new AppointmentDTO());

        appointmentService.save(dto);

        ArgumentCaptor<Appointment> captor = ArgumentCaptor.forClass(Appointment.class);
        verify(appointmentRepository).save(captor.capture());

        Appointment saved = captor.getValue();
        assertThat(saved.getStatus()).isEqualTo(AppointmentStatus.SCHEDULED);
        assertThat(saved.getStartAt()).isEqualTo(startAt);
        assertThat(saved.getEndAt()).isEqualTo(startAt.plusMinutes(30));
        assertThat(saved.getCompanyId()).isEqualTo(COMPANY_ID);

        // The plain client from buildValidDto has no linked User -- nothing to notify.
        verify(notificationService, never()).notify(any());
    }

    @Test
    void save_notifiesTheLinkedUserByEmailWhenAppointmentIsCreated() {

        LocalDate date = LocalDate.now().plusDays(30);
        LocalDateTime startAt = date.atTime(10, 0);

        Person clientPerson = new Person();
        clientPerson.setName("Maria Cliente");

        Client client = new Client();
        client.setId(CLIENT_ID);
        client.setUserId("user-1");
        client.setPerson(clientPerson);

        AppointmentDTO dto = buildValidDto(startAt, client);

        User user = new User();
        user.setId("user-1");
        user.setEmail("maria@teste.com");
        user.setName("Maria Cliente");

        when(userRepository.findById("user-1")).thenReturn(Optional.of(user));
        when(appointmentRepository.existsConflict(eq(COMPANY_ID), eq(PROFESSIONAL_ID), any(), any(), any(), any())).thenReturn(false);
        when(appointmentMapper.toDTO(any())).thenReturn(new AppointmentDTO());

        appointmentService.save(dto);

        ArgumentCaptor<NotificationEvent> captor = ArgumentCaptor.forClass(NotificationEvent.class);
        verify(notificationService).notify(captor.capture());

        NotificationEvent event = captor.getValue();
        assertThat(event.getType()).isEqualTo(NotificationEventType.APPOINTMENT_CREATED);
        assertThat(event.getRecipientEmail()).isEqualTo("maria@teste.com");
        assertThat(event.getRecipientName()).isEqualTo("Maria Cliente");
        assertThat(event.getAppointmentStartAt()).isEqualTo(startAt);
    }

    @Test
    void save_skipsNotificationWhenLinkedUserHasNoEmail() {

        LocalDate date = LocalDate.now().plusDays(30);
        LocalDateTime startAt = date.atTime(10, 0);

        Client client = new Client();
        client.setId(CLIENT_ID);
        client.setUserId("user-1");

        AppointmentDTO dto = buildValidDto(startAt, client);

        User userWithoutEmail = new User();
        userWithoutEmail.setId("user-1");

        when(userRepository.findById("user-1")).thenReturn(Optional.of(userWithoutEmail));
        when(appointmentRepository.existsConflict(eq(COMPANY_ID), eq(PROFESSIONAL_ID), any(), any(), any(), any())).thenReturn(false);
        when(appointmentMapper.toDTO(any())).thenReturn(new AppointmentDTO());

        appointmentService.save(dto);

        verify(notificationService, never()).notify(any());
    }

    @Test
    void update_skipsAvailabilityAndConflictValidationWhenNewStatusIsNotBlocking() {

        LocalDate date = LocalDate.now().plusDays(30);
        LocalDateTime outOfWindowStart = date.atTime(8, 0);
        AppointmentDTO dto = buildValidDto(outOfWindowStart);
        dto.setStatus(AppointmentStatus.CANCELLED);

        Appointment existing = new Appointment();
        existing.setId(APPOINTMENT_ID);
        existing.setCompanyId(COMPANY_ID);
        existing.setStatus(AppointmentStatus.SCHEDULED);

        when(appointmentRepository.findById(APPOINTMENT_ID)).thenReturn(Optional.of(existing));
        when(appointmentMapper.toDTO(any())).thenReturn(new AppointmentDTO());

        appointmentService.update(APPOINTMENT_ID, dto);

        assertThat(existing.getStatus()).isEqualTo(AppointmentStatus.CANCELLED);
        verify(appointmentRepository, org.mockito.Mockito.never()).existsConflict(any(), any(), any(), any(), any(), any());
    }

    @Test
    void update_validatesConflictWhenNewStatusIsBlocking() {

        LocalDate date = LocalDate.now().plusDays(30);
        LocalDateTime startAt = date.atTime(10, 0);
        AppointmentDTO dto = buildValidDto(startAt);
        dto.setStatus(AppointmentStatus.CONFIRMED);

        Appointment existing = new Appointment();
        existing.setId(APPOINTMENT_ID);
        existing.setCompanyId(COMPANY_ID);
        existing.setStatus(AppointmentStatus.SCHEDULED);

        when(appointmentRepository.findById(APPOINTMENT_ID)).thenReturn(Optional.of(existing));
        when(appointmentRepository.existsConflict(eq(COMPANY_ID), eq(PROFESSIONAL_ID), any(), any(), any(), eq(APPOINTMENT_ID))).thenReturn(true);

        assertThatThrownBy(() -> appointmentService.update(APPOINTMENT_ID, dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("ocupado");
    }

    @Test
    void update_notifiesRescheduleWhenStartAtChanges() {

        LocalDate date = LocalDate.now().plusDays(30);
        LocalDateTime newStartAt = date.atTime(10, 0);
        LocalDateTime previousStartAt = date.atTime(9, 0);

        Person clientPerson = new Person();
        clientPerson.setName("Maria Cliente");

        Client client = new Client();
        client.setId(CLIENT_ID);
        client.setUserId("user-1");
        client.setPerson(clientPerson);

        AppointmentDTO dto = buildValidDto(newStartAt, client);
        dto.setStatus(AppointmentStatus.SCHEDULED);

        Appointment existing = new Appointment();
        existing.setId(APPOINTMENT_ID);
        existing.setCompanyId(COMPANY_ID);
        existing.setStatus(AppointmentStatus.SCHEDULED);
        existing.setStartAt(previousStartAt);

        User user = new User();
        user.setId("user-1");
        user.setEmail("maria@teste.com");
        user.setName("Maria Cliente");

        when(appointmentRepository.findById(APPOINTMENT_ID)).thenReturn(Optional.of(existing));
        when(appointmentRepository.existsConflict(eq(COMPANY_ID), eq(PROFESSIONAL_ID), any(), any(), any(), eq(APPOINTMENT_ID))).thenReturn(false);
        when(userRepository.findById("user-1")).thenReturn(Optional.of(user));
        when(appointmentMapper.toDTO(any())).thenReturn(new AppointmentDTO());

        appointmentService.update(APPOINTMENT_ID, dto);

        ArgumentCaptor<NotificationEvent> captor = ArgumentCaptor.forClass(NotificationEvent.class);
        verify(notificationService).notify(captor.capture());

        NotificationEvent event = captor.getValue();
        assertThat(event.getType()).isEqualTo(NotificationEventType.APPOINTMENT_RESCHEDULED);
        assertThat(event.getPreviousStartAt()).isEqualTo(previousStartAt);
        assertThat(event.getAppointmentStartAt()).isEqualTo(newStartAt);
    }

    @Test
    void cancelMany_throwsWhenNoIdsProvided() {

        assertThatThrownBy(() -> appointmentService.cancelMany(List.of()))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Nenhum agendamento");
    }

    @Test
    void cancelMany_throwsWhenAnyAppointmentIsCompleted() {

        Appointment completed = new Appointment();
        completed.setId(APPOINTMENT_ID);
        completed.setStatus(AppointmentStatus.COMPLETED);

        when(appointmentRepository.findByIdIn(List.of(APPOINTMENT_ID))).thenReturn(List.of(completed));

        assertThatThrownBy(() -> appointmentService.cancelMany(List.of(APPOINTMENT_ID)))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("concluido");
    }

    @Test
    void cancelMany_cancelsEligibleAppointments() {

        Appointment scheduled = new Appointment();
        scheduled.setId(APPOINTMENT_ID);
        scheduled.setStatus(AppointmentStatus.SCHEDULED);

        when(appointmentRepository.findByIdIn(List.of(APPOINTMENT_ID))).thenReturn(List.of(scheduled));

        appointmentService.cancelMany(List.of(APPOINTMENT_ID));

        assertThat(scheduled.getStatus()).isEqualTo(AppointmentStatus.CANCELLED);
        verify(appointmentRepository).saveAll(List.of(scheduled));
    }

    @Test
    void cancelMany_scopesLookupByCompanyForNonMasterAdmin() {

        authenticateAs("COMPANY_ADMIN", COMPANY_ID);

        Appointment scheduled = new Appointment();
        scheduled.setId(APPOINTMENT_ID);
        scheduled.setCompanyId(COMPANY_ID);
        scheduled.setStatus(AppointmentStatus.SCHEDULED);

        when(appointmentRepository.findByIdInAndCompanyId(List.of(APPOINTMENT_ID), COMPANY_ID)).thenReturn(List.of(scheduled));

        appointmentService.cancelMany(List.of(APPOINTMENT_ID));

        assertThat(scheduled.getStatus()).isEqualTo(AppointmentStatus.CANCELLED);
        verify(appointmentRepository, org.mockito.Mockito.never()).findByIdIn(any());
    }

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
