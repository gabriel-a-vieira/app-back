package com.softix.app_back.appointment;

import com.softix.app_back.availability.Availability;
import com.softix.app_back.availability.AvailabilityRepository;
import com.softix.app_back.client.Client;
import com.softix.app_back.client.ClientRepository;
import com.softix.app_back.professional.Professional;
import com.softix.app_back.professional.ProfessionalRepository;
import com.softix.app_back.service_offering.ServiceOffering;
import com.softix.app_back.service_offering.ServiceOfferingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ProfessionalRepository professionalRepository;

    @Autowired
    ServiceOfferingRepository serviceOfferingRepository;

    @Autowired
    AvailabilityRepository availabilityRepository;

    //TODO review this entire method!
    @Transactional
    public Appointment save(AppointmentDTO dto) {

        Client client = clientRepository.findById(dto.getClientId()).orElseThrow(() -> new RuntimeException("Client not found"));

        Professional professional = professionalRepository.findById(dto.getProfessionalId()).orElseThrow(() -> new RuntimeException("Professional not found"));

        if (!client.getCompanyId().equals(dto.getCompanyId()) || !professional.getCompanyId().equals(dto.getCompanyId())) {
            throw new RuntimeException("Invalid tenant access");
        }

        List<ServiceOffering> services = serviceOfferingRepository.findAllById(dto.getServiceIds());

        if (services.isEmpty()) {
            throw new RuntimeException("No services selected");
        }

        int totalMinutes = services.stream()
                .mapToInt(ServiceOffering::getDurationMinutes)
                .sum();

        LocalDateTime startAt = dto.getStartAt();
        LocalDateTime endAt = startAt.plusMinutes(totalMinutes);

        validateAvailability(professional.getId(), startAt, endAt);

        boolean conflict =
                appointmentRepository.existsByProfessionalIdAndStatusInAndStartAtLessThanAndEndAtGreaterThan(
                        professional.getId(),
                        List.of(AppointmentStatus.SCHEDULED, AppointmentStatus.CONFIRMED),
                        endAt,
                        startAt
                );

        if (conflict) {
            throw new RuntimeException("Time slot already booked");
        }

        Appointment appointment = new Appointment();
        appointment.setCompanyId(dto.getCompanyId());
        appointment.setClient(client);
        appointment.setProfessional(professional);
        appointment.setStartAt(startAt);
        appointment.setEndAt(endAt);
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.setCreatedBy(dto.getUserId());

        List<AppointmentServiceItem> items = new ArrayList<>();

        int order = 1;
        for (ServiceOffering service : services) {

            AppointmentServiceItem item = new AppointmentServiceItem();

            item.setCompanyId(dto.getCompanyId());
            item.setAppointment(appointment);
            item.setServiceOffering(service);
            item.setDurationMinutes(service.getDurationMinutes());
            item.setPrice(service.getPrice());
            item.setExecutionOrder(order++);

            items.add(item);

            //calls the method to persist the AppointmentServiceItem entity

        }

        return appointmentRepository.save(appointment);

    }

    private void validateAvailability(UUID professionalId, LocalDateTime start, LocalDateTime end) {

        DayOfWeek day = start.getDayOfWeek();

        List<Availability> availabilities = availabilityRepository.findByProfessionalIdAndDayWeek(professionalId,day);

        boolean fits = availabilities.stream().anyMatch(a ->
                !start.toLocalTime().isBefore(a.getStartTime()) &&
                        !end.toLocalTime().isAfter(a.getEndTime())
        );

        if (!fits) {
            throw new RuntimeException("Outside professional availability");
        }

    }

}