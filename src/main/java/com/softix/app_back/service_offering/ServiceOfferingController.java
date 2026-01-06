package com.softix.app_back.service_offering;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/service-offering")
public class ServiceOfferingController {

    @Autowired
    ServiceOfferingService serviceOfferingService;

    @Autowired
    ServiceOfferingRepository serviceOfferingRepository;

    @GetMapping
    public List<ServiceOffering> getAll() {
        return serviceOfferingRepository.findAll();
    }

    @GetMapping(path = "{id}")
    public ServiceOffering getById(@PathVariable("id") String id) {
        return serviceOfferingRepository.findById(id).orElse(null);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody String jsonBody) {

        if (jsonBody == null) return ResponseEntity.badRequest().body("Invalid/Null JSON");

        ObjectMapper objectMapper = new ObjectMapper();
        ServiceOfferingDTO dto;

        try {
            dto = objectMapper.readValue(jsonBody, ServiceOfferingDTO.class);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error parsing JSON");
        }

        return ResponseEntity.ok(serviceOfferingService.save(dto));

    }

}