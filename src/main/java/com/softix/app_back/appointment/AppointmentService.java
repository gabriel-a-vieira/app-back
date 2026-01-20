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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class AppointmentService {

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

    //TODO review this entire method!
    @Transactional
    public Appointment save(AppointmentDTO dto) {

        Client client = clientRepository.findById(dto.getClientId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente não encontrado!"));

        Professional professional = professionalRepository.findById(dto.getProfessionalId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profissional não encontrado!"));

//        if (!client.getCompanyId().equals(dto.getCompanyId()) || !professional.getCompanyId().equals(dto.getCompanyId())) {
//            throw new RuntimeException("Invalid tenant access");
//        } TODO remove this conditional, because it has to be implemented something on the queries, to validate in the where section de companyId, not on every API, it has to be something default for the queries, and if doesnt want to validate the companyId just declare some parameter to deny it

        List<ServiceOffering> services = serviceOfferingRepository.findAllById(dto.getServiceIds());

        if (services.isEmpty()) {
            throw new RuntimeException("No services selected");
        } //TODO remove all this RuntimeExceptions because it has to be a 400 return like the ResponseStatusException

        int totalMinutes = services.stream()
                .mapToInt(ServiceOffering::getDurationMinutes)
                .sum();

        Date startAt = dto.getStartAt();
        Date endAt = Date.from(startAt.toInstant().plusSeconds(totalMinutes * 60L));

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
        appointment.setCreatedByUserId(dto.getUserId());

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

            appointmentServiceItemRepository.save(item);
            items.add(item);

        }

        return appointmentRepository.save(appointment);

    }

    private void validateAvailability(String professionalId, Date start, Date end) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        DayOfWeek day = DayOfWeek.of(dayOfWeek);

        List<Availability> availabilities = availabilityRepository.findByProfessionalIdAndDayWeek(professionalId, day);

        LocalTime startTime = start.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        LocalTime endTime = end.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();

        boolean fits = availabilities.stream().anyMatch(a ->
                !startTime.isBefore(a.getStartTime()) &&
                        !endTime.isAfter(a.getEndTime())
        );

        if (!fits) {
            throw new RuntimeException("Outside professional availability");
        }

    }

}