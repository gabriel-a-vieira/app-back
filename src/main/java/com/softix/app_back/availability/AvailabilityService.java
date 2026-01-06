package com.softix.app_back.availability;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;

@Service
public class AvailabilityService {

    @Autowired
    AvailabilityRepository availabilityRepository;

    @Transactional
    public Availability save(AvailabilityDTO dto) {

        Availability availability = new Availability();

        if (dto.getProfessionalId() != null) {

            if (BooleanUtils.isTrue(availabilityRepository.existsById(dto.getProfessionalId()))) {
                availability.setProfessionalId(dto.getProfessionalId());
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profissional não encontrado!");
            }

        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profissional não informado!");
        }

        availability.setDayWeek(dto.getDayWeek());
        availability.setStartTime(dto.getStartTime());
        availability.setEndTime(dto.getEndTime());

        availabilityRepository.save(availability);

        return availability;

    }

    @Transactional
    public List<Availability> getByDay(String professionalId, DayOfWeek day) {
        return availabilityRepository.findByProfessionalIdAndDayWeek(professionalId, day);
    }

}
