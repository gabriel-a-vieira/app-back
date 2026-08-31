package com.softix.app_back.appointment.customer_appointment;

import com.softix.app_back.appointment.AppointmentServiceItemDTO;
import com.softix.app_back.appointment.AppointmentStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CustomerAppointmentDTO {

    private String id;
    private String companyId;
    private String companyName;
    private String professionalId;
    private String professionalName;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private AppointmentStatus status;
    private Double totalPrice;
    private String notes;
    private Boolean prefersSilence;
    private List<AppointmentServiceItemDTO> services = new ArrayList<>();

}
