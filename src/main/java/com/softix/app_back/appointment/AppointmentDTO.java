package com.softix.app_back.appointment;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class AppointmentDTO {

    private UUID id;
    private UUID clientId;
    private UUID professionalId;
    private LocalDateTime startAt;
    private List<UUID> serviceIds;
    private UUID companyId;
    private UUID userId;

}
