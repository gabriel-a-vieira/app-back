package com.softix.app_back.appointment;

import com.softix.app_back.appointment.customer_appointment.CustomerAppointmentDTO;
import com.softix.app_back.appointment.customer_appointment.CustomerAppointmentRequest;
import com.softix.app_back.availability.Availability;
import com.softix.app_back.availability.AvailabilityRepository;
import com.softix.app_back.client.Client;
import com.softix.app_back.client.ClientRepository;
import com.softix.app_back.client.ClientService;
import com.softix.app_back.company.Company;
import com.softix.app_back.company.CompanyRepository;
import com.softix.app_back.professional.Professional;
import com.softix.app_back.professional.ProfessionalRepository;
import com.softix.app_back.service_offering.ServiceOffering;
import com.softix.app_back.service_offering.ServiceOfferingRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import utils.security.SecurityUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private static final List<AppointmentStatus> BLOCKING_STATUSES = List.of(AppointmentStatus.SCHEDULED, AppointmentStatus.CONFIRMED);

    private static final int SLOT_INTERVAL_MINUTES = 15;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    AppointmentServiceItemRepository appointmentServiceItemRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ProfessionalRepository professionalRepository;

    @Autowired
    ServiceOfferingRepository serviceOfferingRepository;

    @Autowired
    AvailabilityRepository availabilityRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ClientService clientService;

    @Transactional(readOnly = true)
    public Page<AppointmentDTO> findAll(String search, String status, String clientId, String professionalId, LocalDate dateFrom, LocalDate dateTo, String companyId, Pageable pageable) {

        String resolvedCompanyId = SecurityUtils.resolveCompanyId(companyId);

        AppointmentStatus parsedStatus = parseStatus(status);

        LocalDateTime dateFromTime = dateFrom != null ? dateFrom.atStartOfDay() : LocalDateTime.now();

        LocalDateTime dateToTime = dateTo != null ? dateTo.plusDays(1).atStartOfDay() : LocalDateTime.now().plusDays(7);

        return appointmentRepository.findAdvanced(resolvedCompanyId, search, parsedStatus, clientId, professionalId, dateFromTime, dateToTime, pageable).map(this::toDTO);

    }

    @Transactional(readOnly = true)
    public AppointmentDTO findById(String id) {

        String companyId = SecurityUtils.resolveCompanyId(null);
        Appointment appointment = findAppointment(id, companyId);

        return toDTO(appointment);

    }

    @Transactional
    public AppointmentDTO save(AppointmentDTO dto) {

        String companyId = SecurityUtils.resolveCompanyId(dto.getCompanyId());
        validateCompanyId(companyId);

        Client client = findClient(dto.getClientId(), companyId);
        Professional professional = findProfessional(dto.getProfessionalId(), companyId);
        List<ServiceOffering> services = findServices(dto.getServiceIds(), companyId);

        LocalDateTime startAt = dto.getStartAt();
        validateStartAt(startAt);

        LocalDateTime endAt = calculateEndAt(startAt, services);

        validateAvailability(professional.getId(), companyId, startAt, endAt);
        validateConflict(professional.getId(), companyId, startAt, endAt, null);

        Appointment appointment = new Appointment();

        appointment.setCompanyId(companyId);
        appointment.setClient(client);
        appointment.setProfessional(professional);
        appointment.setStartAt(startAt);
        appointment.setEndAt(endAt);
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        appointmentRepository.save(appointment);

        saveServiceItems(appointment, services, companyId);

        return toDTO(appointment);

    }

    @Transactional
    public AppointmentDTO update(String id, AppointmentDTO dto) {

        String companyId = SecurityUtils.resolveCompanyId(dto.getCompanyId());

        validateCompanyId(companyId);

        Appointment appointment = findAppointment(id, companyId);
        Client client = findClient(dto.getClientId(), companyId);
        Professional professional = findProfessional(dto.getProfessionalId(), companyId);
        List<ServiceOffering> services = findServices(dto.getServiceIds(), companyId);

        LocalDateTime startAt = dto.getStartAt();
        LocalDateTime endAt = calculateEndAt(startAt, services);

        AppointmentStatus newStatus = dto.getStatus() != null ? dto.getStatus() : appointment.getStatus();

        if (BLOCKING_STATUSES.contains(newStatus)) {

            validateAvailability(professional.getId(), companyId, startAt, endAt);
            validateConflict(professional.getId(), companyId, startAt, endAt, id);

        }

        appointment.setClient(client);
        appointment.setProfessional(professional);
        appointment.setStartAt(startAt);
        appointment.setEndAt(endAt);
        appointment.setStatus(newStatus);

        appointmentRepository.save(appointment);

        appointmentServiceItemRepository.deleteByAppointmentId(id);

        saveServiceItems(appointment, services, companyId);

        return toDTO(appointment);

    }

    @Transactional
    public void cancelMany(List<String> ids) {

        if (ids == null || ids.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhum agendamento informado");
        }

        String companyId = SecurityUtils.resolveCompanyId(null);
        List<Appointment> appointments;

        if (companyId == null || companyId.isBlank()) {
            appointments = appointmentRepository.findByIdIn(ids);
        } else {
            appointments = appointmentRepository.findByIdInAndCompanyId(ids, companyId);
        }

        for (Appointment appointment : appointments) {

            if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Agendamento concluido nao pode ser cancelado");
            }

            appointment.setStatus(AppointmentStatus.CANCELLED);

        }

        appointmentRepository.saveAll(appointments);

    }

    @Transactional(readOnly = true)
    public List<String> findAvailableSlots(String professionalId, LocalDate date, List<String> serviceIds, String companyId, String ignoreAppointmentId) {

        companyId = StringUtils.isBlank(companyId) ? SecurityUtils.resolveCompanyId(companyId) : companyId;

        validateCompanyId(companyId);

        Professional professional = findProfessional(professionalId, companyId);
        List<ServiceOffering> services = findServices(serviceIds, companyId);

        int totalMinutes = calculateTotalMinutes(services);
        DayOfWeek day = date.getDayOfWeek();

        List<Availability> availabilities = availabilityRepository.findByProfessionalIdAndDayWeekAndCompanyIdOrderByStartTimeAsc(professional.getId(), day, companyId);

        if (availabilities.isEmpty()) {
            return List.of();
        }

        LocalDateTime dayStart = date.atStartOfDay();
        LocalDateTime dayEnd = date.plusDays(1).atStartOfDay();

        List<Appointment> booked = appointmentRepository.findByCompanyIdAndProfessionalIdAndStatusInAndStartAtLessThanAndEndAtGreaterThan(companyId, professional.getId(), BLOCKING_STATUSES, dayEnd, dayStart);

        if (ignoreAppointmentId != null && !ignoreAppointmentId.isBlank()) {
            booked = booked.stream().filter(appointment -> !ignoreAppointmentId.equals(appointment.getId())).toList();
        }

        List<String> slots = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        for (Availability availability : availabilities) {

            LocalDateTime current = LocalDateTime.of(date, availability.getStartTime());
            LocalDateTime availabilityEnd = LocalDateTime.of(date, availability.getEndTime());

            while (!current.plusMinutes(totalMinutes).isAfter(availabilityEnd)) {

                LocalDateTime candidateEnd = current.plusMinutes(totalMinutes);

                boolean past = current.isBefore(now);
                boolean conflict = hasConflict(booked, current, candidateEnd);

                if (!past && !conflict) {
                    slots.add(formatTime(current.toLocalTime()));
                }

                current = current.plusMinutes(SLOT_INTERVAL_MINUTES);

            }

        }

        return slots.stream().distinct().toList();

    }

    private AppointmentDTO toDTO(Appointment appointment) {

        AppointmentDTO dto = new AppointmentDTO();

        dto.setId(appointment.getId());
        dto.setCompanyId(appointment.getCompanyId());
        dto.setStartAt(appointment.getStartAt());
        dto.setEndAt(appointment.getEndAt());
        dto.setStatus(appointment.getStatus());

        if (appointment.getClient() != null) {

            dto.setClientId(appointment.getClient().getId());

            if (appointment.getClient().getPerson() != null) {
                dto.setClientName(appointment.getClient().getPerson().getName());
            }

        }

        if (appointment.getProfessional() != null) {

            dto.setProfessionalId(appointment.getProfessional().getId());

            if (appointment.getProfessional().getPerson() != null) {
                dto.setProfessionalName(appointment.getProfessional().getPerson().getName());
            }

        }

        List<AppointmentServiceItem> items = appointmentServiceItemRepository.findByAppointmentIdOrderByExecutionOrderAsc(appointment.getId());

        List<AppointmentServiceItemDTO> serviceDTOs = items.stream().map(AppointmentServiceItemDTO::new).toList();

        dto.setServices(serviceDTOs);
        dto.setServiceIds(serviceDTOs.stream().map(AppointmentServiceItemDTO::getServiceOfferingId).toList());

        return dto;

    }

    private Appointment findAppointment(String id, String companyId) {

        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamento nao encontrado"));

        if (companyId != null && !companyId.isBlank() && !companyId.equals(appointment.getCompanyId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamento nao encontrado");
        }

        return appointment;

    }

    private Client findClient(String id, String companyId) {
        return clientRepository.findByIdAndCompanyId(id, companyId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente nao encontrado"));
    }

    private Professional findProfessional(String id, String companyId) {
        return professionalRepository.findByIdAndCompanyId(id, companyId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profissional nao encontrado"));
    }

    private List<ServiceOffering> findServices(List<String> ids, String companyId) {

        if (ids == null || ids.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Selecione pelo menos um servico");
        }

        List<String> uniqueIds = ids.stream().filter(Objects::nonNull).distinct().toList();

        List<ServiceOffering> found = serviceOfferingRepository.findByIdInAndCompanyId(uniqueIds, companyId);

        if (found.size() != uniqueIds.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Um ou mais servicos nao foram encontrados");
        }

        Map<String, ServiceOffering> byId = found.stream().collect(Collectors.toMap(ServiceOffering::getId, Function.identity()));

        return uniqueIds.stream().map(byId::get).toList();

    }

    private void saveServiceItems(Appointment appointment, List<ServiceOffering> services, String companyId) {

        List<AppointmentServiceItem> items = new ArrayList<>();

        int order = 1;

        for (ServiceOffering service : services) {

            AppointmentServiceItem item = new AppointmentServiceItem();

            item.setCompanyId(companyId);
            item.setAppointment(appointment);
            item.setServiceOffering(service);
            item.setDurationMinutes(service.getDurationMinutes());
            item.setPrice(service.getPrice());
            item.setExecutionOrder(order++);
            items.add(item);

        }

        appointmentServiceItemRepository.saveAll(items);

    }

    private LocalDateTime calculateEndAt(LocalDateTime startAt, List<ServiceOffering> services) {

        int totalMinutes = calculateTotalMinutes(services);

        LocalDateTime endAt = startAt.plusMinutes(totalMinutes);

        if (!startAt.toLocalDate().equals(endAt.toLocalDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O agendamento nao pode ultrapassar o final do dia");
        }

        return endAt;

    }

    private int calculateTotalMinutes(List<ServiceOffering> services) {
        return services.stream().mapToInt(ServiceOffering::getDurationMinutes).sum();
    }

    private void validateAvailability(String professionalId, String companyId, LocalDateTime startAt, LocalDateTime endAt) {

        DayOfWeek day = startAt.getDayOfWeek();

        List<Availability> availabilities = availabilityRepository.findByProfessionalIdAndDayWeekAndCompanyIdOrderByStartTimeAsc(professionalId, day, companyId);
        LocalTime startTime = startAt.toLocalTime();
        LocalTime endTime = endAt.toLocalTime();

        boolean fits = availabilities.stream().anyMatch(availability -> !startTime.isBefore(availability.getStartTime()) && !endTime.isAfter(availability.getEndTime()));

        if (!fits) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Horario fora da disponibilidade do profissional");
        }

    }

    private void validateConflict(String professionalId, String companyId, LocalDateTime startAt, LocalDateTime endAt, String ignoreId) {

        boolean conflict = appointmentRepository.existsConflict(companyId, professionalId, BLOCKING_STATUSES, startAt, endAt, ignoreId);

        if (conflict) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este horario ja esta ocupado para o profissional");
        }

    }

    private boolean hasConflict(List<Appointment> appointments, LocalDateTime startAt, LocalDateTime endAt) {
        return appointments.stream().anyMatch(appointment -> appointment.getStartAt().isBefore(endAt) && appointment.getEndAt().isAfter(startAt));
    }

    private AppointmentStatus parseStatus(String status) {

        if (status == null || status.isBlank() || "ALL".equalsIgnoreCase(status)) {
            return null;
        }

        try {
            return AppointmentStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status invalido");
        }

    }

    private void validateCompanyId(String companyId) {

        if (companyId == null || companyId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empresa nao informada");
        }

    }

    private void validateStartAt(LocalDateTime startAt) {

        if (startAt == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data e horario sao obrigatorios");
        }

        if (startAt.isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nao e possivel criar um agendamento no passado");
        }

    }

    private String formatTime(LocalTime time) {
        return String.format("%02d:%02d", time.getHour(), time.getMinute());
    }

    @Transactional
    public CustomerAppointmentDTO saveCustomer(CustomerAppointmentRequest request) {

        Client client = clientService.findOrCreateForCustomer(request.getCompanyId());
        Professional professional = findProfessional(request.getProfessionalId(), request.getCompanyId());
        List<ServiceOffering> services = findServices(request.getServiceIds(), request.getCompanyId());

        LocalDateTime startAt = request.getStartAt();

        if (startAt == null || startAt.isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Horario de agendamento invalido");
        }

        LocalDateTime endAt = calculateEndAt(startAt, services);

        validateAvailability(professional.getId(), request.getCompanyId(), startAt, endAt);
        validateConflict(professional.getId(), request.getCompanyId(), startAt, endAt, null);

        Appointment appointment = new Appointment();
        appointment.setCompanyId(request.getCompanyId());
        appointment.setClient(client);
        appointment.setProfessional(professional);
        appointment.setStartAt(startAt);
        appointment.setEndAt(endAt);
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.setNotes(StringUtils.trim(request.getNotes()));
        appointment.setPrefersSilence(Boolean.TRUE.equals(request.getPrefersSilence()));

        appointmentRepository.save(appointment);

        saveServiceItems(appointment, services, request.getCompanyId());

        return toCustomerDTO(appointment);

    }

    @Transactional(readOnly = true)
    public Page<CustomerAppointmentDTO> findMine(Pageable pageable) {

        String userId = SecurityUtils.userId();
        return appointmentRepository.findMine(userId, LocalDate.now().atStartOfDay(), pageable).map(this::toCustomerDTO);

    }

    @Transactional(readOnly = true)
    public CustomerAppointmentDTO findMineById(String id) {

        String userId = SecurityUtils.userId();
        Appointment appointment = appointmentRepository.findMineById(id, userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamento nao encontrado"));
        return toCustomerDTO(appointment);

    }

    @Transactional
    public void cancelMine(String id) {

        String userId = SecurityUtils.userId();

        Appointment appointment = appointmentRepository.findMineById(id, userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamento nao encontrado"));

        if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Agendamento concluido nao pode ser cancelado");
        }

        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            return;
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);

        appointmentRepository.save(appointment);

    }

    private CustomerAppointmentDTO toCustomerDTO(Appointment appointment) {

        CustomerAppointmentDTO dto = new CustomerAppointmentDTO();

        dto.setId(appointment.getId());
        dto.setCompanyId(appointment.getCompanyId());
        dto.setStartAt(appointment.getStartAt());
        dto.setEndAt(appointment.getEndAt());
        dto.setStatus(appointment.getStatus());
        dto.setNotes(appointment.getNotes());
        dto.setPrefersSilence(appointment.getPrefersSilence());

        if (appointment.getProfessional() != null) {

            dto.setProfessionalId(appointment.getProfessional().getId());

            if (appointment.getProfessional().getPerson() != null) {
                dto.setProfessionalName(appointment.getProfessional().getPerson().getName());
            }

        }

        Company company = companyRepository.findById(appointment.getCompanyId()).orElse(null);

        if (company != null) {

            String companyName = company.getTradeName();

            if (companyName == null || companyName.isBlank()) {
                companyName = company.getLegalName();
            }

            dto.setCompanyName(companyName);

        }

        List<AppointmentServiceItem> items = appointmentServiceItemRepository.findByAppointmentIdOrderByExecutionOrderAsc(appointment.getId());
        dto.setServices(items.stream().map(AppointmentServiceItemDTO::new).toList());

        double total = items.stream().map(AppointmentServiceItem::getPrice).filter(Objects::nonNull).mapToDouble(Double::doubleValue).sum();
        dto.setTotalPrice(total);

        return dto;

    }

}