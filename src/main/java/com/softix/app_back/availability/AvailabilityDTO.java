package com.softix.app_back.availability;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class AvailabilityDTO {

    private String id;

    @NotBlank(message = "Profissional e obrigatorio")
    private String professionalId;

    private String name;

    @NotNull(message = "Dia da semana e obrigatorio")
    private DayOfWeek dayWeek;

    @NotNull(message = "Horario inicial e obrigatorio")
    private LocalTime startTime;

    @NotNull(message = "Horario final e obrigatorio")
    private LocalTime endTime;

    private String companyId;

    public AvailabilityDTO() {}

    public AvailabilityDTO(Availability availability) {

        this.id = availability.getId();
        this.professionalId = availability.getProfessionalId();
        this.dayWeek = availability.getDayWeek();
        this.startTime = availability.getStartTime();
        this.endTime = availability.getEndTime();
        this.companyId = availability.getCompanyId();

        if (availability.getProfessional() != null && availability.getProfessional().getPerson() != null) {
            this.name = availability.getProfessional().getPerson().getName();
        }

    }

}