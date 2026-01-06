package com.softix.app_back.professional;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/professional")
public class ProfessionalController {

    @Autowired
    ProfessionalService professionalService;

    @Autowired
    ProfessionalRepository professionalRepository;

    @GetMapping
    public List<Professional> getAll() {
        return professionalRepository.findAll();
    }

    @GetMapping(path = "{id}")
    public Professional getById(@PathVariable("id") String id) {
        return professionalRepository.findById(id).orElse(null);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody String jsonBody) {

        if (jsonBody == null) return ResponseEntity.badRequest().body("Invalid/Null JSON");

        ObjectMapper objectMapper = new ObjectMapper();
        ProfessionalDTO dto;

        try {
            dto = objectMapper.readValue(jsonBody, ProfessionalDTO.class);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error parsing JSON");
        }

        return ResponseEntity.ok(professionalService.save(dto));

    }

}