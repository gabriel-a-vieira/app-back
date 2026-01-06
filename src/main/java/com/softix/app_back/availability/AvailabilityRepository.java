package com.softix.app_back.availability;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability, String> {

    List<Availability> findByProfessionalIdAndDayWeek(String professionalId, DayOfWeek dayWeek);

}
