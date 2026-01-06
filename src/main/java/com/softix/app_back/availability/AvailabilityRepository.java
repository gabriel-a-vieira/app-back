package com.softix.app_back.availability;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;

public interface AvailabilityRepository extends JpaRepository<Availability, UUID> {

    List<Availability> findByProfessionalIdAndDayWeek(UUID professionalId, DayOfWeek dayWeek);

}
