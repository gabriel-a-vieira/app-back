package com.softix.app_back.appointment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    AppointmentRepository appointmentRepository;

    @GetMapping
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    @GetMapping(path = "{id}")
    public Appointment findById(@PathVariable("id") String id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    @PostMapping
    public ResponseEntity post(@RequestBody String jsonBody) {

        if (jsonBody == null) return ResponseEntity.badRequest().body("Invalid JSON");

        ObjectMapper objectMapper = new ObjectMapper();
        AppointmentDTO dto;

        try {
            dto = objectMapper.readValue(jsonBody, AppointmentDTO.class);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error parsing JSON");
        }

        return ResponseEntity.ok(appointmentService.save(dto));

    }

}
