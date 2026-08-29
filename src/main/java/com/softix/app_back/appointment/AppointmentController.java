package com.softix.app_back.appointment;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    @GetMapping
    public Page<AppointmentDTO> findAll(@RequestParam(required = false) String search,
                                        @RequestParam(required = false) String status,
                                        @RequestParam(required = false) String clientId,
                                        @RequestParam(required = false) String professionalId,
                                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
                                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
                                        @RequestParam(required = false) String companyId,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "startAt"));

        return appointmentService.findAll(search, status, clientId, professionalId, dateFrom, dateTo, companyId, pageable);

    }

    @GetMapping("/{id}")
    public AppointmentDTO findById(@PathVariable String id) {
        return appointmentService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentDTO create(@Valid @RequestBody AppointmentDTO dto) {
        return appointmentService.save(dto);
    }

    @PutMapping("/{id}")
    public AppointmentDTO update(@PathVariable String id,
                                 @Valid @RequestBody AppointmentDTO dto) {
        return appointmentService.update(id, dto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelMany(@RequestBody List<String> ids) {
        appointmentService.cancelMany(ids);
    }

    @GetMapping("/available-slots")
    public List<String> findAvailableSlots(@RequestParam String professionalId,
                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                           @RequestParam List<String> serviceIds,
                                           @RequestParam(required = false) String companyId,
                                           @RequestParam(required = false) String ignoreAppointmentId) {
        return appointmentService.findAvailableSlots(professionalId, date, serviceIds, companyId, ignoreAppointmentId);
    }

}