package com.softix.app_back.availability;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/availability")
public class AvailabilityController {

    @Autowired
    AvailabilityService availabilityService;

    @Autowired
    AvailabilityRepository availabilityRepository;

    @GetMapping
    public List<Availability> findAll() {
        return availabilityRepository.findAll();
    }

    @GetMapping(path = "{id}")
    public Availability findById(@PathVariable("id") String id) {
        return availabilityRepository.findById(id).orElse(null);
    }

    @PostMapping
    public ResponseEntity post(@RequestBody String jsonBody) {

        if (jsonBody == null) return ResponseEntity.badRequest().body("Invalid JSON");

        ObjectMapper objectMapper = new ObjectMapper();
        AvailabilityDTO dto;

        try {
            dto = objectMapper.readValue(jsonBody, AvailabilityDTO.class);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error parsing JSON");
        }

        return ResponseEntity.ok(availabilityService.save(dto));

    }

    @GetMapping(path = "by-day")
    public List<Availability> list(@RequestParam String professionalId,
                                   @RequestParam DayOfWeek day) {
        return availabilityService.getByDay(professionalId, day);
    }

}
