package com.softix.app_back.appointment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    boolean existsByProfessionalIdAndStatusInAndStartAtLessThanAndEndAtGreaterThan(UUID professionalId, List<AppointmentStatus> statuses,
                                                                                   LocalDateTime endAt, LocalDateTime startAt);

}
