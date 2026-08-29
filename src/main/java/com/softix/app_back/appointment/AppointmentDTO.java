package com.softix.app_back.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class AppointmentDTO {

    private String id;

    @NotBlank(message = "Cliente e obrigatorio")
    private String clientId;

    private String clientName;

    @NotBlank(message = "Profissional e obrigatorio")
    private String professionalId;

    private String professionalName;

    @NotNull(message = "Data e horario sao obrigatorios")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endAt;

    @NotEmpty(message = "Selecione pelo menos um servico")
    private List<String> serviceIds = new ArrayList<>();

    private List<AppointmentServiceItemDTO> services = new ArrayList<>();

    private AppointmentStatus status;

    private String companyId;

}