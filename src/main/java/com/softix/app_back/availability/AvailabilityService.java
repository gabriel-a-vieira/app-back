package com.softix.app_back.availability;

import com.softix.app_back.professional.ProfessionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import utils.security.SecurityUtils;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Service
public class AvailabilityService {

    @Autowired
    AvailabilityRepository availabilityRepository;

    @Autowired
    ProfessionalRepository professionalRepository;

    @Transactional(readOnly = true)
    public Page<AvailabilityDTO> findAll(String search, String professionalId, DayOfWeek dayWeek, String companyId, Pageable pageable) {

        String resolvedCompanyId = SecurityUtils.resolveCompanyId(companyId);

        return availabilityRepository.findAdvanced(resolvedCompanyId, professionalId, dayWeek, search, pageable).map(AvailabilityDTO::new);

    }

    @Transactional(readOnly = true)
    public AvailabilityDTO findById(String id) {

        Availability availability = availabilityRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disponibilidade nao encontrada"));

        return new AvailabilityDTO(availability);

    }

    @Transactional
    public AvailabilityDTO save(AvailabilityDTO dto) {

        String companyId = SecurityUtils.resolveCompanyId(dto.getCompanyId());

        validateCompany(companyId);
        validateProfessional(dto.getProfessionalId(), companyId);
        validateTimeRange(dto.getStartTime(), dto.getEndTime());
        validateOverlap(companyId, dto.getProfessionalId(), dto.getDayWeek(), dto.getStartTime(), dto.getEndTime(), null);

        Availability availability = new Availability();
        availability.setCompanyId(companyId);
        availability.setProfessionalId(dto.getProfessionalId());
        availability.setDayWeek(dto.getDayWeek());
        availability.setStartTime(dto.getStartTime());
        availability.setEndTime(dto.getEndTime());

        availabilityRepository.save(availability);

        return new AvailabilityDTO(availability);

    }

    @Transactional
    public AvailabilityDTO update(String id, AvailabilityDTO dto) {

        Availability availability = availabilityRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disponibilidade nao encontrada"));

        String companyId = SecurityUtils.resolveCompanyId(dto.getCompanyId());
        validateCompany(companyId);
        validateProfessional(dto.getProfessionalId(), companyId);
        validateTimeRange(dto.getStartTime(), dto.getEndTime());
        validateOverlap(companyId, dto.getProfessionalId(), dto.getDayWeek(), dto.getStartTime(), dto.getEndTime(), id);

        availability.setCompanyId(companyId);
        availability.setProfessionalId(dto.getProfessionalId());
        availability.setDayWeek(dto.getDayWeek());
        availability.setStartTime(dto.getStartTime());
        availability.setEndTime(dto.getEndTime());

        availabilityRepository.save(availability);

        return new AvailabilityDTO(availability);

    }

    @Transactional
    public void deleteMany(List<String> ids) {

        if (ids == null || ids.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhuma disponibilidade informada");
        }

        List<Availability> availabilities = availabilityRepository.findByIdIn(ids);

        availabilityRepository.deleteAll(availabilities);

    }

    @Transactional(readOnly = true)
    public List<AvailabilityDTO> getByDay(String professionalId, DayOfWeek day) {
        return availabilityRepository.findByProfessionalIdAndDayWeek(professionalId, day).stream().map(AvailabilityDTO::new).toList();
    }

    private void validateProfessional(String professionalId, String companyId) {

        if (professionalId == null || professionalId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profissional nao informado");
        }

        boolean exists = professionalRepository.existsByIdAndCompanyId(professionalId, companyId);

        if (!exists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profissional nao encontrado");
        }

    }

    private void validateCompany(String companyId) {

        if (companyId == null || companyId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empresa nao informada");
        }

    }

    private void validateTimeRange(LocalTime startTime, LocalTime endTime) {

        if (startTime == null || endTime == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Horario inicial e final sao obrigatorios");
        }

        if (!startTime.isBefore(endTime)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Horario inicial deve ser anterior ao horario final");
        }

    }

    private void validateOverlap(String companyId, String professionalId, DayOfWeek dayWeek, LocalTime startTime, LocalTime endTime, String ignoreId) {

        boolean overlap = availabilityRepository.existsOverlappingAvailability(companyId, professionalId, dayWeek, startTime, endTime, ignoreId);

        if (overlap) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ja existe uma disponibilidade que conflita com este horario");
        }

    }

}