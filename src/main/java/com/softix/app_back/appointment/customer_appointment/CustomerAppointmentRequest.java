package com.softix.app_back.appointment.customer_appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CustomerAppointmentRequest {

    @NotBlank(message = "Empresa e obrigatoria")
    private String companyId;

    @NotBlank(message = "Profissional e obrigatorio")
    private String professionalId;

    @NotEmpty(message = "Selecione pelo menos um servico")
    private List<String> serviceIds;

    @NotNull(message = "Data e horario sao obrigatorios")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startAt;

    private String notes;

    private Boolean prefersSilence;

}
