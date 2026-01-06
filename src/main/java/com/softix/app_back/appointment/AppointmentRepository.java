package com.softix.app_back.appointment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, String> {

    boolean existsByProfessionalIdAndStatusInAndStartAtLessThanAndEndAtGreaterThan(String professionalId, List<AppointmentStatus> statuses,
                                                                                   LocalDateTime endAt, LocalDateTime startAt);

}
