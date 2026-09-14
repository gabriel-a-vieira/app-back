package com.softix.app_back.appointment;

import com.softix.app_back.appointment.customer_appointment.CustomerAppointmentDTO;
import com.softix.app_back.company.Company;
import com.softix.app_back.company.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AppointmentMapper {

    private final AppointmentServiceItemRepository appointmentServiceItemRepository;

    private final CompanyRepository companyRepository;

    public AppointmentDTO toDTO(Appointment appointment) {

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

    public CustomerAppointmentDTO toCustomerDTO(Appointment appointment) {

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
